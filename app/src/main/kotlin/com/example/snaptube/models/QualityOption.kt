package com.example.snaptube.models

/**
 * نموذج خيارات الجودة للتحميل
 * يمثل خيار جودة واحد مع المعلومات المرتبطة به
 */
data class QualityOption(
    val id: String,
    val name: String,
    val quality: Int, // الرقم المرجعي للجودة (مثل 720 لـ 720p)
    val fileSize: Long = 0,
    val isAudioOnly: Boolean = false,
    val format: DownloadFormat,
    val estimatedDuration: Long = 0, // مدة التحميل المتوقعة بالثواني
    val isRecommended: Boolean = false,
    val compatibilityScore: Int = 0, // نقاط التوافق (0-100)
    val description: String = "",
    val bandwidth: Long = 0 // عرض النطاق المطلوب
) {
    
    /**
     * تنسيق اسم الجودة للعرض
     */
    fun getDisplayName(): String {
        return when {
            isAudioOnly -> "صوت - $name"
            quality > 0 -> "${quality}p - $name"
            else -> name
        }
    }
    
    /**
     * تنسيق حجم الملف للعرض
     */
    fun getFormattedFileSize(): String {
        return format.getFormattedFileSize()
    }
    
    /**
     * تحديد لون الجودة حسب النوع
     */
    fun getQualityColor(): String {
        return when {
            isAudioOnly -> "#FF9800" // برتقالي للصوت
            quality >= 1080 -> "#4CAF50" // أخضر للجودة العالية
            quality >= 720 -> "#2196F3" // أزرق للجودة المتوسطة
            quality >= 480 -> "#FF9800" // برتقالي للجودة المنخفضة
            else -> "#757575" // رمادي للجودة المنخفضة جداً
        }
    }
    
    /**
     * تحديد أيقونة الجودة
     */
    fun getQualityIcon(): String {
        return when {
            isAudioOnly -> "🎵"
            quality >= 2160 -> "🎬" // 4K
            quality >= 1080 -> "📱" // HD
            quality >= 720 -> "📺" // HD Ready
            else -> "📱" // SD
        }
    }
    
    /**
     * تحديد درجة الجودة كنص
     */
    fun getQualityGrade(): String {
        return when {
            isAudioOnly -> "صوت"
            quality >= 2160 -> "4K UHD"
            quality >= 1440 -> "2K QHD"
            quality >= 1080 -> "Full HD"
            quality >= 720 -> "HD"
            quality >= 480 -> "SD"
            else -> "منخفضة"
        }
    }
    
    /**
     * تقدير استهلاك البيانات بالميجابايت
     */
    fun getEstimatedDataUsage(): String {
        val sizeInMB = fileSize / (1024 * 1024)
        return when {
            sizeInMB >= 1000 -> "%.1f GB".format(sizeInMB / 1024.0)
            sizeInMB > 0 -> "$sizeInMB MB"
            else -> "غير معروف"
        }
    }
    
    /**
     * تقدير وقت التحميل بالدقائق (بناءً على سرعة متوسطة)
     */
    fun getEstimatedDownloadTime(speedKbps: Long = 1000): String {
        if (fileSize <= 0) return "غير معروف"
        
        val timeSeconds = (fileSize * 8) / (speedKbps * 1024) // تحويل إلى ثوان
        
        return when {
            timeSeconds >= 3600 -> "${timeSeconds / 3600}س ${(timeSeconds % 3600) / 60}د"
            timeSeconds >= 60 -> "${timeSeconds / 60}د ${timeSeconds % 60}ث"
            else -> "${timeSeconds}ث"
        }
    }
    
    /**
     * فحص ما إذا كانت الجودة مناسبة للهاتف المحمول
     */
    fun isMobileFriendly(): Boolean {
        return when {
            isAudioOnly -> true
            quality in 360..1080 -> true
            fileSize in 1..200_000_000 -> true // أقل من 200 ميجا
            else -> false
        }
    }
    
    /**
     * فحص ما إذا كانت الجودة مناسبة للشبكة البطيئة
     */
    fun isSlowNetworkFriendly(): Boolean {
        return when {
            isAudioOnly -> true
            quality <= 480 -> true
            fileSize <= 50_000_000 -> true // أقل من 50 ميجا
            else -> false
        }
    }
    
    /**
     * تحديد مستوى التوصية
     */
    fun getRecommendationLevel(): RecommendationLevel {
        return when {
            isRecommended -> RecommendationLevel.HIGHLY_RECOMMENDED
            isMobileFriendly() && compatibilityScore >= 80 -> RecommendationLevel.RECOMMENDED
            compatibilityScore >= 60 -> RecommendationLevel.ACCEPTABLE
            else -> RecommendationLevel.NOT_RECOMMENDED
        }
    }
    
    /**
     * الحصول على نص التوصية
     */
    fun getRecommendationText(): String {
        return when (getRecommendationLevel()) {
            RecommendationLevel.HIGHLY_RECOMMENDED -> "موصى به بشدة"
            RecommendationLevel.RECOMMENDED -> "موصى به"
            RecommendationLevel.ACCEPTABLE -> "مقبول"
            RecommendationLevel.NOT_RECOMMENDED -> "غير موصى به"
        }
    }
    
    /**
     * مقارنة الخيارات حسب الأولوية
     */
    fun compareTo(other: QualityOption): Int {
        // أولوية التوصية
        val recommendationComparison = getRecommendationLevel().ordinal.compareTo(other.getRecommendationLevel().ordinal)
        if (recommendationComparison != 0) return -recommendationComparison
        
        // أولوية الجودة
        val qualityComparison = quality.compareTo(other.quality)
        if (qualityComparison != 0) return -qualityComparison
        
        // أولوية حجم الملف (الأصغر أفضل)
        return fileSize.compareTo(other.fileSize)
    }
    
    /**
     * تحويل إلى كائن مبسط للواجهة
     */
    fun toDisplayModel(): QualityDisplayModel {
        return QualityDisplayModel(
            id = id,
            name = getDisplayName(),
            quality = getQualityGrade(),
            fileSize = getFormattedFileSize(),
            icon = getQualityIcon(),
            color = getQualityColor(),
            recommendation = getRecommendationText(),
            isRecommended = isRecommended,
            description = description.ifEmpty { "${getQualityGrade()} - ${getFormattedFileSize()}" }
        )
    }
    
    companion object {
        /**
         * إنشاء خيار جودة من DownloadFormat
         */
        fun fromDownloadFormat(format: DownloadFormat, isRecommended: Boolean = false): QualityOption {
            val quality = format.getQualityScore()
            val isAudioOnly = format.isAudioOnly()
            
            val name = when {
                isAudioOnly -> "${format.audioBitrate ?: ""}kbps ${format.acodec?.uppercase() ?: ""}"
                else -> format.getQualityDescription()
            }
            
            return QualityOption(
                id = format.formatId,
                name = name.trim(),
                quality = if (isAudioOnly) format.getAudioQualityScore() else quality,
                fileSize = format.fileSize ?: 0,
                isAudioOnly = isAudioOnly,
                format = format,
                isRecommended = isRecommended,
                compatibilityScore = calculateCompatibilityScore(format),
                description = format.note
            )
        }
        
        /**
         * حساب نقاط التوافق للتنسيق
         */
        private fun calculateCompatibilityScore(format: DownloadFormat): Int {
            var score = 50 // نقطة أساسية
            
            // نقاط للتوافق مع الهاتف
            if (format.isMobileSupported()) score += 30
            
            // نقاط للتنسيقات المدمجة
            if (format.isCombined()) score += 20
            
            // نقاط للحجم المناسب
            val fileSizeMB = (format.fileSize ?: 0) / (1024 * 1024)
            when {
                fileSizeMB in 10..100 -> score += 10
                fileSizeMB in 100..500 -> score += 5
                fileSizeMB > 1000 -> score -= 10
            }
            
            // نقاط للجودة المتوسطة (مناسبة للهاتف)
            val quality = format.getQualityScore()
            when {
                quality in 720..1080 -> score += 10
                quality in 480..720 -> score += 5
                quality > 1080 -> score -= 5
            }
            
            return score.coerceIn(0, 100)
        }
        
        /**
         * ترتيب خيارات الجودة حسب الأولوية
         */
        fun sortByPriority(options: List<QualityOption>): List<QualityOption> {
            return options.sortedWith { a, b -> a.compareTo(b) }
        }
        
        /**
         * تصفية خيارات الجودة حسب نوع الشبكة
         */
        fun filterByNetworkType(options: List<QualityOption>, isSlowNetwork: Boolean): List<QualityOption> {
            return if (isSlowNetwork) {
                options.filter { it.isSlowNetworkFriendly() }
            } else {
                options
            }
        }
    }
}

/**
 * مستوى التوصية للجودة
 */
enum class RecommendationLevel {
    NOT_RECOMMENDED,
    ACCEPTABLE,
    RECOMMENDED,
    HIGHLY_RECOMMENDED
}

/**
 * نموذج مبسط لعرض الجودة في الواجهة
 */
data class QualityDisplayModel(
    val id: String,
    val name: String,
    val quality: String,
    val fileSize: String,
    val icon: String,
    val color: String,
    val recommendation: String,
    val isRecommended: Boolean,
    val description: String
)
