package com.example.snaptube.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import com.example.snaptube.R
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.DownloadState
import com.example.snaptube.repository.DownloadRepository
import com.example.snaptube.repository.SettingsRepository
import com.example.snaptube.utils.LogUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * Foreground service for handling background downloads
 * Manages download lifecycle and provides persistent notifications
 */
class DownloadService : Service() {

    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "download_channel"
        private const val CHANNEL_NAME = "Downloads"
        
        const val ACTION_START_DOWNLOAD = "start_download"
        const val ACTION_PAUSE_DOWNLOAD = "pause_download"
        const val ACTION_RESUME_DOWNLOAD = "resume_download"
        const val ACTION_CANCEL_DOWNLOAD = "cancel_download"
        const val ACTION_PAUSE_ALL = "pause_all"
        const val ACTION_RESUME_ALL = "resume_all"
        const val ACTION_CANCEL_ALL = "cancel_all"
        
        const val EXTRA_DOWNLOAD_ID = "download_id"
        
        fun startDownloadService(context: Context) {
            val intent = Intent(context, DownloadService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        fun stopDownloadService(context: Context) {
            val intent = Intent(context, DownloadService::class.java)
            context.stopService(intent)
        }
    }

    inner class DownloadServiceBinder : Binder() {
        fun getService(): DownloadService = this@DownloadService
    }

    private val binder = DownloadServiceBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    private val downloadManager: DownloadManager by inject()
    private val downloadRepository: DownloadRepository by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val logUtils: LogUtils by inject()
    
    private var wakeLock: PowerManager.WakeLock? = null
    private var notificationManager: NotificationManager? = null
    private var isServiceRunning = false
    private var currentNotification: Notification? = null

    override fun onCreate() {
        super.onCreate()
        
        logUtils.info("DownloadService", "Download service created")
        
        // Initialize notification manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        
        // Acquire wake lock for downloads
        acquireWakeLock()
        
        // Start monitoring downloads
        startMonitoringDownloads()
        
        isServiceRunning = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logUtils.debug("DownloadService", "Service started with action: ${intent?.action}")
        
        // Start as foreground service
        if (!isServiceRunning) {
            val notification = createServiceNotification()
            startForeground(NOTIFICATION_ID, notification)
        }
        
        // Handle intent actions
        intent?.let { handleIntent(it) }
        
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        
        logUtils.info("DownloadService", "Download service destroyed")
        
        // Cancel all jobs
        serviceScope.cancel()
        
        // Release wake lock
        releaseWakeLock()
        
        // Pause all downloads
        serviceScope.launch {
            try {
                downloadManager.pauseAllDownloads()
            } catch (e: Exception) {
                Timber.e(e, "Failed to pause downloads on service destroy")
            }
        }
        
        isServiceRunning = false
    }

    /**
     * Handle incoming intents
     */
    private fun handleIntent(intent: Intent) {
        serviceScope.launch {
            try {
                when (intent.action) {
                    ACTION_START_DOWNLOAD -> {
                        val downloadId = intent.getStringExtra(EXTRA_DOWNLOAD_ID)
                        if (downloadId != null) {
                            downloadRepository.resumeDownload(downloadId)
                        }
                    }
                    ACTION_PAUSE_DOWNLOAD -> {
                        val downloadId = intent.getStringExtra(EXTRA_DOWNLOAD_ID)
                        if (downloadId != null) {
                            downloadRepository.pauseDownload(downloadId)
                        }
                    }
                    ACTION_RESUME_DOWNLOAD -> {
                        val downloadId = intent.getStringExtra(EXTRA_DOWNLOAD_ID)
                        if (downloadId != null) {
                            downloadRepository.resumeDownload(downloadId)
                        }
                    }
                    ACTION_CANCEL_DOWNLOAD -> {
                        val downloadId = intent.getStringExtra(EXTRA_DOWNLOAD_ID)
                        if (downloadId != null) {
                            downloadRepository.cancelDownload(downloadId)
                        }
                    }
                    ACTION_PAUSE_ALL -> {
                        downloadManager.pauseAllDownloads()
                    }
                    ACTION_RESUME_ALL -> {
                        downloadManager.resumeAllDownloads()
                    }
                    ACTION_CANCEL_ALL -> {
                        downloadManager.cancelAllDownloads()
                    }
                }
            } catch (e: Exception) {
                logUtils.error("DownloadService", "Failed to handle intent action: ${intent.action}", e)
            }
        }
    }

    /**
     * Start monitoring downloads for notification updates
     */
    private fun startMonitoringDownloads() {
        serviceScope.launch {
            combine(
                downloadRepository.getActiveDownloads(),
                downloadRepository.getOverallProgressFlow()
            ) { activeDownloads, overallProgress ->
                Pair(activeDownloads, overallProgress)
            }.collect { (activeDownloads, overallProgress) ->
                updateServiceNotification(activeDownloads.size, overallProgress)
                
                // Stop service if no active downloads
                if (activeDownloads.isEmpty()) {
                    delay(5000) // Wait 5 seconds before stopping
                    val stillEmpty = downloadRepository.getActiveDownloads()
                    // Re-check if still empty after delay
                    // Implementation would depend on current state
                    if (activeDownloads.isEmpty()) {
                        stopSelfAndNotify()
                    }
                }
            }
        }
    }

    /**
     * Update service notification with current download status
     */
    private fun updateServiceNotification(
        activeDownloads: Int,
        overallProgress: DownloadRepository.OverallProgress
    ) {
        try {
            val notification = createProgressNotification(activeDownloads, overallProgress)
            notificationManager?.notify(NOTIFICATION_ID, notification)
            currentNotification = notification
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to update service notification", e)
        }
    }

    /**
     * Create notification channel for downloads
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifications for download progress"
                setShowBadge(false)
                enableVibration(false)
                setSound(null, null)
            }
            
            notificationManager?.createNotificationChannel(channel)
        }
    }

    /**
     * Create basic service notification
     */
    private fun createServiceNotification(): Notification {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Snaptube Downloads")
            .setContentText("Download service is running")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setShowWhen(false)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    /**
     * Create progress notification with download details
     */
    private fun createProgressNotification(
        activeDownloads: Int,
        overallProgress: DownloadRepository.OverallProgress
    ): Notification {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create action buttons
        val pauseAllIntent = Intent(this, DownloadService::class.java).apply {
            action = ACTION_PAUSE_ALL
        }
        val pauseAllPendingIntent = PendingIntent.getService(
            this,
            1,
            pauseAllIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val cancelAllIntent = Intent(this, DownloadService::class.java).apply {
            action = ACTION_CANCEL_ALL
        }
        val cancelAllPendingIntent = PendingIntent.getService(
            this,
            2,
            cancelAllIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Downloading $activeDownloads item${if (activeDownloads != 1) "s" else ""}")
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setShowWhen(false)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_PROGRESS)

        // Add progress bar if there are active downloads
        if (activeDownloads > 0) {
            val progressText = "${overallProgress.totalProgress}% • ${formatSpeed(overallProgress.averageSpeed)}"
            builder.setContentText(progressText)
                .setProgress(100, overallProgress.totalProgress, false)
                .setSubText(formatDataSize(overallProgress.totalDownloadedSize))
        } else {
            builder.setContentText("No active downloads")
        }

        // Add action buttons for multiple downloads
        if (activeDownloads > 1) {
            builder.addAction(
                android.R.drawable.ic_media_pause,
                "Pause All",
                pauseAllPendingIntent
            ).addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Cancel All", 
                cancelAllPendingIntent
            )
        }

        return builder.build()
    }

    /**
     * Acquire wake lock to keep CPU running during downloads
     */
    private fun acquireWakeLock() {
        try {
            val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "SnaptubeDownloadService::WakeLock"
            ).apply {
                acquire(10 * 60 * 1000L) // 10 minutes maximum
            }
            logUtils.debug("DownloadService", "Wake lock acquired")
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to acquire wake lock", e)
        }
    }

    /**
     * Release wake lock
     */
    private fun releaseWakeLock() {
        try {
            wakeLock?.let { lock ->
                if (lock.isHeld) {
                    lock.release()
                    logUtils.debug("DownloadService", "Wake lock released")
                }
            }
            wakeLock = null
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to release wake lock", e)
        }
    }

    /**
     * Stop service and notify
     */
    private fun stopSelfAndNotify() {
        logUtils.info("DownloadService", "Stopping download service - no active downloads")
        
        // Update notification to show completion
        val completionNotification = createCompletionNotification()
        notificationManager?.notify(NOTIFICATION_ID + 1, completionNotification)
        
        // Stop foreground service
        stopForeground(true)
        stopSelf()
    }

    /**
     * Create completion notification
     */
    private fun createCompletionNotification(): Notification {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Downloads Completed")
            .setContentText("All downloads have finished")
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    /**
     * Format download speed for display
     */
    private fun formatSpeed(speedBytesPerSecond: Double): String {
        if (speedBytesPerSecond <= 0) return "0 B/s"
        
        val units = arrayOf("B/s", "KB/s", "MB/s", "GB/s")
        var speed = speedBytesPerSecond
        var unitIndex = 0
        
        while (speed >= 1024 && unitIndex < units.size - 1) {
            speed /= 1024
            unitIndex++
        }
        
        return "%.1f %s".format(speed, units[unitIndex])
    }

    /**
     * Format data size for display
     */
    private fun formatDataSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB")
        var size = bytes.toDouble()
        var unitIndex = 0
        
        while (size >= 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        
        return "%.1f %s".format(size, units[unitIndex])
    }

    /**
     * Check if service is running
     */
    fun isRunning(): Boolean = isServiceRunning

    /**
     * Get current download count
     */
    suspend fun getActiveDownloadCount(): Int {
        return try {
            downloadRepository.getActiveDownloads().first().size
        } catch (e: Exception) {
            0
        }
    }

    /**
     * Pause all downloads
     */
    suspend fun pauseAllDownloads(): Result<Unit> {
        return try {
            downloadManager.pauseAllDownloads()
            logUtils.info("DownloadService", "All downloads paused")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to pause all downloads", e)
            Result.failure(e)
        }
    }

    /**
     * Resume all downloads
     */
    suspend fun resumeAllDownloads(): Result<Unit> {
        return try {
            downloadManager.resumeAllDownloads()
            logUtils.info("DownloadService", "All downloads resumed")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to resume all downloads", e)
            Result.failure(e)
        }
    }

    /**
     * Cancel all downloads
     */
    suspend fun cancelAllDownloads(): Result<Unit> {
        return try {
            downloadManager.cancelAllDownloads()
            logUtils.info("DownloadService", "All downloads cancelled")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadService", "Failed to cancel all downloads", e)
            Result.failure(e)
        }
    }
}
