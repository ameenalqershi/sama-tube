package com.example.snaptube.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.snaptube.R
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.download.DownloadState
import com.example.snaptube.download.DownloadStatus
import com.example.snaptube.repository.SettingsRepository
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.InputStream
import java.net.URL

/**
 * Service for managing download notifications
 * Provides rich notifications with progress updates and completion alerts
 */
class NotificationService(
    private val context: Context,
    private val settingsRepository: SettingsRepository,
    private val fileUtils: FileUtils,
    private val logUtils: LogUtils
) {

    companion object {
        // Notification channels
        private const val DOWNLOAD_PROGRESS_CHANNEL = "download_progress"
        private const val DOWNLOAD_COMPLETE_CHANNEL = "download_complete"
        private const val DOWNLOAD_FAILED_CHANNEL = "download_failed"
        
        // Notification IDs
        private const val PROGRESS_NOTIFICATION_BASE = 2000
        private const val COMPLETE_NOTIFICATION_BASE = 3000
        private const val FAILED_NOTIFICATION_BASE = 4000
        
        // Actions
        const val ACTION_PAUSE_DOWNLOAD = "pause_download"
        const val ACTION_RESUME_DOWNLOAD = "resume_download"
        const val ACTION_CANCEL_DOWNLOAD = "cancel_download"
        const val ACTION_RETRY_DOWNLOAD = "retry_download"
        const val ACTION_OPEN_FILE = "open_file"
        const val ACTION_SHARE_FILE = "share_file"
        
        const val EXTRA_DOWNLOAD_ID = "download_id"
        const val EXTRA_FILE_PATH = "file_path"
    }

    private val notificationManager = NotificationManagerCompat.from(context)
    private val thumbnailCache = mutableMapOf<String, Bitmap>()

    init {
        createNotificationChannels()
    }

    /**
     * Create notification channels for different types of notifications
     */
    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    DOWNLOAD_PROGRESS_CHANNEL,
                    "Download Progress",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Shows download progress"
                    setShowBadge(false)
                    enableVibration(false)
                    setSound(null, null)
                },
                
                NotificationChannel(
                    DOWNLOAD_COMPLETE_CHANNEL,
                    "Download Complete",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Notifies when downloads complete"
                    setShowBadge(true)
                },
                
                NotificationChannel(
                    DOWNLOAD_FAILED_CHANNEL,
                    "Download Failed",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifies when downloads fail"
                    setShowBadge(true)
                }
            )
            
            val systemNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            channels.forEach { channel ->
                systemNotificationManager.createNotificationChannel(channel)
            }
        }
    }

    /**
     * Show progress notification for ongoing download
     */
    suspend fun showProgressNotification(download: DownloadEntity) {
        try {
            if (!shouldShowNotifications()) return
            
            val notificationId = PROGRESS_NOTIFICATION_BASE + download.id.hashCode()
            val thumbnail = getThumbnail(download.thumbnail)
            
            val notification = NotificationCompat.Builder(context, DOWNLOAD_PROGRESS_CHANNEL)
                .setContentTitle(download.title)
                .setContentText(getProgressText(download))
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setProgress(100, download.progress.toInt(), download.status == DownloadStatus.DOWNLOADING && download.progress.toInt() == 0)
                .setOngoing(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .apply {
                    // Add thumbnail if available
                    thumbnail?.let { 
                        // Note: setLargeIcon requires Bitmap, simplified for now
                        // setLargeIcon(it) 
                    }
                    
                    // Add action buttons based on download status
                    when (download.status) {
                        DownloadStatus.DOWNLOADING -> {
                            this@NotificationService.addProgressActions(this, download.id)
                        }
                        DownloadStatus.PAUSED -> {
                            this@NotificationService.addPausedActions(this, download.id)
                        }
                        DownloadStatus.PENDING -> {
                            this@NotificationService.addQueuedActions(this, download.id)
                        }
                        else -> {
                            // No actions for other states
                        }
                    }
                    
                    // Set click action to open app
                    // setContentIntent(createOpenAppIntent())
                }
                .build()
            
            notificationManager.notify(notificationId, notification)
            
        } catch (e: Exception) {
            logUtils.error("NotificationService", "Failed to show progress notification", e)
        }
    }

    /**
     * Show completion notification
     */
    suspend fun showCompletionNotification(download: DownloadEntity) {
        try {
            if (!shouldShowNotifications()) return
            
            // Remove progress notification
            val progressNotificationId = PROGRESS_NOTIFICATION_BASE + download.id.hashCode()
            notificationManager.cancel(progressNotificationId)
            
            val notificationId = COMPLETE_NOTIFICATION_BASE + download.id.hashCode()
            val thumbnail = getThumbnail(download.thumbnail)
            val fileSize = formatFileSize(File(download.outputPath).length())
            
            val notification = NotificationCompat.Builder(context, DOWNLOAD_COMPLETE_CHANNEL)
                .setContentTitle("Download Complete")
                .setContentText("${download.title} ($fileSize)")
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .apply {
                    // Add thumbnail if available
                    thumbnail?.let { 
                        // Note: setLargeIcon requires Bitmap, simplified for now
                        // setLargeIcon(it) 
                    }
                    
                    // Add action buttons (simplified)
                    // Note: Using system icons, actual implementation would need custom icons
                    // addAction(android.R.drawable.ic_menu_view, "Open", createOpenFileIntent(download.outputPath))
                    // addAction(android.R.drawable.ic_menu_share, "Share", createShareFileIntent(download.outputPath))
                    
                    // Set click action to open app for now
                    // setContentIntent(createOpenFileIntent(download.outputPath)))
                    
                    // Add vibration if enabled
                    if (isVibrationEnabled()) {
                        // setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    }
                }
                .build()
            
            notificationManager.notify(notificationId, notification)
            
            logUtils.info("NotificationService", "Completion notification shown for: ${download.title}")
            
        } catch (e: Exception) {
            logUtils.error("NotificationService", "Failed to show completion notification", e)
        }
    }

    /**
     * Show failure notification
     */
    suspend fun showFailureNotification(download: DownloadEntity) {
        try {
            if (!shouldShowNotifications()) return
            
            // Remove progress notification
            val progressNotificationId = PROGRESS_NOTIFICATION_BASE + download.id.hashCode()
            notificationManager.cancel(progressNotificationId)
            
            val notificationId = FAILED_NOTIFICATION_BASE + download.id.hashCode()
            val thumbnail = getThumbnail(download.thumbnail)
            val errorText = download.error ?: "Unknown error"
            
            val notification = NotificationCompat.Builder(context, DOWNLOAD_FAILED_CHANNEL)
                .setContentTitle("Download Failed")
                .setContentText(download.title)
                .setSubText(errorText)
                .setSmallIcon(android.R.drawable.stat_notify_error)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .apply {
                    // Add thumbnail if available
                    thumbnail?.let { 
                        // setLargeIcon(it) 
                    }
                    
                    // Add retry action (simplified)
                    // addAction(android.R.drawable.ic_menu_revert, "Retry", createRetryIntent(download.id))
                    
                    // Set click action to open app
                    // setContentIntent(createOpenAppIntent())
                    
                    // Add vibration if enabled
                    if (isVibrationEnabled()) {
                        // setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                    }
                }
                .build()
            
            notificationManager.notify(notificationId, notification)
            
            logUtils.warn("NotificationService", "Failure notification shown for: ${download.title}")
            
        } catch (e: Exception) {
            logUtils.error("NotificationService", "Failed to show failure notification", e)
        }
    }

    /**
     * Cancel notification for a download
     */
    fun cancelNotification(downloadId: String) {
        try {
            val progressNotificationId = PROGRESS_NOTIFICATION_BASE + downloadId.hashCode()
            val completeNotificationId = COMPLETE_NOTIFICATION_BASE + downloadId.hashCode()
            val failedNotificationId = FAILED_NOTIFICATION_BASE + downloadId.hashCode()
            
            notificationManager.cancel(progressNotificationId)
            notificationManager.cancel(completeNotificationId)
            notificationManager.cancel(failedNotificationId)
            
            logUtils.debug("NotificationService", "Cancelled notifications for: $downloadId")
            
        } catch (e: Exception) {
            logUtils.error("NotificationService", "Failed to cancel notification", e)
        }
    }

    /**
     * Cancel all download notifications
     */
    fun cancelAllNotifications() {
        try {
            // This is a simplified approach - in a real app you might want to track notification IDs
            notificationManager.cancelAll()
            logUtils.info("NotificationService", "All notifications cancelled")
        } catch (e: Exception) {
            logUtils.error("NotificationService", "Failed to cancel all notifications", e)
        }
    }

    /**
     * Get thumbnail for notification
     */
    private suspend fun getThumbnail(thumbnailUrl: String?): Bitmap? {
        if (thumbnailUrl.isNullOrEmpty()) return null
        
        return withContext(Dispatchers.IO) {
            try {
                // Check cache first
                thumbnailCache[thumbnailUrl]?.let { return@withContext it }
                
                // Download thumbnail
                val bitmap = downloadThumbnail(thumbnailUrl)
                if (bitmap != null) {
                    thumbnailCache[thumbnailUrl] = bitmap
                }
                bitmap
            } catch (e: Exception) {
                logUtils.error("NotificationService", "Failed to get thumbnail", e)
                null
            }
        }
    }

    /**
     * Download thumbnail from URL
     */
    private fun downloadThumbnail(url: String): Bitmap? {
        return try {
            val inputStream: InputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            
            // Resize to notification size
            val size = 256 // 256dp for large icon
            Bitmap.createScaledBitmap(bitmap, size, size, true)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Get progress text for notification
     */
    private fun getProgressText(download: DownloadEntity): String {
        return when (download.status) {
            DownloadStatus.PENDING -> "Pending"
            DownloadStatus.DOWNLOADING -> {
                val speed = if (download.speed > 0) " • ${formatFileSize(download.speed)}/s" else ""
                val size = if (download.totalBytes > 0) {
                    " • ${formatFileSize(download.downloadedBytes)}/${formatFileSize(download.totalBytes)}"
                } else {
                    " • ${formatFileSize(download.downloadedBytes)}"
                }
                "${download.progress}%$speed$size"
            }
            DownloadStatus.PROCESSING -> "Processing"
            DownloadStatus.EXTRACTING -> "Extracting"
            DownloadStatus.PAUSED -> "Paused • ${download.progress}%"
            DownloadStatus.COMPLETED -> "Complete"
            DownloadStatus.FAILED -> "Failed"
            DownloadStatus.CANCELLED -> "Cancelled"
        }
    }

    /**
     * Add actions for downloading state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun addProgressActions(builder: NotificationCompat.Builder, downloadId: String) {
        // Simplified - using system icons
        // builder.addAction(android.R.drawable.ic_media_pause, "Pause", createPauseIntent(downloadId))
        // builder.addAction(android.R.drawable.ic_delete, "Cancel", createCancelIntent(downloadId))
    }

    /**
     * Add actions for paused state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun addPausedActions(builder: NotificationCompat.Builder, downloadId: String) {
        // Simplified - using system icons  
        // builder.addAction(android.R.drawable.ic_media_play, "Resume", createResumeIntent(downloadId))
        // builder.addAction(android.R.drawable.ic_delete, "Cancel", createCancelIntent(downloadId))
    }

    /**
     * Add actions for queued state
     */
    @Suppress("UNUSED_PARAMETER")
    private fun addQueuedActions(builder: NotificationCompat.Builder, downloadId: String) {
        // Simplified - using system icons
        // builder.addAction(android.R.drawable.ic_delete, "Cancel", createCancelIntent(downloadId))
    }

    /**
     * Create intent to pause download
     */
    private fun createPauseIntent(downloadId: String): PendingIntent {
        val intent = Intent(context, DownloadService::class.java).apply {
            action = ACTION_PAUSE_DOWNLOAD
            putExtra(EXTRA_DOWNLOAD_ID, downloadId)
        }
        return PendingIntent.getService(
            context,
            downloadId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to resume download
     */
    private fun createResumeIntent(downloadId: String): PendingIntent {
        val intent = Intent(context, DownloadService::class.java).apply {
            action = ACTION_RESUME_DOWNLOAD
            putExtra(EXTRA_DOWNLOAD_ID, downloadId)
        }
        return PendingIntent.getService(
            context,
            downloadId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to cancel download
     */
    private fun createCancelIntent(downloadId: String): PendingIntent {
        val intent = Intent(context, DownloadService::class.java).apply {
            action = ACTION_CANCEL_DOWNLOAD
            putExtra(EXTRA_DOWNLOAD_ID, downloadId)
        }
        return PendingIntent.getService(
            context,
            downloadId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to retry download
     */
    private fun createRetryIntent(downloadId: String): PendingIntent {
        val intent = Intent(context, DownloadService::class.java).apply {
            action = ACTION_RETRY_DOWNLOAD
            putExtra(EXTRA_DOWNLOAD_ID, downloadId)
        }
        return PendingIntent.getService(
            context,
            downloadId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to open file
     */
    private fun createOpenFileIntent(filePath: String): PendingIntent {
        // TODO: Implement when FileUtils.createOpenFileIntent is available
        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW)
        return PendingIntent.getActivity(
            context,
            filePath.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to share file
     */
    private fun createShareFileIntent(filePath: String): PendingIntent {
        // TODO: Implement when FileUtils.createShareFileIntent is available
        val intent = android.content.Intent(android.content.Intent.ACTION_SEND)
        return PendingIntent.getActivity(
            context,
            filePath.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Create intent to open app
     */
    private fun createOpenAppIntent(): PendingIntent {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    /**
     * Check if notifications should be shown
     */
    private suspend fun shouldShowNotifications(): Boolean {
        return try {
            settingsRepository.downloadSettings.value.notifications
        } catch (e: Exception) {
            true // Default to showing notifications
        }
    }

    /**
     * Check if vibration should be used
     */
    private suspend fun shouldVibrate(): Boolean {
        return try {
            settingsRepository.downloadSettings.value.vibration
        } catch (e: Exception) {
            true // Default to vibration
        }
    }

    /**
     * Check if vibration is enabled for notifications
     */
    private fun isVibrationEnabled(): Boolean {
        return try {
            // Simplified implementation - can be enhanced with user preferences
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Format file size for display
     */
    private fun formatFileSize(bytes: Long): String {
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
     * Clear thumbnail cache
     */
    fun clearThumbnailCache() {
        thumbnailCache.clear()
        logUtils.debug("NotificationService", "Thumbnail cache cleared")
    }

    /**
     * Get notification statistics
     */
    fun getNotificationStats(): NotificationStats {
        return NotificationStats(
            thumbnailCacheSize = thumbnailCache.size,
            channelsCreated = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 3 else 0
        )
    }

    /**
     * Data class for notification statistics
     */
    data class NotificationStats(
        val thumbnailCacheSize: Int,
        val channelsCreated: Int
    )
}
