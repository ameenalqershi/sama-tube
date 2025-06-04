package com.example.snaptube.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.snaptube.R
import com.example.snaptube.download.Task
import com.example.snaptube.MainActivity

class DownloadNotificationManager(
    private val context: Context
) {
    companion object {
        private const val CHANNEL_ID_DOWNLOADS = "downloads"
        private const val CHANNEL_ID_COMPLETED = "downloads_completed"
        private const val CHANNEL_ID_ERROR = "downloads_error"
        
        private const val NOTIFICATION_ID_PROGRESS = 1000
        private const val NOTIFICATION_ID_COMPLETED_BASE = 2000
        private const val NOTIFICATION_ID_ERROR_BASE = 3000
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            
            // Downloads in progress channel
            val downloadsChannel = NotificationChannel(
                CHANNEL_ID_DOWNLOADS,
                "Downloads",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Shows download progress"
                setShowBadge(false)
            }
            
            // Completed downloads channel
            val completedChannel = NotificationChannel(
                CHANNEL_ID_COMPLETED,
                "Download Completed",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for completed downloads"
            }
            
            // Error channel
            val errorChannel = NotificationChannel(
                CHANNEL_ID_ERROR,
                "Download Errors",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications for download errors"
            }
            
            notificationManager.createNotificationChannels(listOf(
                downloadsChannel,
                completedChannel,
                errorChannel
            ))
        }
    }

    fun showDownloadProgress(task: Task, activeDownloads: Int) {
        val state = task.downloadState
        
        when (state) {
            is Task.DownloadState.Running -> {
                val progress = if (state.progress >= 0) (state.progress * 100).toInt() else -1
                val isIndeterminate = state.progress < 0
                
                val notification = NotificationCompat.Builder(context, CHANNEL_ID_DOWNLOADS)
                    .setContentTitle(task.viewState.title.takeIf { it.isNotBlank() } ?: "Downloading...")
                    .setContentText(
                        if (state.progressText.isNotBlank()) {
                            state.progressText
                        } else if (progress >= 0) {
                            "${progress}%"
                        } else {
                            "Preparing download..."
                        }
                    )
                    .setSmallIcon(R.drawable.ic_download)
                    .setProgress(100, progress, isIndeterminate)
                    .setOngoing(true)
                    .setAutoCancel(false)
                    .setContentIntent(createMainActivityIntent())
                    .apply {
                        if (activeDownloads > 1) {
                            setSubText("$activeDownloads downloads active")
                        }
                        
                        // Add cancel action
                        addAction(
                            R.drawable.ic_cancel,
                            "Cancel",
                            createCancelIntent(task.id)
                        )
                        
                        // Add pause action if supported
                        addAction(
                            R.drawable.ic_pause,
                            "Pause",
                            createPauseIntent(task.id)
                        )
                    }
                    .build()
                
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_PROGRESS, notification)
            }
            
            is Task.DownloadState.FetchingInfo -> {
                val notification = NotificationCompat.Builder(context, CHANNEL_ID_DOWNLOADS)
                    .setContentTitle("Fetching video information...")
                    .setContentText(task.url)
                    .setSmallIcon(R.drawable.ic_download)
                    .setProgress(0, 0, true)
                    .setOngoing(true)
                    .setAutoCancel(false)
                    .setContentIntent(createMainActivityIntent())
                    .apply {
                        if (activeDownloads > 1) {
                            setSubText("$activeDownloads downloads active")
                        }
                        
                        // Add cancel action
                        addAction(
                            R.drawable.ic_cancel,
                            "Cancel",
                            createCancelIntent(task.id)
                        )
                    }
                    .build()
                
                NotificationManagerCompat.from(context).notify(NOTIFICATION_ID_PROGRESS, notification)
            }
            
            else -> {
                // Clear progress notification for non-active states
                if (activeDownloads == 0) {
                    NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID_PROGRESS)
                }
            }
        }
    }

    fun showDownloadCompleted(task: Task) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_COMPLETED)
            .setContentTitle("Download completed")
            .setContentText(task.viewState.title.takeIf { it.isNotBlank() } ?: "Unknown title")
            .setSmallIcon(R.drawable.ic_download_done)
            .setAutoCancel(true)
            .setContentIntent(createMainActivityIntent())
            .apply {
                // Add open file action if file exists
                task.outputPath?.let { path ->
                    addAction(
                        R.drawable.ic_play,
                        "Open",
                        createOpenFileIntent(path)
                    )
                    
                    addAction(
                        R.drawable.ic_share,
                        "Share",
                        createShareFileIntent(path)
                    )
                }
            }
            .build()
        
        val notificationId = NOTIFICATION_ID_COMPLETED_BASE + task.id.hashCode()
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    fun showDownloadError(task: Task, error: Throwable) {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID_ERROR)
            .setContentTitle("Download failed")
            .setContentText(task.viewState.title.takeIf { it.isNotBlank() } ?: "Unknown title")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("${task.viewState.title}\n\nError: ${error.message ?: "Unknown error"}")
            )
            .setSmallIcon(R.drawable.ic_error)
            .setAutoCancel(true)
            .setContentIntent(createMainActivityIntent())
            .addAction(
                R.drawable.ic_refresh,
                "Retry",
                createRetryIntent(task.id)
            )
            .build()
        
        val notificationId = NOTIFICATION_ID_ERROR_BASE + task.id.hashCode()
        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    fun clearProgressNotification() {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID_PROGRESS)
    }

    fun clearAllNotifications() {
        NotificationManagerCompat.from(context).cancelAll()
    }

    private fun createMainActivityIntent(): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createCancelIntent(taskId: String): PendingIntent {
        val intent = Intent(context, DownloadActionReceiver::class.java).apply {
            action = DownloadActionReceiver.ACTION_CANCEL
            putExtra(DownloadActionReceiver.EXTRA_TASK_ID, taskId)
        }
        return PendingIntent.getBroadcast(
            context,
            taskId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createPauseIntent(taskId: String): PendingIntent {
        val intent = Intent(context, DownloadActionReceiver::class.java).apply {
            action = DownloadActionReceiver.ACTION_PAUSE
            putExtra(DownloadActionReceiver.EXTRA_TASK_ID, taskId)
        }
        return PendingIntent.getBroadcast(
            context,
            taskId.hashCode() + 1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createRetryIntent(taskId: String): PendingIntent {
        val intent = Intent(context, DownloadActionReceiver::class.java).apply {
            action = DownloadActionReceiver.ACTION_RETRY
            putExtra(DownloadActionReceiver.EXTRA_TASK_ID, taskId)
        }
        return PendingIntent.getBroadcast(
            context,
            taskId.hashCode() + 2,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createOpenFileIntent(filePath: String): PendingIntent {
        val intent = Intent(context, DownloadActionReceiver::class.java).apply {
            action = DownloadActionReceiver.ACTION_OPEN_FILE
            putExtra(DownloadActionReceiver.EXTRA_FILE_PATH, filePath)
        }
        return PendingIntent.getBroadcast(
            context,
            filePath.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createShareFileIntent(filePath: String): PendingIntent {
        val intent = Intent(context, DownloadActionReceiver::class.java).apply {
            action = DownloadActionReceiver.ACTION_SHARE_FILE
            putExtra(DownloadActionReceiver.EXTRA_FILE_PATH, filePath)
        }
        return PendingIntent.getBroadcast(
            context,
            filePath.hashCode() + 1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}
