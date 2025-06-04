package com.example.snaptube.models

/**
 * نموذج معلومات قائمة التشغيل
 * يحتوي على معلومات قائمة تشغيل من منصات مختلفة
 */
data class PlaylistInfo(
    val id: String,
    val title: String,
    val description: String = "",
    val uploaderName: String = "",
    val uploaderUrl: String = "",
    val videoCount: Int = 0,
    val videos: List<VideoInfo> = emptyList(),
    val thumbnail: String = "",
    val createdDate: String = "",
    val updatedDate: String = "",
    val platform: String = "unknown",
    val originalUrl: String = "",
    val isPublic: Boolean = true,
    val totalDuration: Long = 0, // إجمالي المدة بالثواني
    val viewCount: Long = 0,
    val likeCount: Long = 0,
    val tags: List<String> = emptyList(),
    val language: String = "",
    val category: String = ""
) {
    
    /**
     * تنسيق عدد الفيديوهات
     */
    fun getFormattedVideoCount(): String {
        return when {
            videoCount >= 1000 -> "${videoCount / 1000}K فيديو"
            videoCount > 0 -> "$videoCount فيديو"
            else -> "فارغة"
        }
    }
    
    /**
     * تنسيق إجمالي مدة قائمة التشغيل
     */
    fun getFormattedTotalDuration(): String {
        if (totalDuration <= 0 && videos.isNotEmpty()) {
            val calculatedDuration = videos.sumOf { it.duration }
            return formatDuration(calculatedDuration)
        }
        return formatDuration(totalDuration)
    }
    
    /**
     * تنسيق المدة
     */
    private fun formatDuration(duration: Long): String {
        if (duration <= 0) return "غير معروف"
        
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        
        return when {
            hours > 24 -> "${hours / 24} يوم ${hours % 24} ساعة"
            hours > 0 -> "$hours ساعة $minutes دقيقة"
            minutes > 0 -> "$minutes دقيقة"
            else -> "أقل من دقيقة"
        }
    }
    
    /**
     * تنسيق عدد المشاهدات
     */
    fun getFormattedViewCount(): String {
        return when {
            viewCount >= 1_000_000_000 -> "${viewCount / 1_000_000_000}B مشاهدة"
            viewCount >= 1_000_000 -> "${viewCount / 1_000_000}M مشاهدة"
            viewCount >= 1_000 -> "${viewCount / 1_000}K مشاهدة"
            viewCount > 0 -> "$viewCount مشاهدة"
            else -> "لا توجد مشاهدات"
        }
    }
    
    /**
     * الحصول على اسم المنصة للعرض
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
     * حساب متوسط مدة الفيديوهات
     */
    fun getAverageVideoDuration(): Long {
        return if (videos.isNotEmpty()) {
            videos.sumOf { it.duration } / videos.size
        } else {
            0
        }
    }
    
    /**
     * تنسيق متوسط مدة الفيديوهات
     */
    fun getFormattedAverageVideoDuration(): String {
        val avgDuration = getAverageVideoDuration()
        val minutes = avgDuration / 60
        val seconds = avgDuration % 60
        
        return when {
            minutes > 0 -> "${minutes}:${String.format("%02d", seconds)}"
            seconds > 0 -> "0:${String.format("%02d", seconds)}"
            else -> "0:00"
        }
    }
    
    /**
     * الحصول على أحدث فيديو في القائمة
     */
    fun getLatestVideo(): VideoInfo? {
        return videos.maxByOrNull { 
            try {
                // تحويل uploadDate إلى timestamp للمقارنة
                parseUploadDate(it.uploadDate)
            } catch (e: Exception) {
                0L
            }
        }
    }
    
    /**
     * الحصول على أكثر فيديو مشاهدة
     */
    fun getMostViewedVideo(): VideoInfo? {
        return videos.maxByOrNull { it.viewCount }
    }
    
    /**
     * الحصول على أطول فيديو
     */
    fun getLongestVideo(): VideoInfo? {
        return videos.maxByOrNull { it.duration }
    }
    
    /**
     * فحص ما إذا كانت قائمة التشغيل فارغة
     */
    fun isEmpty(): Boolean {
        return videos.isEmpty() || videoCount == 0
    }
    
    /**
     * فحص ما إذا كانت قائمة التشغيل كبيرة
     */
    fun isLargePlaylist(): Boolean {
        return videoCount > 50 || videos.size > 50
    }
    
    /**
     * تقدير حجم التحميل الإجمالي
     */
    fun getEstimatedTotalSize(): Long {
        return videos.sumOf { it.fileSize }
    }
    
    /**
     * تنسيق حجم التحميل الإجمالي
     */
    fun getFormattedTotalSize(): String {
        val totalSize = getEstimatedTotalSize()
        
        return when {
            totalSize >= 1_073_741_824 -> "%.1f GB".format(totalSize / 1_073_741_824.0)
            totalSize >= 1_048_576 -> "%.1f MB".format(totalSize / 1_048_576.0)
            totalSize >= 1_024 -> "%.1f KB".format(totalSize / 1_024.0)
            totalSize > 0 -> "$totalSize B"
            else -> "غير معروف"
        }
    }
    
    /**
     * تصفية الفيديوهات حسب المدة
     */
    fun filterVideosByDuration(minDuration: Long = 0, maxDuration: Long = Long.MAX_VALUE): List<VideoInfo> {
        return videos.filter { it.duration in minDuration..maxDuration }
    }
    
    /**
     * تصفية الفيديوهات حسب الجودة
     */
    fun filterVideosByQuality(minQuality: Int = 0): List<VideoInfo> {
        return videos.filter { video ->
            video.getBestQuality()?.getQualityScore() ?: 0 >= minQuality
        }
    }
    
    /**
     * البحث في الفيديوهات
     */
    fun searchVideos(query: String): List<VideoInfo> {
        val lowerQuery = query.lowercase()
        return videos.filter { video ->
            video.title.lowercase().contains(lowerQuery) ||
            video.description.lowercase().contains(lowerQuery) ||
            video.uploader.lowercase().contains(lowerQuery)
        }
    }
    
    /**
     * تجميع الفيديوهات حسب القناة
     */
    fun groupVideosByUploader(): Map<String, List<VideoInfo>> {
        return videos.groupBy { it.uploader }
    }
    
    /**
     * تحويل إلى نموذج عرض مبسط
     */
    fun toDisplayModel(): PlaylistDisplayModel {
        return PlaylistDisplayModel(
            id = id,
            title = title,
            uploader = uploaderName,
            videoCount = getFormattedVideoCount(),
            totalDuration = getFormattedTotalDuration(),
            viewCount = getFormattedViewCount(),
            thumbnail = thumbnail,
            platform = getPlatformDisplayName(),
            description = if (description.length > 100) description.take(100) + "..." else description,
            isLarge = isLargePlaylist(),
            estimatedSize = getFormattedTotalSize()
        )
    }
    
    /**
     * تحليل تاريخ الرفع
     */
    private fun parseUploadDate(uploadDate: String): Long {
        // تحليل بسيط لتاريخ الرفع
        // يمكن تحسينه لاحقاً لدعم تنسيقات مختلفة
        return try {
            uploadDate.toLongOrNull() ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    
    companion object {
        /**
         * إنشاء قائمة تشغيل فارغة
         */
        fun empty(): PlaylistInfo {
            return PlaylistInfo(
                id = "",
                title = "",
                description = "",
                originalUrl = ""
            )
        }
        
        /**
         * إنشاء قائمة تشغيل من قائمة فيديوهات
         */
        fun fromVideos(
            id: String,
            title: String,
            videos: List<VideoInfo>,
            platform: String = "unknown"
        ): PlaylistInfo {
            return PlaylistInfo(
                id = id,
                title = title,
                videos = videos,
                videoCount = videos.size,
                platform = platform,
                totalDuration = videos.sumOf { it.duration },
                thumbnail = videos.firstOrNull()?.thumbnail ?: "",
                uploaderName = videos.firstOrNull()?.uploader ?: ""
            )
        }
        
        /**
         * دمج قوائم تشغيل متعددة
         */
        fun merge(playlists: List<PlaylistInfo>, newTitle: String): PlaylistInfo {
            val allVideos = playlists.flatMap { it.videos }
            val totalCount = playlists.sumOf { it.videoCount }
            
            return PlaylistInfo(
                id = "merged_${System.currentTimeMillis()}",
                title = newTitle,
                videos = allVideos,
                videoCount = totalCount,
                totalDuration = allVideos.sumOf { it.duration },
                thumbnail = allVideos.firstOrNull()?.thumbnail ?: "",
                platform = "merged"
            )
        }
    }
}

/**
 * نموذج مبسط لعرض قائمة التشغيل
 */
data class PlaylistDisplayModel(
    val id: String,
    val title: String,
    val uploader: String,
    val videoCount: String,
    val totalDuration: String,
    val viewCount: String,
    val thumbnail: String,
    val platform: String,
    val description: String,
    val isLarge: Boolean,
    val estimatedSize: String
)
