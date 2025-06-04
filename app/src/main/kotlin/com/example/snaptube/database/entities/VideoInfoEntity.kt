package com.example.snaptube.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.snaptube.database.converters.DateConverter
import com.example.snaptube.database.converters.StringListConverter
import java.util.Date

@Entity(tableName = "video_info_cache")
@TypeConverters(DateConverter::class, StringListConverter::class)
data class VideoInfoEntity(
    @PrimaryKey
    val url: String,
    val videoId: String,
    val title: String,
    val description: String? = null,
    val thumbnail: String? = null,
    val duration: Long = 0L,
    val viewCount: Long = 0L,
    val likeCount: Long = 0L,
    val author: String? = null,
    val authorUrl: String? = null,
    val authorThumbnail: String? = null,
    val platform: String,
    val uploadDate: String? = null,
    val isLive: Boolean = false,
    val ageLimit: Int = 0,
    val categories: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val language: String? = null,
    val subtitles: List<String> = emptyList(), // JSON list of subtitle info
    val formats: List<String> = emptyList(), // JSON list of format info
    val qualityOptions: List<String> = emptyList(), // JSON list of quality options
    val availableFormats: List<String> = emptyList(), // List of format IDs
    val hasAudio: Boolean = true,
    val hasVideo: Boolean = true,
    val maxQuality: String? = null,
    val minQuality: String? = null,
    val recommendedFormat: String? = null,
    val fileSize: Long = 0L,
    val bitrate: Int = 0,
    val fps: Int = 0,
    val resolution: String? = null,
    val aspectRatio: String? = null,
    val audioLanguage: String? = null,
    val videoLanguage: String? = null,
    val chapters: List<String> = emptyList(), // JSON list of chapter info
    val webpage: String? = null,
    val extractor: String? = null,
    val extractorKey: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val lastAccessed: Date = Date(),
    val accessCount: Int = 1,
    val cacheExpiryDate: Date? = null,
    val isValid: Boolean = true,
    val metadata: String? = null // Additional JSON metadata
) {
    
    fun isExpired(): Boolean {
        return cacheExpiryDate?.let { it.before(Date()) } ?: false
    }
    
    fun shouldRefresh(): Boolean {
        val oneDayAgo = Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
        return updatedAt.before(oneDayAgo) || isExpired()
    }
    
    fun incrementAccessCount(): VideoInfoEntity {
        return copy(
            accessCount = accessCount + 1,
            lastAccessed = Date()
        )
    }
    
    fun getFormattedDuration(): String {
        if (duration <= 0) return "Unknown"
        
        val hours = duration / 3600
        val minutes = (duration % 3600) / 60
        val seconds = duration % 60
        
        return when {
            hours > 0 -> "%d:%02d:%02d".format(hours, minutes, seconds)
            else -> "%d:%02d".format(minutes, seconds)
        }
    }
    
    fun getFormattedViewCount(): String {
        return when {
            viewCount > 1_000_000_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000_000_000)}B"
            viewCount > 1_000_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000_000)}M"
            viewCount > 1_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000)}K"
            else -> viewCount.toString()
        }
    }
    
    fun getFormattedFileSize(): String {
        return when {
            fileSize > 1024 * 1024 * 1024 -> "${"%.1f".format(fileSize.toDouble() / (1024 * 1024 * 1024))} GB"
            fileSize > 1024 * 1024 -> "${"%.1f".format(fileSize.toDouble() / (1024 * 1024))} MB"
            fileSize > 1024 -> "${"%.1f".format(fileSize.toDouble() / 1024)} KB"
            else -> "$fileSize B"
        }
    }
    
    fun getPlatformDisplayName(): String {
        return when (platform.lowercase()) {
            "youtube" -> "YouTube"
            "twitter" -> "Twitter/X"
            "instagram" -> "Instagram"
            "tiktok" -> "TikTok"
            "facebook" -> "Facebook"
            "vimeo" -> "Vimeo"
            "dailymotion" -> "Dailymotion"
            else -> platform.replaceFirstChar { it.uppercaseChar() }
        }
    }
    
    fun getQualityRange(): String {
        return when {
            maxQuality != null && minQuality != null -> "$minQuality - $maxQuality"
            maxQuality != null -> "Up to $maxQuality"
            minQuality != null -> "From $minQuality"
            else -> "Unknown"
        }
    }
    
    fun hasSubtitles(): Boolean = subtitles.isNotEmpty()
    
    fun hasMultipleFormats(): Boolean = availableFormats.size > 1
    
    fun isRecentlyAccessed(): Boolean {
        val oneHourAgo = Date(System.currentTimeMillis() - 60 * 60 * 1000)
        return lastAccessed.after(oneHourAgo)
    }
}
