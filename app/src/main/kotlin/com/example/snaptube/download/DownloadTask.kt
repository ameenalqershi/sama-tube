package com.example.snaptube.download

import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.database.entities.DownloadEntity
import java.util.*

/**
 * نموذج مهمة التحميل
 * يحتوي على جميع المعلومات اللازمة لتتبع حالة التحميل
 */
data class DownloadTask(
    val id: String,
    val url: String,
    val title: String,
    val downloadFormat: DownloadFormat,
    val downloadPath: String,
    val audioOnly: Boolean = false,
    val totalSize: Long = 0,
    var status: DownloadStatus = DownloadStatus.PENDING,
    var progress: Int = 0,
    var downloadedBytes: Long = 0,
    var speed: Long = 0, // بايت في الثانية
    var filePath: String = "",
    var errorMessage: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    var startedAt: Long = 0,
    var completedAt: Long = 0
) {
    
    /**
     * حساب الوقت المتبقي للتحميل (بالثواني)
     */
    fun getEstimatedTimeRemaining(): Long {
        return if (speed > 0 && totalSize > 0) {
            val remainingBytes = totalSize - downloadedBytes
            remainingBytes / speed
        } else {
            -1 // غير معروف
        }
    }
    
    /**
     * حساب النسبة المئوية للتحميل
     */
    fun getProgressPercentage(): Int {
        return if (totalSize > 0) {
            ((downloadedBytes.toDouble() / totalSize.toDouble()) * 100).toInt()
        } else {
            progress
        }
    }
    
    /**
     * تحقق ما إذا كان التحميل نشطاً
     */
    fun isActive(): Boolean {
        return status in listOf(
            DownloadStatus.PENDING,
            DownloadStatus.DOWNLOADING,
            DownloadStatus.PROCESSING
        )
    }
    
    /**
     * تحقق ما إذا كان التحميل مكتملاً
     */
    fun isCompleted(): Boolean {
        return status == DownloadStatus.COMPLETED
    }
    
    /**
     * تحقق ما إذا كان التحميل فاشلاً
     */
    fun isFailed(): Boolean {
        return status == DownloadStatus.FAILED
    }
    
    /**
     * تحقق ما إذا كان التحميل متوقفاً
     */
    fun isPaused(): Boolean {
        return status == DownloadStatus.PAUSED
    }
    
    /**
     * تحقق ما إذا كان التحميل ملغياً
     */
    fun isCancelled(): Boolean {
        return status == DownloadStatus.CANCELLED
    }
    
    /**
     * الحصول على اسم الملف المقترح
     */
    fun getSuggestedFileName(): String {
        val extension = downloadFormat.extension.ifEmpty { 
            if (audioOnly) "mp3" else "mp4" 
        }
        
        val cleanTitle = title
            .replace(Regex("[^a-zA-Z0-9\\s\\-_\\u0600-\\u06FF]"), "") // إزالة الأحرف الخاصة والاحتفاظ بالعربية
            .replace(Regex("\\s+"), "_") // استبدال المسافات بـ _
            .take(50) // قصر الاسم
        
        return "${cleanTitle}.${extension}"
    }
    
    /**
     * تحويل إلى كيان قاعدة البيانات
     */
    fun toEntity(): DownloadEntity {
        return DownloadEntity(
            id = id,
            url = url,
            title = title,
            downloadPath = downloadPath,
            filePath = filePath,
            audioOnly = audioOnly,
            totalSize = totalSize,
            downloadedBytes = downloadedBytes,
            progress = progress.toFloat(),
            speed = speed,
            status = status,
            errorMessage = errorMessage,
            formatId = downloadFormat.formatId,
            quality = downloadFormat.quality,
            extension = downloadFormat.extension,
            createdAt = Date(createdAt),
            startedAt = startedAt,
            completedAt = Date(completedAt)
        )
    }
    
    /**
     * تحديث معلومات التقدم
     */
    fun updateProgress(
        newProgress: Int,
        newDownloadedBytes: Long,
        newSpeed: Long
    ): DownloadTask {
        return copy(
            progress = newProgress,
            downloadedBytes = newDownloadedBytes,
            speed = newSpeed
        )
    }
    
    /**
     * تحديث الحالة
     */
    fun updateStatus(newStatus: DownloadStatus, error: String = ""): DownloadTask {
        val now = System.currentTimeMillis()
        return copy(
            status = newStatus,
            errorMessage = error,
            startedAt = if (newStatus == DownloadStatus.DOWNLOADING && startedAt == 0L) now else startedAt,
            completedAt = if (newStatus == DownloadStatus.COMPLETED) now else completedAt
        )
    }
    
    companion object {
        /**
         * إنشاء مهمة تحميل من كيان قاعدة البيانات
         */
        fun fromEntity(entity: DownloadEntity): DownloadTask {
            return DownloadTask(
                id = entity.id,
                url = entity.url,
                title = entity.title,
                downloadFormat = DownloadFormat(
                    formatId = entity.formatId,
                    extension = entity.extension,
                    quality = entity.quality
                ),
                downloadPath = entity.downloadPath,
                audioOnly = entity.audioOnly,
                totalSize = entity.totalSize,
                status = entity.status,
                progress = entity.progress.toInt(),
                downloadedBytes = entity.downloadedBytes,
                speed = entity.speed,
                filePath = entity.filePath,
                errorMessage = entity.errorMessage,
                createdAt = entity.createdAt.time,
                startedAt = entity.startedAt,
                completedAt = entity.completedAt?.time ?: 0L
            )
        }
    }
}

/**
 * أنواع أحداث التحميل
 */
sealed class DownloadEvent {
    data class ProgressUpdate(
        val downloadId: String,
        val progress: Int,
        val downloadedBytes: Long,
        val totalBytes: Long,
        val speed: Long
    ) : DownloadEvent()
    
    data class StatusChanged(
        val downloadId: String,
        val oldStatus: DownloadStatus,
        val newStatus: DownloadStatus,
        val errorMessage: String = ""
    ) : DownloadEvent()
    
    data class DownloadStarted(
        val downloadId: String,
        val title: String
    ) : DownloadEvent()
    
    data class DownloadCompleted(
        val downloadId: String,
        val filePath: String,
        val duration: Long
    ) : DownloadEvent()
    
    data class DownloadFailed(
        val downloadId: String,
        val errorMessage: String,
        val exception: Throwable?
    ) : DownloadEvent()
}

/**
 * معلومات إحصائيات التحميل
 */
data class DownloadStats(
    val totalDownloads: Int = 0,
    val completedDownloads: Int = 0,
    val failedDownloads: Int = 0,
    val activeDownloads: Int = 0,
    val totalBytesDownloaded: Long = 0,
    val averageSpeed: Long = 0,
    val totalDuration: Long = 0
) {
    
    fun getSuccessRate(): Double {
        return if (totalDownloads > 0) {
            (completedDownloads.toDouble() / totalDownloads.toDouble()) * 100
        } else {
            0.0
        }
    }
    
    fun getFailureRate(): Double {
        return if (totalDownloads > 0) {
            (failedDownloads.toDouble() / totalDownloads.toDouble()) * 100
        } else {
            0.0
        }
    }
}

/**
 * إعدادات التحميل
 */
data class DownloadSettings(
    val maxConcurrentDownloads: Int = 3,
    val defaultDownloadPath: String = "",
    val defaultQuality: String = "720p",
    val preferAudioOnly: Boolean = false,
    val autoRetryFailedDownloads: Boolean = true,
    val maxRetryAttempts: Int = 3,
    val retryDelay: Long = 5000, // 5 ثوان
    val downloadSpeedLimit: Long = 0, // 0 = بدون حد
    val enableNotifications: Boolean = true,
    val deleteAfterDays: Int = 0, // 0 = عدم الحذف التلقائي
    val createSubfolders: Boolean = true,
    val subfoldersPattern: String = "{uploader}/{title}"
)
