package com.example.snaptube.utils

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class VideoUtils(private val context: Context) {
    
    companion object {
        // Video quality tiers
        val QUALITY_TIERS = mapOf(
            "144p" to QualityTier.VERY_LOW,
            "240p" to QualityTier.LOW,
            "360p" to QualityTier.MEDIUM,
            "480p" to QualityTier.GOOD,
            "720p" to QualityTier.HIGH,
            "1080p" to QualityTier.VERY_HIGH,
            "1440p" to QualityTier.ULTRA,
            "2160p" to QualityTier.ULTRA,
            "4320p" to QualityTier.ULTRA
        )
        
        // Video formats priority (higher number = better)
        val FORMAT_PRIORITY = mapOf(
            "mp4" to 10,
            "mkv" to 8,
            "webm" to 6,
            "avi" to 4,
            "mov" to 3,
            "wmv" to 2,
            "flv" to 1,
            "3gp" to 0
        )
        
        // Audio formats priority
        val AUDIO_FORMAT_PRIORITY = mapOf(
            "m4a" to 10,
            "aac" to 9,
            "mp3" to 8,
            "opus" to 7,
            "ogg" to 6,
            "flac" to 5,
            "wav" to 4,
            "wma" to 2
        )
        
        // Bitrate ranges for quality assessment
        const val BITRATE_VERY_LOW = 500_000L // 500 kbps
        const val BITRATE_LOW = 1_000_000L // 1 Mbps
        const val BITRATE_MEDIUM = 2_500_000L // 2.5 Mbps
        const val BITRATE_HIGH = 5_000_000L // 5 Mbps
        const val BITRATE_VERY_HIGH = 10_000_000L // 10 Mbps
        
        /**
         * الحصول على وصف الدقة بناءً على العرض والارتفاع
         */
        @Suppress("UNUSED_PARAMETER")
        fun getVideoQualityFromResolution(width: Int, height: Int): String {
            return when {
                height >= 2160 -> "4K"
                height >= 1440 -> "1440p"
                height >= 1080 -> "1080p"
                height >= 720 -> "720p"
                height >= 480 -> "480p"
                height >= 360 -> "360p"
                height >= 240 -> "240p"
                else -> "Unknown"
            }
        }
        
        /**
         * تنسيق مدة التشغيل إلى نص (HH:MM:SS أو MM:SS)
         */
        fun formatDuration(seconds: Long): String {
            if (seconds <= 0) return "0:00"
            val hours = seconds / 3600
            val minutes = (seconds % 3600) / 60
            val secs = seconds % 60
            return if (hours > 0) {
                "%d:%02d:%02d".format(hours, minutes, secs)
            } else {
                "%d:%02d".format(minutes, secs)
            }
        }
        
        /**
         * تنسيق معدل البت إلى نص (kbps, Mbps)
         */
        fun formatBitrate(bitrate: Long): String {
            return when {
                bitrate >= 1_000_000 -> String.format("%.1f Mbps", bitrate / 1_000_000.0)
                bitrate >= 1_000 -> String.format("%.0f kbps", bitrate / 1_000.0)
                else -> "$bitrate bps"
            }
        }
    }
    
    enum class QualityTier {
        VERY_LOW, LOW, MEDIUM, GOOD, HIGH, VERY_HIGH, ULTRA
    }
    
    // Quality and format analysis
    fun parseQuality(qualityString: String): QualityInfo {
        val quality = qualityString.lowercase()
        
        return when {
            quality.contains("144") || quality.contains("240") -> QualityInfo(144, "144p", QualityTier.VERY_LOW)
            quality.contains("360") -> QualityInfo(360, "360p", QualityTier.MEDIUM)
            quality.contains("480") -> QualityInfo(480, "480p", QualityTier.GOOD)
            quality.contains("720") -> QualityInfo(720, "720p", QualityTier.HIGH)
            quality.contains("1080") -> QualityInfo(1080, "1080p", QualityTier.VERY_HIGH)
            quality.contains("1440") || quality.contains("2k") -> QualityInfo(1440, "1440p", QualityTier.ULTRA)
            quality.contains("2160") || quality.contains("4k") -> QualityInfo(2160, "2160p", QualityTier.ULTRA)
            quality.contains("4320") || quality.contains("8k") -> QualityInfo(4320, "4320p", QualityTier.ULTRA)
            else -> {
                // Try to extract number
                val numberRegex = Regex("(\\d+)")
                val match = numberRegex.find(quality)
                val height = match?.value?.toIntOrNull() ?: 480
                QualityInfo(height, "${height}p", getQualityTierForHeight(height))
            }
        }
    }
    
    private fun getQualityTierForHeight(height: Int): QualityTier {
        return when {
            height <= 240 -> QualityTier.VERY_LOW
            height <= 360 -> QualityTier.LOW
            height <= 480 -> QualityTier.MEDIUM
            height <= 720 -> QualityTier.HIGH
            height <= 1080 -> QualityTier.VERY_HIGH
            else -> QualityTier.ULTRA
        }
    }
    
    fun getQualityScore(quality: String, fileSize: Long, duration: Long): Int {
        val qualityInfo = parseQuality(quality)
        val bitrateScore = getBitrateScore(fileSize, duration)
        val tierScore = qualityInfo.tier.ordinal * 10
        
        return tierScore + bitrateScore
    }
    
    private fun getBitrateScore(fileSize: Long, duration: Long): Int {
        if (duration <= 0) return 5 // Default score
        
        val bitrate = (fileSize * 8) / duration // bits per second
        
        return when {
            bitrate >= BITRATE_VERY_HIGH -> 9
            bitrate >= BITRATE_HIGH -> 7
            bitrate >= BITRATE_MEDIUM -> 5
            bitrate >= BITRATE_LOW -> 3
            bitrate >= BITRATE_VERY_LOW -> 2
            else -> 1
        }
    }
    
    fun getFormatScore(format: String): Int {
        return FORMAT_PRIORITY[format.lowercase()] ?: 0
    }
    
    fun getAudioFormatScore(format: String): Int {
        return AUDIO_FORMAT_PRIORITY[format.lowercase()] ?: 0
    }
    
    // Format recommendations
    fun recommendBestFormat(
        formats: List<FormatInfo>,
        preferredQuality: String? = null,
        maxFileSize: Long? = null,
        audioOnly: Boolean = false
    ): FormatInfo? {
        var candidates = formats.filter { format ->
            if (audioOnly) format.audioOnly else format.hasVideo
        }
        
        // Filter by file size if specified
        maxFileSize?.let { maxSize ->
            candidates = candidates.filter { it.fileSize <= maxSize }
        }
        
        if (candidates.isEmpty()) return null
        
        // If preferred quality is specified, try to find exact match
        preferredQuality?.let { preferred ->
            val exactMatch = candidates.find { it.quality.equals(preferred, ignoreCase = true) }
            if (exactMatch != null) return exactMatch
        }
        
        // Score and sort formats
        return candidates.maxByOrNull { format ->
            val qualityScore = getQualityScore(format.quality, format.fileSize, format.duration)
            val formatScore = if (audioOnly) {
                getAudioFormatScore(format.audioCodec ?: "")
            } else {
                getFormatScore(format.container)
            }
            
            qualityScore * 10 + formatScore
        }
    }
    
    fun recommendQualityForDevice(): String {
        // This could be enhanced with actual device capabilities detection
        val screenHeight = context.resources.displayMetrics.heightPixels
        
        return when {
            screenHeight >= 2160 -> "1080p"  // 4K screens
            screenHeight >= 1440 -> "720p"  // QHD screens
            screenHeight >= 1080 -> "480p"  // FHD screens
            else -> "360p" // Lower resolution screens
        }
    }
    
    fun recommendQualityForNetwork(networkSpeed: NetworkUtils.NetworkSpeed, isMetered: Boolean): String {
        return when {
            isMetered -> when (networkSpeed) {
                NetworkUtils.NetworkSpeed.HIGH -> "480p"
                NetworkUtils.NetworkSpeed.MEDIUM -> "360p"
                else -> "240p"
            }
            else -> when (networkSpeed) {
                NetworkUtils.NetworkSpeed.HIGH -> "1080p"
                NetworkUtils.NetworkSpeed.MEDIUM -> "720p"
                NetworkUtils.NetworkSpeed.LOW -> "480p"
                else -> "360p"
            }
        }
    }
    
    // Duration and time formatting
    fun parseDuration(durationString: String): Long {
        return try {
            val parts = durationString.split(":")
            when (parts.size) {
                3 -> {
                    val hours = parts[0].toLong()
                    val minutes = parts[1].toLong()
                    val seconds = parts[2].toLong()
                    hours * 3600 + minutes * 60 + seconds
                }
                2 -> {
                    val minutes = parts[0].toLong()
                    val seconds = parts[1].toLong()
                    minutes * 60 + seconds
                }
                1 -> parts[0].toLong()
                else -> 0L
            }
        } catch (e: Exception) {
            Timber.w(e, "Failed to parse duration: $durationString")
            0L
        }
    }
    
    // File size calculations and estimations
    fun estimateFileSize(bitrate: Long, duration: Long): Long {
        return (bitrate * duration) / 8 // Convert bits to bytes
    }
    
    fun estimateDownloadTime(fileSize: Long, speedBytesPerSecond: Long): Long {
        return if (speedBytesPerSecond > 0) fileSize / speedBytesPerSecond else 0L
    }
    
    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        
        val size = bytes / 1024.0.pow(digitGroups.toDouble())
        return "${DecimalFormat("#.#").format(size)} ${units[digitGroups]}"
    }
    
    // Video metadata extraction
    fun extractVideoMetadata(filePath: String): VideoMetadata? {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)
            
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull() ?: 0L
            val width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull() ?: 0
            val height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull() ?: 0
            val bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toLongOrNull() ?: 0L
            val fps = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE)?.toFloatOrNull() ?: 0f
            val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
            val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
            
            retriever.release()
            
            VideoMetadata(
                duration = duration / 1000, // Convert to seconds
                width = width,
                height = height,
                bitrate = bitrate,
                fps = fps,
                title = title,
                artist = artist,
                album = album
            )
        } catch (e: Exception) {
            Timber.e(e, "Failed to extract video metadata from: $filePath")
            null
        }
    }
    
    // Thumbnail generation
    fun generateThumbnail(videoPath: String, timeUs: Long = 0L): Bitmap? {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(videoPath)
            
            val bitmap = if (timeUs > 0) {
                retriever.getFrameAtTime(timeUs, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
            } else {
                retriever.frameAtTime
            }
            
            retriever.release()
            bitmap
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate thumbnail for: $videoPath")
            null
        }
    }
    
    fun saveThumbnail(bitmap: Bitmap, outputFile: File): Boolean {
        return try {
            FileOutputStream(outputFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
            }
            true
        } catch (e: Exception) {
            Timber.e(e, "Failed to save thumbnail: ${outputFile.path}")
            false
        }
    }
    
    /**
     * Generate thumbnail and save to file
     */
    fun generateThumbnailFile(videoPath: String, outputPath: String, timeUs: Long = 0L): Boolean {
        return try {
            val bitmap = generateThumbnail(videoPath, timeUs) ?: return false
            val outputFile = File(outputPath)
            saveThumbnail(bitmap, outputFile)
        } catch (e: Exception) {
            Timber.e(e, "Failed to generate thumbnail file for: $videoPath")
            false
        }
    }
    
    // Quality validation and conversion
    fun isValidVideoFile(filePath: String): Boolean {
        return try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(filePath)
            
            val duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull()
            val hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO) == "yes"
            
            retriever.release()
            
            duration != null && duration > 0 && hasVideo
        } catch (e: Exception) {
            Timber.e(e, "Video file validation failed: $filePath")
            false
        }
    }
    
    /**
     * Check if file is a video file based on extension
     */
    fun isVideoFile(file: File): Boolean {
        val extension = file.extension.lowercase()
        return extension in listOf("mp4", "mkv", "webm", "avi", "mov", "wmv", "flv", "3gp", "m4v")
    }
    
    /**
     * Check if file is an audio file based on extension
     */
    fun isAudioFile(file: File): Boolean {
        val extension = file.extension.lowercase()
        return extension in listOf("mp3", "m4a", "aac", "opus", "ogg", "flac", "wav", "wma")
    }
    
    /**
     * Get all files in directory recursively
     */
    fun getAllFiles(directory: File): List<File> {
        val files = mutableListOf<File>()
        if (directory.exists() && directory.isDirectory) {
            directory.listFiles()?.forEach { file ->
                if (file.isDirectory) {
                    files.addAll(getAllFiles(file))
                } else {
                    files.add(file)
                }
            }
        }
        return files
    }
    
    // Data classes
    data class QualityInfo(
        val height: Int,
        val label: String,
        val tier: QualityTier
    )
    
    data class FormatInfo(
        val formatId: String,
        val container: String,
        val quality: String,
        val fileSize: Long,
        val duration: Long,
        val bitrate: Long = 0L,
        val fps: Float = 0f,
        val hasVideo: Boolean = true,
        val hasAudio: Boolean = true,
        val audioOnly: Boolean = false,
        val videoOnly: Boolean = false,
        val videoCodec: String? = null,
        val audioCodec: String? = null
    )
    
    data class VideoMetadata(
        val duration: Long = 0L, // in seconds
        val width: Int = 0,
        val height: Int = 0,
        val bitrate: Long = 0L,
        val fps: Float = 0f,
        val title: String? = null,
        val artist: String? = null,
        val album: String? = null
    ) {
        fun getQuality(): String = VideoUtils.getVideoQualityFromResolution(width, height)
        fun getAspectRatio(): Float = if (height > 0) width.toFloat() / height else 0f
        fun getFormattedDuration(): String = VideoUtils.formatDuration(duration)
        fun getFormattedBitrate(): String = VideoUtils.formatBitrate(bitrate)
    }
    
    // Utility functions
    fun compareQualities(quality1: String, quality2: String): Int {
        val info1 = parseQuality(quality1)
        val info2 = parseQuality(quality2)
        
        return info1.height.compareTo(info2.height)
    }
    
    fun isHighQuality(quality: String): Boolean {
        val info = parseQuality(quality)
        return info.tier >= QualityTier.HIGH
    }
    
    fun shouldWarnAboutFileSize(fileSize: Long, isMetered: Boolean): Boolean {
        val sizeMB = fileSize / (1024 * 1024)
        return when {
            isMetered && sizeMB > 100 -> true // 100MB on metered
            !isMetered && sizeMB > 500 -> true // 500MB on unmetered
            else -> false
        }
    }
    
    fun getQualityRecommendation(
        availableQualities: List<String>,
        networkSpeed: NetworkUtils.NetworkSpeed,
        isMetered: Boolean,
        deviceRecommendation: String
    ): String {
        val networkRecommendation = recommendQualityForNetwork(networkSpeed, isMetered)
        
        // Find the best quality that doesn't exceed recommendations
        val deviceInfo = parseQuality(deviceRecommendation)
        val networkInfo = parseQuality(networkRecommendation)
        
        val maxHeight = minOf(deviceInfo.height, networkInfo.height)
        
        return availableQualities
            .map { parseQuality(it) }
            .filter { it.height <= maxHeight }
            .maxByOrNull { it.height }
            ?.label ?: availableQualities.firstOrNull() ?: "480p"
    }
}
