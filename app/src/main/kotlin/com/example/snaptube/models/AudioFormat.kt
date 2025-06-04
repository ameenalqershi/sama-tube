package com.example.snaptube.models

/**
 * نموذج تنسيق صوتي لتحميل الصوت
 * يحتوي على معلومات خاصة بالتنسيق الصوتي
 *
 * @property formatId معرف التنسيق
 * @property extension امتداد الملف (mp3, m4a, إلخ)
 * @property bitrate معدل البت الصوتي (kbps)
 * @property fileSize حجم الملف بالبايت
 * @property audioCodec اسم كوديك الصوت (aac, mp3, إلخ)
 */
data class AudioFormat(
    val formatId: String,
    val extension: String,
    val bitrate: Int? = null,
    val fileSize: Long? = null,
    val audioCodec: String? = null
) {

    /**
     * تحديد ما إذا كان التنسيق للصوت فقط
     * في AudioFormat دائماً يُعتبر صوتي
     */
    fun isAudioOnly(): Boolean = true

    /**
     * الحصول على وصف جودة التنسيق الصوتي
     *
     * @return نص يدمج معدل البت والكوديك
     */
    fun getQualityDescription(): String {
        val bitrateText = bitrate?.let { "${it}kbps" } ?: ""
        val codecText = audioCodec?.uppercase() ?: ""
        return listOf(codecText, bitrateText)
            .filter { it.isNotEmpty() }
            .joinToString(" ")
    }

    /**
     * تنسيق حجم الملف للعرض
     *
     * @return حجم الملف كنص مقروء (KB, MB, GB)
     */
    fun getFormattedFileSize(): String {
        val size = fileSize ?: return "غير معروف"
        if (size <= 0) return "غير معروف"
        return when {
            size >= 1_073_741_824 -> "%.1f GB".format(size / 1_073_741_824.0)
            size >= 1_048_576 -> "%.1f MB".format(size / 1_048_576.0)
            size >= 1_024 -> "%.1f KB".format(size / 1_024.0)
            else -> "$size B"
        }
    }

    companion object {
        /**
         * إنشاء تنسيق صوت افتراضي (mp3)
         */
        fun createDefaultAudio(): AudioFormat {
            return AudioFormat(
                formatId = "default_audio",
                extension = "mp3",
                bitrate = 192,
                fileSize = null,
                audioCodec = "mp3"
            )
        }
    }
} 