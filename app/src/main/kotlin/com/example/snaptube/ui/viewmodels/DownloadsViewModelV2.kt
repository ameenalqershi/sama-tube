package com.example.snaptube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snaptube.download.Task
import com.example.snaptube.download.TaskDownloaderV2
import com.example.snaptube.download.isActive
import com.example.snaptube.download.isCompleted
import com.example.snaptube.download.isError
import com.example.snaptube.download.isCanceled
import com.example.snaptube.ui.components.UiAction
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DownloadsViewModelV2(
    private val taskDownloader: TaskDownloaderV2
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Observe tasks from the downloader
    val tasks: StateFlow<List<Task>> = taskDownloader.tasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Derived state for download statistics
    val downloadStats: StateFlow<DownloadStats> = tasks
        .map { taskList ->
            DownloadStats(
                total = taskList.size,
                downloading = taskList.count { it.downloadState.isActive() },
                completed = taskList.count { it.downloadState.isCompleted() },
                failed = taskList.count { it.downloadState.isError() },
                canceled = taskList.count { it.downloadState.isCanceled() }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DownloadStats()
        )

    init {
        // Initialize the task downloader
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // TaskDownloaderV2 initializes automatically, no need to call initialize()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to initialize downloads: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun handleTaskAction(task: Task, action: UiAction) {
        viewModelScope.launch {
            try {
                when (action) {
                    is UiAction.Cancel -> {
                        taskDownloader.cancelTask(task.id)
                    }
                    is UiAction.Delete -> {
                        taskDownloader.deleteTask(task.id)
                    }
                    is UiAction.Resume -> {
                        taskDownloader.resumeTask(task.id)
                    }
                    is UiAction.Restart -> {
                        taskDownloader.restartTask(task.id)
                    }
                    is UiAction.Pause -> {
                        taskDownloader.pauseTask(task.id)
                    }
                    is UiAction.RetryFailed -> {
                        taskDownloader.retryTask(task.id)
                    }
                    is UiAction.OpenFile -> {
                        // TODO: Implement file opening
                        task.outputPath?.let { path ->
                            openFile(path)
                        }
                    }
                    is UiAction.ShareFile -> {
                        // TODO: Implement file sharing
                        task.outputPath?.let { path ->
                            shareFile(path)
                        }
                    }
                    is UiAction.ShowInFolder -> {
                        // TODO: Implement show in folder
                        task.outputPath?.let { path ->
                            showInFolder(path)
                        }
                    }
                    is UiAction.CopyLink -> {
                        // TODO: Implement copy link
                        copyToClipboard(task.url)
                    }
                    is UiAction.DownloadAgain -> {
                        taskDownloader.restartTask(task.id)
                    }
                    is UiAction.CopyErrorReport -> {
                        // TODO: Implement copy error report
                        copyToClipboard("Error: ${action.throwable.message}")
                    }
                    is UiAction.CopyVideoURL -> {
                        // TODO: Implement copy video URL
                        copyToClipboard(task.url)
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Action failed: ${e.message}"
            }
        }
    }

    fun startDownload(url: String, preferences: com.example.snaptube.download.DownloadPreferences? = null): Boolean {
        return try {
            val downloadPreferences = preferences ?: com.example.snaptube.download.DownloadPreferences()
            val task = com.example.snaptube.download.Task(
                url = url,
                preferences = downloadPreferences
            )
            
            viewModelScope.launch {
                taskDownloader.addTask(task)
            }
            true
        } catch (e: Exception) {
            _errorMessage.value = "Failed to start download: ${e.message}"
            false
        }
    }

    fun clearAllTasks() {
        viewModelScope.launch {
            try {
                taskDownloader.clearAllTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to clear tasks: ${e.message}"
            }
        }
    }

    fun clearCompletedTasks() {
        viewModelScope.launch {
            try {
                taskDownloader.clearCompletedTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to clear completed tasks: ${e.message}"
            }
        }
    }

    fun pauseAllDownloads() {
        viewModelScope.launch {
            try {
                taskDownloader.pauseAllTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to pause downloads: ${e.message}"
            }
        }
    }

    fun resumeAllDownloads() {
        viewModelScope.launch {
            try {
                taskDownloader.resumeAllTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to resume downloads: ${e.message}"
            }
        }
    }

    fun getTask(taskId: String): Task? {
        return tasks.value.find { it.id == taskId }
    }

    fun refreshTasks() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                taskDownloader.refreshTasks()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to refresh tasks: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    private fun openFile(path: String) {
        // TODO: Implement file opening with system default app
        // This would typically use Android's Intent.ACTION_VIEW
    }

    private fun shareFile(path: String) {
        // TODO: Implement file sharing
        // This would typically use Android's Intent.ACTION_SEND
    }

    private fun showInFolder(path: String) {
        // TODO: Implement show in folder
        // This might open a file manager or custom folder view
    }

    private fun copyToClipboard(text: String) {
        // TODO: Implement clipboard copying
        // This would use Android's ClipboardManager
    }

    override fun onCleared() {
        super.onCleared()
        // The TaskDownloaderV2 will handle its own cleanup
    }
}

data class DownloadStats(
    val total: Int = 0,
    val downloading: Int = 0,
    val completed: Int = 0,
    val failed: Int = 0,
    val canceled: Int = 0
) {
    val pending: Int get() = total - downloading - completed - failed - canceled
    val activeDownloads: Int get() = downloading
    val hasActiveDownloads: Boolean get() = downloading > 0
    val hasCompletedDownloads: Boolean get() = completed > 0
    val hasFailedDownloads: Boolean get() = failed > 0
}
