package com.example.snaptube.download

import android.content.Context
import android.util.Log

import com.example.snaptube.download.Task.DownloadState
import com.example.snaptube.download.Task.DownloadState.Canceled
import com.example.snaptube.download.Task.DownloadState.Completed
import com.example.snaptube.download.Task.DownloadState.Error
import com.example.snaptube.download.Task.DownloadState.FetchingInfo
import com.example.snaptube.download.Task.DownloadState.Idle
import com.example.snaptube.download.Task.DownloadState.ReadyWithInfo
import com.example.snaptube.download.Task.DownloadState.Running
import com.example.snaptube.download.Task.RestartableAction.Download
import com.example.snaptube.download.Task.RestartableAction.FetchInfo
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.utils.DownloadNotificationManager
import com.example.snaptube.utils.DownloadPreferencesManager
import com.example.snaptube.utils.FileUtils
import com.yausername.youtubedl_android.YoutubeDL
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.util.concurrent.ConcurrentHashMap

private const val TAG = "TaskDownloaderV2"
private const val MAX_CONCURRENCY = 3

@OptIn(FlowPreview::class)
class TaskDownloaderV2(
    private val appContext: Context,
    private val notificationManager: DownloadNotificationManager,
    private val preferencesManager: DownloadPreferencesManager,
    private val downloadManager: DownloadManager,
    private val fileUtils: FileUtils,
    private val videoInfoExtractor: VideoInfoExtractor
) {
    companion object {
        private const val TAG = "TaskDownloaderV2"
        private const val MAX_CONCURRENCY = 3
        private const val TASKS_BACKUP_FILE = "tasks_backup.json"
    }

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val taskMap = ConcurrentHashMap<String, Task>()
    private val activeJobs = ConcurrentHashMap<String, Job>()
    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())
    
    // Public flow for observing tasks
    val tasks: StateFlow<List<Task>> = _tasksFlow.asStateFlow()
    
    // Helper function to update the tasks flow whenever taskMap changes
    private fun updateTasksFlow() {
        _tasksFlow.value = taskMap.values.toList()
    }

    init {
        // Initialize and restore tasks from backup
        scope.launch {
            initializeDownloader()
        }

        // Monitor active downloads for notifications
        scope.launch {
            tasks.collect { taskList ->
                val activeDownloads = taskList.count { it.downloadState.isActive() }
                
                // Update notifications for active downloads
                taskList.forEach { task ->
                    when (task.downloadState) {
                        is Running, is FetchingInfo -> {
                            if (preferencesManager.areNotificationsEnabled()) {
                                notificationManager.showDownloadProgress(task, activeDownloads)
                            }
                        }
                        is Completed -> {
                            if (preferencesManager.areNotificationsEnabled()) {
                                notificationManager.showDownloadCompleted(task)
                            }
                        }
                        is Error -> {
                            if (preferencesManager.areNotificationsEnabled()) {
                                task.error?.let { error ->
                                    notificationManager.showDownloadError(task, error)
                                }
                            }
                        }
                        else -> { /* No notification needed */ }
                    }
                }
                
                // Clear progress notification if no active downloads
                if (activeDownloads == 0) {
                    notificationManager.clearProgressNotification()
                }
            }
        }

        // Auto-backup tasks periodically
        scope.launch {
            tasks
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .collect { taskList ->
                    backupTasks(taskList)
                }
        }
    }

    private suspend fun initializeDownloader() {
        try {
            // Initialize YoutubeDL if not already done
            YoutubeDL.getInstance().init(appContext)
            
            // Restore tasks from backup
            restoreTasksFromBackup()
            
            Log.d(TAG, "TaskDownloaderV2 initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize TaskDownloaderV2", e)
        }
    }

    suspend fun addTask(task: Task): Boolean {
        return try {
            taskMap[task.id] = task
            updateTasksFlow()
            
            // Start processing the task immediately if we have capacity
            processNextTasks()
            
            Log.d(TAG, "Added task: ${task.id}")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add task: ${task.id}", e)
            false
        }
    }

    suspend fun cancelTask(taskId: String): Boolean {
        return try {
            val task = taskMap[taskId] ?: return false
            
            // Cancel the active job if it exists
            activeJobs[taskId]?.cancel()
            activeJobs.remove(taskId)
            
            // Update task state
            task.downloadState = when (val currentState = task.downloadState) {
                is FetchingInfo -> Canceled(FetchInfo)
                is Running -> Canceled(Download, currentState.progress)
                is ReadyWithInfo -> Canceled(Download)
                else -> return false
            }
            
            taskMap[taskId] = task
            updateTasksFlow()
            
            Log.d(TAG, "Canceled task: $taskId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to cancel task: $taskId", e)
            false
        }
    }

    suspend fun deleteTask(taskId: String): Boolean {
        return try {
            val task = taskMap[taskId] ?: return false
            
            // Cancel first if it's active
            if (task.downloadState.isActive()) {
                cancelTask(taskId)
            }
            
            // Delete the file if it exists
            task.outputPath?.let { path ->
                fileUtils.deleteFile(File(path))
            }
            
            // Remove from map
            taskMap.remove(taskId)
            updateTasksFlow()
            
            Log.d(TAG, "Deleted task: $taskId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to delete task: $taskId", e)
            false
        }
    }

    suspend fun pauseTask(taskId: String): Boolean {
        return cancelTask(taskId) // For now, pause is the same as cancel
    }

    suspend fun resumeTask(taskId: String): Boolean {
        return try {
            val task = taskMap[taskId] ?: return false
            
            when (val currentState = task.downloadState) {
                is Canceled -> {
                    // Reset to appropriate state for restart
                    task.downloadState = when (currentState.action) {
                        FetchInfo -> Idle
                        Download -> ReadyWithInfo
                    }
                    taskMap[taskId] = task
                    updateTasksFlow()
                    
                    // Start processing
                    processNextTasks()
                    
                    Log.d(TAG, "Resumed task: $taskId")
                    true
                }
                else -> false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to resume task: $taskId", e)
            false
        }
    }

    suspend fun restartTask(taskId: String): Boolean {
        return try {
            val task = taskMap[taskId] ?: return false
            
            // Cancel current operation if active
            if (task.downloadState.isActive()) {
                cancelTask(taskId)
            }
            
            // Reset task state
            task.downloadState = Idle
            task.progress = 0f
            task.progressText = ""
            task.error = null
            task.outputPath = null
            
            taskMap[taskId] = task
            updateTasksFlow()
            
            // Start processing
            processNextTasks()
            
            Log.d(TAG, "Restarted task: $taskId")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restart task: $taskId", e)
            false
        }
    }

    suspend fun retryTask(taskId: String): Boolean {
        return restartTask(taskId)
    }

    suspend fun clearAllTasks(): Boolean {
        return try {
            // Cancel all active jobs
            activeJobs.values.forEach { it.cancel() }
            activeJobs.clear()
            
            // Delete all files
            taskMap.values.forEach { task ->
                task.outputPath?.let { path ->
                    fileUtils.deleteFile(File(path))
                }
            }
            
            // Clear all tasks
            taskMap.clear()
            updateTasksFlow()
            
            // Clear backup
            clearTaskBackup()
            
            Log.d(TAG, "Cleared all tasks")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clear all tasks", e)
            false
        }
    }

    suspend fun clearCompletedTasks(): Boolean {
        return try {
            val completedTasks = taskMap.values.filter { it.downloadState is Completed }
            
            completedTasks.forEach { task ->
                taskMap.remove(task.id)
            }
            updateTasksFlow()
            
            Log.d(TAG, "Cleared ${completedTasks.size} completed tasks")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clear completed tasks", e)
            false
        }
    }

    suspend fun pauseAllTasks(): Boolean {
        return try {
            val activeTasks = taskMap.values.filter { it.downloadState.isActive() }
            
            activeTasks.forEach { task ->
                cancelTask(task.id)
            }
            
            Log.d(TAG, "Paused ${activeTasks.size} active tasks")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to pause all tasks", e)
            false
        }
    }

    suspend fun resumeAllTasks(): Boolean {
        return try {
            val pausedTasks = taskMap.values.filter { it.downloadState is Canceled }
            
            pausedTasks.forEach { task ->
                resumeTask(task.id)
            }
            
            Log.d(TAG, "Resumed ${pausedTasks.size} paused tasks")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to resume all tasks", e)
            false
        }
    }

    suspend fun refreshTasks(): Boolean {
        return try {
            // Process any tasks that might be stuck in idle state
            processNextTasks()
            
            Log.d(TAG, "Refreshed tasks")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to refresh tasks", e)
            false
        }
    }

    private suspend fun processNextTasks() {
        val currentActive = taskMap.values.count { it.downloadState.isActive() }
        val maxConcurrent = preferencesManager.getMaxConcurrentDownloads()
        
        if (currentActive >= maxConcurrent) {
            return // Already at capacity
        }
        
        // Find tasks that can be started
        val tasksToStart = taskMap.values
            .filter { task ->
                when (task.downloadState) {
                    is Idle -> true
                    is ReadyWithInfo -> true
                    else -> false
                }
            }
            .sortedBy { it.timeCreated }
            .take(maxConcurrent - currentActive)
        
        tasksToStart.forEach { task ->
            startTaskProcessing(task)
        }
    }

    private fun startTaskProcessing(task: Task) {
        val job = scope.launch {
            try {
                when (task.downloadState) {
                    is Idle -> {
                        fetchVideoInfo(task)
                    }
                    is ReadyWithInfo -> {
                        downloadVideo(task)
                    }
                    else -> {
                        Log.w(TAG, "Cannot start task in state: ${task.downloadState}")
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error processing task: ${task.id}", e)
                task.downloadState = Error(e, 
                    if (task.downloadState is FetchingInfo) FetchInfo else Download
                )
                task.error = e
                taskMap[task.id] = task
                updateTasksFlow()
            } finally {
                activeJobs.remove(task.id)
                // Try to start next tasks
                processNextTasks()
            }
        }
        
        activeJobs[task.id] = job
    }
        
    private suspend fun fetchVideoInfo(task: Task) {
        task.downloadState = FetchingInfo(activeJobs[task.id] ?: Job(), task.id)
        task.progressText = "Fetching video information..."
        taskMap[task.id] = task
        updateTasksFlow()

        try {
            val videoInfo = videoInfoExtractor.extractVideoInfo(task.url)
            
            // Update task with video information
            task.viewState = Task.ViewState.fromVideoInfo(videoInfo)
            task.downloadState = ReadyWithInfo
            taskMap[task.id] = task
            updateTasksFlow()
            
            // Continue to download phase
            processNextTasks()
            
        } catch (e: Exception) {
            task.downloadState = Error(e, FetchInfo)
            task.error = e
            taskMap[task.id] = task
            updateTasksFlow()
            throw e
        }
    }

    private suspend fun downloadVideo(task: Task) {
        val job = activeJobs[task.id] ?: Job()
        task.downloadState = Running(job, task.id, 0f, "Starting download...")
        taskMap[task.id] = task
        updateTasksFlow()

        try {
            // Create download format from task preferences
            val downloadFormat = DownloadFormat(
                formatId = task.preferences.formatId,
                extension = if (task.preferences.audioOnly) task.preferences.audioFormat else task.preferences.videoFormat,
                quality = "",
                fps = 0,
                vcodec = "",
                acodec = "",
                fileSize = 0L
            )
            
            // Start download using DownloadManager
            val downloadResult = downloadManager.startDownload(
                url = task.url,
                selectedFormat = downloadFormat,
                downloadPath = task.preferences.downloadLocation,
                audioOnly = task.preferences.audioOnly
            )
            
            if (downloadResult.isSuccess) {
                val downloadId = downloadResult.getOrThrow()
                
                // For now, simulate download completion
                // TODO: Implement proper progress monitoring
                task.downloadState = Completed("/path/to/downloaded/file")
                task.outputPath = "/path/to/downloaded/file"
                task.progress = 1f
                task.progressText = "Download completed"
                taskMap[task.id] = task
                updateTasksFlow()
            } else {
                throw downloadResult.exceptionOrNull() ?: Exception("Unknown download error")
            }
            
        } catch (e: Exception) {
            task.downloadState = Error(e, Download)
            task.error = e
            taskMap[task.id] = task
            updateTasksFlow()
            throw e
        }
    }

    private suspend fun backupTasks(taskList: List<Task>) {
        try {
            val json = Json.encodeToString(taskList)
            val backupFile = File(appContext.filesDir, TASKS_BACKUP_FILE)
            backupFile.writeText(json)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to backup tasks", e)
        }
    }

    private suspend fun restoreTasksFromBackup() {
        try {
            val backupFile = File(appContext.filesDir, TASKS_BACKUP_FILE)
            if (backupFile.exists()) {
                val json = backupFile.readText()
                val taskList = Json.decodeFromString<List<Task>>(json)
                
                taskList.forEach { task ->
                    // Reset active states to canceled on restore
                    when (task.downloadState) {
                        is FetchingInfo -> task.downloadState = Canceled(FetchInfo)
                        is Running -> task.downloadState = Canceled(Download, task.progress)
                        else -> { /* Keep current state */ }
                    }
                    taskMap[task.id] = task
                }
                updateTasksFlow()
                
                Log.d(TAG, "Restored ${taskList.size} tasks from backup")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to restore tasks from backup", e)
        }
    }

    private suspend fun clearTaskBackup() {
        try {
            val backupFile = File(appContext.filesDir, TASKS_BACKUP_FILE)
            if (backupFile.exists()) {
                backupFile.delete()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to clear task backup", e)
        }
    }
}
