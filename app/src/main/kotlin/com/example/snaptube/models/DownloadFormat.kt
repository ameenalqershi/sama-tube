package com.example.snaptube.models

import kotlinx.serialization.Serializable

/**
 * نموذج تنسيق التحميل
 * يحتوي على معلومات تنسيق الفيديو/الصوت المتاح للتحميل
 */
@Serializable
data class DownloadFormat(
    val formatId: String,
    val extension: String,
    val quality: String = "",
    val height: Int? = null,
    val width: Int? = null,
    val fps: Int? = null,
    val vcodec: String? = null, // Video codec
    val acodec: String? = null, // Audio codec
    val videoBitrate: Int? = null, // Video bitrate (kbps)
    val audioBitrate: Int? = null, // Audio bitrate (kbps)
    val fileSize: Long? = null, // حجم الملف بالبايت
    val hasVideo: Boolean = true,
    val hasAudio: Boolean = true,
    val isDefault: Boolean = false,
    val container: String = "", // الحاوية (mp4, webm, etc.)
    val protocol: String = "https", // البروتوكول
    val url: String = "", // رابط التحميل المباشر
    val fragmentCount: Int? = null, // عدد القطع للتحميل المجزأ
    val note: String = "" // ملاحظات إضافية
) {
    
    /**
     * تحديد ما إذا كان التنسيق للفيديو فقط
     */
    fun isVideoOnly(): Boolean {
        return hasVideo && !hasAudio
    }
    
    /**
     * تحديد ما إذا كان التنسيق للصوت فقط
     */
    fun isAudioOnly(): Boolean {
        return hasAudio && !hasVideo
    }
    
    /**
     * تحديد ما إذا كان التنسيق مدمجاً (فيديو + صوت)
     */
    fun isCombined(): Boolean {
        return hasVideo && hasAudio
    }
    
    /**
     * الحصول على وصف الجودة
     */
    fun getQualityDescription(): String {
        return when {
            isAudioOnly() -> {
                val bitrate = audioBitrate?.let { "${it}kbps" } ?: ""
                val codec = acodec?.uppercase() ?: ""
                "صوت $codec $bitrate".trim()
            }
            
            isVideoOnly() -> {
                val resolution = getResolutionString()
                val codec = vcodec?.uppercase() ?: ""
                "$resolution $codec".trim()
            }
            
            else -> {
                val resolution = getResolutionString()
                val vCodec = vcodec?.uppercase() ?: ""
                val aCodec = acodec?.uppercase() ?: ""
                "$resolution $vCodec+$aCodec".trim()
            }
        }
    }
    
    /**
     * الحصول على نص الدقة
     */
    fun getResolutionString(): String {
        return when {
            height != null && width != null -> "${width}x${height}"
            height != null -> "${height}p"
            quality.isNotEmpty() -> quality
            else -> "غير معروف"
        }
    }
    
    /**
     * الحصول على معدل الإطارات مع النص
     */
    fun getFpsString(): String {
        return fps?.let { "${it}fps" } ?: ""
    }
    
    /**
     * تحديد جودة الفيديو كرقم للمقارنة
     */
    fun getQualityScore(): Int {
        return when {
            height != null -> height
            quality.contains("2160", ignoreCase = true) -> 2160
            quality.contains("1440", ignoreCase = true) -> 1440
            quality.contains("1080", ignoreCase = true) -> 1080
            quality.contains("720", ignoreCase = true) -> 720
            quality.contains("480", ignoreCase = true) -> 480
            quality.contains("360", ignoreCase = true) -> 360
            quality.contains("240", ignoreCase = true) -> 240
            quality.contains("144", ignoreCase = true) -> 144
            else -> 0
        }
    }
    
    /**
     * تحديد جودة الصوت كرقم للمقارنة
     */
    fun getAudioQualityScore(): Int {
        return audioBitrate ?: 0
    }
    
    /**
     * تنسيق حجم الملف
     */
    fun getFormattedFileSize(): String {
        if (fileSize == null || fileSize <= 0) return "غير معروف"
        
        return when {
            fileSize >= 1_073_741_824 -> "%.1f GB".format(fileSize / 1_073_741_824.0)
            fileSize >= 1_048_576 -> "%.1f MB".format(fileSize / 1_048_576.0)
            fileSize >= 1_024 -> "%.1f KB".format(fileSize / 1_024.0)
            else -> "$fileSize B"
        }
    }
    
    /**
     * فحص ما إذا كان التنسيق عالي الجودة
     */
    fun isHighQuality(): Boolean {
        return getQualityScore() >= 1080 || getAudioQualityScore() >= 320
    }
    
    /**
     * فحص ما إذا كان التنسيق منخفض الجودة
     */
    fun isLowQuality(): Boolean {
        return getQualityScore() <= 360 && getAudioQualityScore() <= 128
    }
    
    /**
     * فحص ما إذا كان التنسيق مدعوماً على الهاتف
     */
    fun isMobileSupported(): Boolean {
        val supportedVideoCodecs = listOf("h264", "h265", "vp9")
        val supportedAudioCodecs = listOf("aac", "mp3", "opus")
        
        val videoSupported = vcodec?.lowercase() in supportedVideoCodecs || !hasVideo
        val audioSupported = acodec?.lowercase() in supportedAudioCodecs || !hasAudio
        
        return videoSupported && audioSupported
    }
    
    /**
     * تحديد أولوية التحميل (أعلى = أفضل)
     */
    fun getDownloadPriority(): Int {
        var priority = 0
        
        // أولوية حسب التوافق
        if (isMobileSupported()) priority += 1000
        
        // أولوية حسب الدمج
        if (isCombined()) priority += 500
        
        // أولوية حسب الجودة
        priority += getQualityScore()
        
        // أولوية حسب جودة الصوت
        priority += getAudioQualityScore() / 10
        
        return priority
    }
    
    /**
     * الحصول على اسم ملف مقترح
     */
    fun getSuggestedFileName(title: String): String {
        val cleanTitle = title
            .replace(Regex("[^a-zA-Z0-9\\s\\-_\\u0600-\\u06FF]"), "")
            .replace(Regex("\\s+"), "_")
            .take(50)
        
        val qualityInfo = when {
            isAudioOnly() -> "audio_${audioBitrate ?: "unknown"}kbps"
            isVideoOnly() -> "video_${getResolutionString()}"
            else -> getResolutionString()
        }
        
        return "${cleanTitle}_${qualityInfo}.${extension}"
    }
    
    /**
     * تحديد نوع المحتوى
     */
    fun getContentType(): String {
        return when (extension.lowercase()) {
            "mp4" -> "video/mp4"
            "webm" -> "video/webm"
            "mkv" -> "video/x-matroska"
            "mp3" -> "audio/mpeg"
            "m4a" -> "audio/mp4"
            "wav" -> "audio/wav"
            "ogg" -> "audio/ogg"
            else -> "application/octet-stream"
        }
    }
    
    /**
     * فحص ما إذا كان التنسيق يحتاج لمعالجة إضافية
     */
    fun requiresPostProcessing(): Boolean {
        // التنسيقات المنفصلة تحتاج دمج
        return (hasVideo && !hasAudio) || (!hasVideo && hasAudio && extension != "mp3")
    }
    
    companion object {
        /**
         * إنشاء تنسيق افتراضي للفيديو
         */
        fun createDefaultVideo(): DownloadFormat {
            return DownloadFormat(
                formatId = "default_video",
                extension = "mp4",
                quality = "720p",
                height = 720,
                width = 1280,
                vcodec = "h264",
                acodec = "aac",
                hasVideo = true,
                hasAudio = true,
                isDefault = true
            )
        }
        
        /**
         * إنشاء تنسيق افتراضي للصوت
         */
        fun createDefaultAudio(): DownloadFormat {
            return DownloadFormat(
                formatId = "default_audio",
                extension = "mp3",
                quality = "audio",
                acodec = "mp3",
                audioBitrate = 192,
                hasVideo = false,
                hasAudio = true,
                isDefault = true
            )
        }
        
        /**
         * مقارنة التنسيقات حسب الجودة
         */
        fun compareByQuality(format1: DownloadFormat, format2: DownloadFormat): Int {
            return format1.getDownloadPriority().compareTo(format2.getDownloadPriority())
        }
        
        /**
         * تصفية التنسيقات حسب النوع
         */
        fun filterByType(formats: List<DownloadFormat>, videoOnly: Boolean = false, audioOnly: Boolean = false): List<DownloadFormat> {
            return formats.filter { format ->
                when {
                    videoOnly -> format.hasVideo
                    audioOnly -> format.hasAudio && !format.hasVideo
                    else -> true
                }
            }
        }
        
        /**
         * الحصول على أفضل تنسيق متاح
         */
        fun getBestFormat(formats: List<DownloadFormat>): DownloadFormat? {
            return formats.maxByOrNull { it.getDownloadPriority() }
        }
    }
}
