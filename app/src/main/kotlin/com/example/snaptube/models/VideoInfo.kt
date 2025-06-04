package com.example.snaptube.models

import com.example.snaptube.database.entities.DownloadEntity

/**
 * نموذج معلومات الفيديو
 * يحتوي على جميع المعلومات الخاصة بالفيديو المستخرجة من المنصات المختلفة
 */
data class VideoInfo(
    val id: String,
    val title: String,
    val description: String = "",
    val duration: Long = 0, // بالثواني
    val uploadDate: String = "",
    val uploader: String = "",
    val uploaderUrl: String = "",
    val viewCount: Long = 0,
    val likeCount: Long = 0,
    val thumbnail: String = "",
    val webpage_url: String = "",
    val originalUrl: String = "",
    val platform: String = "unknown", // youtube, twitter, instagram, etc.
    val fileSize: Long = 0,
    val formatId: String = "",
    val ext: String = "mp4",
    val resolution: String = "",
    val fps: Int = 0,
    val vcodec: String = "",
    val acodec: String = "",
    val abr: Int = 0, // Audio bitrate
    val isLive: Boolean = false,
    val subtitles: List<SubtitleInfo> = emptyList(),
    val chapters: List<ChapterInfo> = emptyList(),
    val formats: List<DownloadFormat> = emptyList(),
    val tags: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val language: String = "",
    val ageLimit: Int = 0,
    val isPrivate: Boolean = false,
    val isFamilyFriendly: Boolean = true
) {
    
    /**
     * تنسيق مدة الفيديو كنص قابل للقراءة
     */
    fun getFormattedDuration(): String {
        if (duration <= 0) return "غير معروف"
        
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        val seconds = duration % 60
        
        return when {
            hours > 0 -> String.format("%d:%02d:%02d", hours, minutes, seconds)
            minutes > 0 -> String.format("%d:%02d", minutes, seconds)
            else -> String.format("0:%02d", seconds)
        }
    }
    
    /**
     * تنسيق عدد المشاهدات
     */
    fun getFormattedViewCount(): String {
        return when {
            viewCount >= 1_000_000_000 -> "${viewCount / 1_000_000_000}B"
            viewCount >= 1_000_000 -> "${viewCount / 1_000_000}M"
            viewCount >= 1_000 -> "${viewCount / 1_000}K"
            else -> viewCount.toString()
        }
    }
    
    /**
     * تنسيق حجم الملف
     */
    fun getFormattedFileSize(): String {
        if (fileSize <= 0) return "غير معروف"
        
        return when {
            fileSize >= 1_073_741_824 -> "%.1f GB".format(fileSize / 1_073_741_824.0)
            fileSize >= 1_048_576 -> "%.1f MB".format(fileSize / 1_048_576.0)
            fileSize >= 1_024 -> "%.1f KB".format(fileSize / 1_024.0)
            else -> "$fileSize B"
        }
    }
    
    /**
     * الحصول على أفضل جودة متاحة
     */
    fun getBestQuality(): DownloadFormat? {
        return formats
            .filter { it.hasVideo }
            .maxByOrNull { it.height ?: 0 }
    }
    
    /**
     * الحصول على أفضل جودة صوت متاحة
     */
    fun getBestAudioQuality(): DownloadFormat? {
        return formats
            .filter { it.hasAudio }
            .maxByOrNull { it.audioBitrate ?: 0 }
    }
    
    /**
     * فحص إمكانية التحميل
     */
    fun isDownloadable(): Boolean {
        return formats.isNotEmpty() && !isLive && !isPrivate
    }
    
    /**
     * الحصول على منصة الفيديو
     */
    fun getPlatformDisplayName(): String {
        return when (platform.lowercase()) {
            "youtube" -> "يوتيوب"
            "twitter", "x" -> "تويتر"
            "instagram" -> "إنستغرام"
            "tiktok" -> "تيك توك"
            "facebook" -> "فيسبوك"
            "vimeo" -> "فيميو"
            "dailymotion" -> "ديلي موشن"
            else -> platform.replaceFirstChar { it.uppercase() }
        }
    }
    
    /**
     * الحصول على رابط مصغر للفيديو
     */
    fun getShortUrl(): String {
        return when (platform.lowercase()) {
            "youtube" -> "https://youtu.be/$id"
            else -> webpage_url.ifEmpty { originalUrl }
        }
    }
    
    /**
     * فحص ما إذا كان الفيديو طويلاً (أكثر من ساعة)
     */
    fun isLongVideo(): Boolean {
        return duration > 3600
    }
    
    /**
     * فحص ما إذا كان الفيديو قصيراً (أقل من دقيقة)
     */
    fun isShortVideo(): Boolean {
        return duration > 0 && duration < 60
    }
    
    /**
     * الحصول على تقييم المحتوى
     */
    fun getContentRating(): String {
        return when {
            ageLimit >= 18 -> "للبالغين فقط"
            ageLimit >= 13 -> "PG-13"
            !isFamilyFriendly -> "قد يحتوي على محتوى غير مناسب"
            else -> "مناسب لجميع الأعمار"
        }
    }
    
    /**
     * البحث عن ترجمة بلغة محددة
     */
    fun getSubtitleByLanguage(language: String): SubtitleInfo? {
        return subtitles.find { it.language.equals(language, ignoreCase = true) }
    }
    
    /**
     * الحصول على الترجمة العربية إن وجدت
     */
    fun getArabicSubtitle(): SubtitleInfo? {
        return getSubtitleByLanguage("ar") ?: getSubtitleByLanguage("arabic")
    }
    
    /**
     * تحويل إلى نموذج مبسط للواجهة
     */
    fun toDisplayModel(): VideoDisplayInfo {
        return VideoDisplayInfo(
            id = id,
            title = title,
            uploader = uploader,
            duration = getFormattedDuration(),
            viewCount = getFormattedViewCount(),
            thumbnail = thumbnail,
            platform = getPlatformDisplayName(),
            url = getShortUrl(),
            isLive = isLive,
            quality = getBestQuality()?.quality ?: "غير معروف"
        )
    }
    
    companion object {
        /**
         * إنشاء VideoInfo فارغ للاختبار
         */
        fun empty(): VideoInfo {
            return VideoInfo(
                id = "",
                title = "",
                description = "",
                originalUrl = ""
            )
        }
        
        /**
         * إنشاء VideoInfo من entity قاعدة البيانات
         */
        fun fromDownloadEntity(entity: DownloadEntity): VideoInfo {
            return VideoInfo(
                id = entity.id,
                title = entity.title,
                originalUrl = entity.url,
                fileSize = entity.totalSize,
                formatId = entity.formatId,
                ext = entity.extension
            )
        }
    }
}

/**
 * نموذج مبسط لعرض معلومات الفيديو في الواجهة
 */
data class VideoDisplayInfo(
    val id: String,
    val title: String,
    val uploader: String,
    val duration: String,
    val viewCount: String,
    val thumbnail: String,
    val platform: String,
    val url: String,
    val isLive: Boolean,
    val quality: String
)

/**
 * معلومات الفصول (Chapters) في الفيديو
 */
data class ChapterInfo(
    val title: String,
    val startTime: Long, // بالثواني
    val endTime: Long, // بالثواني
    val thumbnail: String = ""
) {
    fun getFormattedStartTime(): String {
        val minutes = startTime / 60
        val seconds = startTime % 60
        return String.format("%d:%02d", minutes, seconds)
    }
    
    fun getDuration(): Long {
        return endTime - startTime
    }
    
    fun getFormattedDuration(): String {
        val duration = getDuration()
        val minutes = duration / 60
        val seconds = duration % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}
