package com.example.snaptube.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.snaptube.database.converters.DateConverter
import com.example.snaptube.database.converters.DownloadStatusConverter
import com.example.snaptube.download.DownloadStatus
import java.util.Date

@Entity(tableName = "downloads")
@TypeConverters(DateConverter::class, DownloadStatusConverter::class)
data class DownloadEntity(
    @PrimaryKey
    val id: String,
    val url: String,
    val title: String,
    val description: String? = null,
    val thumbnail: String? = null,
    val duration: Long = 0L,
    val author: String? = null,
    val platform: String = "",
    val formatId: String = "",
    val qualityLabel: String = "",
    val audioCodec: String? = null,
    val videoCodec: String? = null,
    val audioOnly: Boolean = false,
    val videoOnly: Boolean = false,
    val fileExtension: String = "",
    val estimatedSize: Long = 0L,
    val outputPath: String = "",
    val filename: String = "",
    val status: DownloadStatus = DownloadStatus.PENDING,
    val progress: Float = 0f,
    val downloadedBytes: Long = 0L,
    val totalBytes: Long = 0L,
    val speed: Long = 0L,
    val eta: Long = 0L,
    val error: String? = null,
    val retryCount: Int = 0,
    val maxRetries: Int = 3,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val completedAt: Date? = null,
    val isVisible: Boolean = true,
    val priority: Int = 0,
    val workerId: String? = null,
    val metadata: String? = null,
    // إضافة fields من DownloadTask للتوافق
    val downloadPath: String = "",
    val filePath: String = "",
    val errorMessage: String = "",
    val quality: String = "",
    val extension: String = "",
    val totalSize: Long = 0L,
    val startedAt: Long = 0L
) {
    
    fun isCompleted(): Boolean = status == DownloadStatus.COMPLETED
    
    fun isFailed(): Boolean = status == DownloadStatus.FAILED
    
    fun isInProgress(): Boolean = status in listOf(
        DownloadStatus.PENDING,
        DownloadStatus.DOWNLOADING,
        DownloadStatus.PROCESSING,
        DownloadStatus.EXTRACTING
    )
    
    fun canRetry(): Boolean = isFailed() && retryCount < maxRetries
    
    fun getProgressPercentage(): Int = (progress * 100).toInt()
    
    fun getFormattedSpeed(): String {
        return when {
            speed > 1024 * 1024 -> "${speed / (1024 * 1024)} MB/s"
            speed > 1024 -> "${speed / 1024} KB/s"
            else -> "$speed B/s"
        }
    }
    
    fun getFormattedSize(): String {
        val bytes = if (totalBytes > 0) totalBytes else estimatedSize
        return when {
            bytes > 1024 * 1024 * 1024 -> "${"%.1f".format(bytes.toDouble() / (1024 * 1024 * 1024))} GB"
            bytes > 1024 * 1024 -> "${"%.1f".format(bytes.toDouble() / (1024 * 1024))} MB"
            bytes > 1024 -> "${"%.1f".format(bytes.toDouble() / 1024)} KB"
            else -> "$bytes B"
        }
    }
    
    fun getFormattedEta(): String {
        if (eta <= 0) return "Unknown"
        
        val hours = eta / 3600
        val minutes = (eta % 3600) / 60
        val seconds = eta % 60
        
        return when {
            hours > 0 -> "${hours}h ${minutes}m"
            minutes > 0 -> "${minutes}m ${seconds}s"
            else -> "${seconds}s"
        }
    }
    
    fun getStatusDisplayName(): String {
        return when (status) {
            DownloadStatus.PENDING -> "في الانتظار"
            DownloadStatus.DOWNLOADING -> "يتم التحميل"
            DownloadStatus.PROCESSING -> "معالجة"
            DownloadStatus.EXTRACTING -> "استخراج"
            DownloadStatus.COMPLETED -> "مكتمل"
            DownloadStatus.FAILED -> "فشل"
            DownloadStatus.CANCELLED -> "ملغي"
            DownloadStatus.PAUSED -> "متوقف مؤقتاً"
        }
    }
}