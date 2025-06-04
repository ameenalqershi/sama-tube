package com.example.snaptube.download

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit
import com.example.snaptube.models.DownloadFormat

/**
 * عامل التحميل في الخلفية باستخدام WorkManager
 * يضمن استمرار التحميلات حتى عند إغلاق التطبيق
 */
class DownloadWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {
    
    private val TAG = "DownloadWorker"
    
    // حقن التبعيات
    private val downloadManager: DownloadManager by inject()
    
    companion object {
        const val WORK_NAME_PREFIX = "download_work_"
        const val KEY_DOWNLOAD_ID = "download_id"
        const val KEY_URL = "url"
        const val KEY_FORMAT_ID = "format_id"
        const val KEY_DOWNLOAD_PATH = "download_path"
        const val KEY_AUDIO_ONLY = "audio_only"
        const val KEY_RETRY_COUNT = "retry_count"
        
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_DELAY_MINUTES = 5L
        
        /**
         * إنشاء وجدولة مهمة تحميل جديدة
         */
        fun scheduleDownload(
            context: Context,
            downloadId: String,
            url: String,
            formatId: String,
            downloadPath: String,
            audioOnly: Boolean = false
        ) {
            val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
                .setInputData(
                    workDataOf(
                        KEY_DOWNLOAD_ID to downloadId,
                        KEY_URL to url,
                        KEY_FORMAT_ID to formatId,
                        KEY_DOWNLOAD_PATH to downloadPath,
                        KEY_AUDIO_ONLY to audioOnly,
                        KEY_RETRY_COUNT to 0
                    )
                )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresStorageNotLow(true)
                        .build()
                )
                .addTag(downloadId)
                .build()
            
            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    WORK_NAME_PREFIX + downloadId,
                    ExistingWorkPolicy.REPLACE,
                    workRequest
                )
            
            Log.d("DownloadWorker", "تم جدولة مهمة التحميل: $downloadId")
        }
        
        /**
         * إلغاء مهمة تحميل
         */
        fun cancelDownload(context: Context, downloadId: String) {
            WorkManager.getInstance(context)
                .cancelUniqueWork(WORK_NAME_PREFIX + downloadId)
            
            Log.d("DownloadWorker", "تم إلغاء مهمة التحميل: $downloadId")
        }
        
        /**
         * إلغاء جميع مهام التحميل
         */
        fun cancelAllDownloads(context: Context) {
            WorkManager.getInstance(context)
                .cancelAllWorkByTag("download")
            
            Log.d("DownloadWorker", "تم إلغاء جميع مهام التحميل")
        }
    }
    
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "بدء تنفيذ مهمة التحميل")
                
                // استخراج المعطيات
                val downloadId = inputData.getString(KEY_DOWNLOAD_ID)
                    ?: return@withContext Result.failure()
                
                val url = inputData.getString(KEY_URL)
                    ?: return@withContext Result.failure()
                
                val formatId = inputData.getString(KEY_FORMAT_ID)
                    ?: return@withContext Result.failure()
                
                val downloadPath = inputData.getString(KEY_DOWNLOAD_PATH)
                    ?: return@withContext Result.failure()
                
                val audioOnly = inputData.getBoolean(KEY_AUDIO_ONLY, false)
                val retryCount = inputData.getInt(KEY_RETRY_COUNT, 0)
                
                Log.d(TAG, "تنفيذ التحميل: $downloadId")
                
                // تحديث إشعار التقدم
                setForeground(createForegroundInfo(downloadId, "جاري التحميل..."))
                
                // تنفيذ التحميل
                val result = executeDownload(downloadId, url, formatId, downloadPath, audioOnly)
                
                when {
                    result.isSuccess -> {
                        Log.d(TAG, "تم إكمال التحميل بنجاح: $downloadId")
                        Result.success()
                    }
                    
                    retryCount < MAX_RETRY_COUNT -> {
                        Log.w(TAG, "فشل التحميل، المحاولة ${retryCount + 1}: $downloadId")
                        
                        // جدولة إعادة المحاولة
                        scheduleRetry(downloadId, url, formatId, downloadPath, audioOnly, retryCount + 1)
                        Result.retry()
                    }
                    
                    else -> {
                        Log.e(TAG, "فشل التحميل نهائياً بعد $MAX_RETRY_COUNT محاولات: $downloadId")
                        Result.failure(
                            workDataOf(
                                "error" to "فشل التحميل بعد عدة محاولات",
                                "download_id" to downloadId
                            )
                        )
                    }
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "خطأ في تنفيذ مهمة التحميل", e)
                Result.failure(
                    workDataOf(
                        "error" to (e.message ?: "خطأ غير معروف"),
                        "download_id" to inputData.getString(KEY_DOWNLOAD_ID)
                    )
                )
            }
        }
    }
    
    /**
     * تنفيذ عملية التحميل الفعلية
     */
    @Suppress("UNUSED_PARAMETER")
    private suspend fun executeDownload(
        _downloadId: String,
        url: String,
        formatId: String,
        downloadPath: String,
        audioOnly: Boolean
    ): kotlin.Result<String> {
        // Delegate to DownloadManager which returns kotlin.Result<String>
        return downloadManager.startDownload(
                url = url,
            selectedFormat = createDownloadFormat(formatId, audioOnly),
                downloadPath = downloadPath,
                audioOnly = audioOnly
            )
    }
    
    /**
     * إنشاء DownloadFormat من formatId
     */
    private fun createDownloadFormat(formatId: String, audioOnly: Boolean): DownloadFormat {
        return if (audioOnly) {
            DownloadFormat(
                formatId = formatId,
                extension = "mp3",
                quality = "audio",
                hasAudio = true,
                hasVideo = false,
                acodec = "mp3"
            )
        } else {
            DownloadFormat(
                formatId = formatId,
                extension = "mp4",
                quality = "720p",
                hasAudio = true,
                hasVideo = true,
                vcodec = "h264",
                acodec = "aac"
            )
        }
    }
    
    /**
     * جدولة إعادة المحاولة
     */
    private fun scheduleRetry(
        downloadId: String,
        url: String,
        formatId: String,
        downloadPath: String,
        audioOnly: Boolean,
        retryCount: Int
    ) {
        val retryRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                workDataOf(
                    KEY_DOWNLOAD_ID to downloadId,
                    KEY_URL to url,
                    KEY_FORMAT_ID to formatId,
                    KEY_DOWNLOAD_PATH to downloadPath,
                    KEY_AUDIO_ONLY to audioOnly,
                    KEY_RETRY_COUNT to retryCount
                )
            )
            .setInitialDelay(RETRY_DELAY_MINUTES, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .addTag(downloadId)
            .build()
        
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                WORK_NAME_PREFIX + downloadId,
                ExistingWorkPolicy.REPLACE,
                retryRequest
            )
    }
    
    /**
     * إنشاء معلومات الإشعار للعمل في المقدمة
     */
    private fun createForegroundInfo(downloadId: String, message: String): ForegroundInfo {
        // إنشاء قناة الإشعارات إذا لم تكن موجودة
        createNotificationChannel()
        
        val notification = androidx.core.app.NotificationCompat.Builder(
            context,
            "download_channel"
        )
            .setContentTitle("تحميل Snaptube")
            .setContentText(message)
            .setSmallIcon(android.R.drawable.stat_sys_download)
            .setOngoing(true)
            .setProgress(0, 0, true)
            .build()
        
        return ForegroundInfo(downloadId.hashCode(), notification)
    }
    
    /**
     * إنشاء قناة الإشعارات
     */
    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                "download_channel",
                "تحميلات Snaptube",
                android.app.NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "إشعارات تقدم التحميل"
                setShowBadge(false)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) 
                as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
