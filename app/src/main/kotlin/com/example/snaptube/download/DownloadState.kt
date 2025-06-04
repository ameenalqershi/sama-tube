package com.example.snaptube.download

/**
 * حالات التحميل البسيطة للحفظ في قاعدة البيانات
 */
enum class DownloadStatus {
    PENDING,
    DOWNLOADING,
    PROCESSING,
    EXTRACTING,
    COMPLETED,
    FAILED,
    CANCELLED,
    PAUSED
}

/**
 * حالات التحميل المختلفة التي يمكن أن تحدث أثناء عملية التحميل
 */
sealed class DownloadState {
    
    /**
     * تم بدء التحميل
     */
    data class Started(
        val downloadId: String,
        val title: String
    ) : DownloadState()
    
    /**
     * التحميل قيد التقدم
     */
    data class Progress(
        val downloadId: String,
        val progress: Int,
        val downloadedBytes: Long = 0,
        val totalBytes: Long = 0,
        val speed: Long = 0, // بايت/ثانية
        val eta: Long = 0 // الوقت المتبقي بالثواني
    ) : DownloadState()
    
    /**
     * تم إيقاف التحميل مؤقتاً
     */
    data class Paused(
        val downloadId: String
    ) : DownloadState()
    
    /**
     * تم استئناف التحميل
     */
    data class Resumed(
        val downloadId: String
    ) : DownloadState()
    
    /**
     * تم إكمال التحميل بنجاح
     */
    data class Completed(
        val downloadId: String,
        val filePath: String,
        val duration: Long = 0
    ) : DownloadState()
    
    /**
     * فشل التحميل
     */
    data class Failed(
        val downloadId: String,
        val errorMessage: String,
        val exception: Throwable? = null
    ) : DownloadState()
    
    /**
     * تم إلغاء التحميل
     */
    data class Cancelled(
        val downloadId: String
    ) : DownloadState()
    
    /**
     * تم حذف التحميل
     */
    data class Deleted(
        val downloadId: String
    ) : DownloadState()
    
    /**
     * جاري معالجة الفيديو (مثل تحويل التنسيق)
     */
    data class Processing(
        val downloadId: String,
        val operation: String // نوع العملية (convert, merge, etc.)
    ) : DownloadState()
    
    /**
     * في انتظار بدء التحميل
     */
    data class Queued(
        val downloadId: String,
        val position: Int // الموضع في قائمة الانتظار
    ) : DownloadState()
    
    /**
     * تحديث معلومات الفيديو
     */
    data class VideoInfoUpdated(
        val downloadId: String,
        val title: String,
        val duration: Long,
        val thumbnail: String
    ) : DownloadState()
    
    /**
     * خطأ في الشبكة
     */
    data class NetworkError(
        val downloadId: String,
        val retryAttempt: Int,
        val maxRetries: Int
    ) : DownloadState()
    
    /**
     * خطأ في التخزين
     */
    data class StorageError(
        val downloadId: String,
        val availableSpace: Long,
        val requiredSpace: Long
    ) : DownloadState()
    
    /**
     * تحذير عام
     */
    data class Warning(
        val downloadId: String,
        val message: String
    ) : DownloadState()
    
    // دوال مساعدة للتحقق من نوع الحالة
    
    fun isActive(): Boolean {
        return this is Started || this is Progress || this is Processing || this is Queued
    }
    
    fun isCompleted(): Boolean {
        return this is Completed
    }
    
    fun isFailed(): Boolean {
        return this is Failed || this is NetworkError || this is StorageError
    }
    
    fun isPaused(): Boolean {
        return this is Paused
    }
    
    fun isCancelled(): Boolean {
        return this is Cancelled
    }
    
    fun isDeleted(): Boolean {
        return this is Deleted
    }
    
    fun getDisplayMessage(): String {
        return when (this) {
            is Started -> "بدء التحميل: $title"
            is Progress -> "التحميل قيد التقدم: $progress%"
            is Paused -> "تم إيقاف التحميل مؤقتاً"
            is Resumed -> "تم استئناف التحميل"
            is Completed -> "تم إكمال التحميل"
            is Failed -> "فشل التحميل: $errorMessage"
            is Cancelled -> "تم إلغاء التحميل"
            is Deleted -> "تم حذف التحميل"
            is Processing -> "جاري المعالجة: $operation"
            is Queued -> "في قائمة الانتظار (الموضع: $position)"
            is VideoInfoUpdated -> "تم تحديث معلومات الفيديو"
            is NetworkError -> "خطأ في الشبكة (المحاولة $retryAttempt من $maxRetries)"
            is StorageError -> "مساحة تخزين غير كافية"
            is Warning -> "تحذير: $message"
        }
    }
    
    companion object {
        /**
         * إنشاء حالة تقدم مع حساب تلقائي للسرعة والوقت المتبقي
         */
        fun createProgress(
            downloadId: String,
            progress: Int,
            downloadedBytes: Long,
            totalBytes: Long,
            startTime: Long
        ): Progress {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime
            
            val speed = if (elapsedTime > 0) {
                (downloadedBytes * 1000) / elapsedTime // بايت/ثانية
            } else {
                0L
            }
            
            val eta = if (speed > 0 && totalBytes > downloadedBytes) {
                (totalBytes - downloadedBytes) / speed // ثواني
            } else {
                0L
            }
            
            return Progress(
                downloadId = downloadId,
                progress = progress,
                downloadedBytes = downloadedBytes,
                totalBytes = totalBytes,
                speed = speed,
                eta = eta
            )
        }
    }
}
