package com.example.snaptube.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.snaptube.database.converters.DateConverter
import com.example.snaptube.database.converters.StringListConverter
import java.util.Date

@Entity(tableName = "playlists")
@TypeConverters(DateConverter::class, StringListConverter::class)
data class PlaylistEntity(
    @PrimaryKey
    val url: String,
    val playlistId: String,
    val title: String,
    val description: String? = null,
    val thumbnail: String? = null,
    val author: String? = null,
    val authorUrl: String? = null,
    val authorThumbnail: String? = null,
    val platform: String,
    val videoCount: Int = 0,
    val downloadedCount: Int = 0,
    val failedCount: Int = 0,
    val pendingCount: Int = 0,
    val totalDuration: Long = 0L,
    val totalSize: Long = 0L,
    val videoUrls: List<String> = emptyList(),
    val videoTitles: List<String> = emptyList(),
    val videoDurations: List<String> = emptyList(), // JSON list of durations
    val videoThumbnails: List<String> = emptyList(),
    val isPublic: Boolean = true,
    val isLive: Boolean = false,
    val uploadDate: String? = null,
    val lastUpdated: String? = null,
    val language: String? = null,
    val tags: List<String> = emptyList(),
    val categories: List<String> = emptyList(),
    val viewCount: Long = 0L,
    val subscriberCount: Long = 0L,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val lastAccessed: Date = Date(),
    val downloadStartedAt: Date? = null,
    val downloadCompletedAt: Date? = null,
    val accessCount: Int = 1,
    val downloadProgress: Float = 0f,
    val selectedQuality: String? = null,
    val selectedFormat: String? = null,
    val downloadPath: String? = null,
    val isBookmarked: Boolean = false,
    val priority: Int = 0,
    val downloadAllSelected: Boolean = false,
    val selectedVideoIndices: List<String> = emptyList(), // JSON list of selected indices
    val failedVideoUrls: List<String> = emptyList(),
    val metadata: String? = null // Additional JSON metadata
) {
    
    fun getCompletionPercentage(): Int {
        return if (videoCount > 0) {
            ((downloadedCount.toFloat() / videoCount) * 100).toInt()
        } else 0
    }
    
    fun getSuccessRate(): Float {
        return if (videoCount > 0) {
            downloadedCount.toFloat() / videoCount
        } else 0f
    }
    
    fun isCompleted(): Boolean = downloadedCount == videoCount && videoCount > 0
    
    fun hasFailures(): Boolean = failedCount > 0
    
    fun isInProgress(): Boolean = downloadedCount > 0 && downloadedCount < videoCount
    
    fun canRetryFailed(): Boolean = failedCount > 0 && failedVideoUrls.isNotEmpty()
    
    fun getRemainingCount(): Int = videoCount - downloadedCount - failedCount
    
    fun getFormattedTotalDuration(): String {
        if (totalDuration <= 0) return "Unknown"
        
        val hours = totalDuration / 3600
        val minutes = (totalDuration % 3600) / 60
        
        return when {
            hours > 24 -> "${hours / 24}d ${hours % 24}h"
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m"
            else -> "< 1m"
        }
    }
    
    fun getFormattedTotalSize(): String {
        return when {
            totalSize > 1024 * 1024 * 1024 -> "${"%.1f".format(totalSize.toDouble() / (1024 * 1024 * 1024))} GB"
            totalSize > 1024 * 1024 -> "${"%.1f".format(totalSize.toDouble() / (1024 * 1024))} MB"
            totalSize > 1024 -> "${"%.1f".format(totalSize.toDouble() / 1024)} KB"
            else -> "$totalSize B"
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
    
    fun getDownloadStatusSummary(): String {
        return when {
            isCompleted() -> "مكتمل ($downloadedCount/$videoCount)"
            isInProgress() -> "جاري التحميل ($downloadedCount/$videoCount)"
            hasFailures() && downloadedCount == 0 -> "فشل ($failedCount/$videoCount)"
            hasFailures() -> "جزئي ($downloadedCount/$videoCount، $failedCount فشل)"
            downloadedCount == 0 -> "لم يبدأ"
            else -> "غير معروف"
        }
    }
    
    fun getEstimatedTimeRemaining(): String {
        if (downloadProgress <= 0 || isCompleted()) return "Unknown"
        
        val remainingVideos = getRemainingCount()
        if (remainingVideos <= 0) return "Finishing..."
        
        // Rough estimation based on average video duration and current progress
        val avgDurationPerVideo = if (videoCount > 0) totalDuration / videoCount else 180L // 3 minutes default
        val estimatedSeconds = (remainingVideos * avgDurationPerVideo * 0.1).toLong() // Assume 0.1x speed
        
        val hours = estimatedSeconds / 3600
        val minutes = (estimatedSeconds % 3600) / 60
        
        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m"
            else -> "< 1m"
        }
    }
    
    fun incrementAccessCount(): PlaylistEntity {
        return copy(
            accessCount = accessCount + 1,
            lastAccessed = Date()
        )
    }
    
    fun updateDownloadStats(downloaded: Int, failed: Int, pending: Int): PlaylistEntity {
        return copy(
            downloadedCount = downloaded,
            failedCount = failed,
            pendingCount = pending,
            downloadProgress = if (videoCount > 0) downloaded.toFloat() / videoCount else 0f,
            updatedAt = Date()
        )
    }
}
