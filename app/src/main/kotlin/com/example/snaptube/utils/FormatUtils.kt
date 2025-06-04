package com.example.snaptube.utils

import android.content.Context
import android.text.format.DateUtils
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ln
import kotlin.math.pow

object FormatUtils {
    
    /**
     * Format file size in human readable format
     */
    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (ln(bytes.toDouble()) / ln(1024.0)).toInt()
        
        return String.format(
            "%.1f %s",
            bytes / 1024.0.pow(digitGroups.toDouble()),
            units[digitGroups]
        )
    }

    /**
     * Format file size from double (approximate size)
     */
    fun formatFileSize(bytes: Double): String {
        return formatFileSize(bytes.toLong())
    }

    /**
     * Format duration in human readable format (HH:MM:SS or MM:SS)
     */
    fun formatDuration(seconds: Int): String {
        if (seconds <= 0) return "00:00"
        
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }

    /**
     * Format timestamp to relative time (e.g., "2 minutes ago")
     */
    fun formatTimeAgo(timestamp: Long): String {
        return DateUtils.getRelativeTimeSpanString(
            timestamp,
            System.currentTimeMillis(),
            DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }

    /**
     * Format timestamp to absolute time
     */
    fun formatAbsoluteTime(timestamp: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    /**
     * Get file extension from filename
     */
    fun getFileExtension(filename: String): String {
        return File(filename).extension.lowercase()
    }

    /**
     * Check if file is a video file
     */
    fun isVideoFile(filename: String): Boolean {
        val videoExtensions = setOf("mp4", "mkv", "avi", "mov", "wmv", "flv", "webm", "m4v")
        return videoExtensions.contains(getFileExtension(filename))
    }

    /**
     * Check if file is an audio file
     */
    fun isAudioFile(filename: String): Boolean {
        val audioExtensions = setOf("mp3", "m4a", "aac", "flac", "wav", "ogg", "opus")
        return audioExtensions.contains(getFileExtension(filename))
    }

    /**
     * Get available storage space in bytes
     */
    fun getAvailableSpace(path: String): Long {
        return try {
            File(path).usableSpace
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Check if there's enough space for download
     */
    fun hasEnoughSpace(path: String, requiredBytes: Long): Boolean {
        val availableSpace = getAvailableSpace(path)
        // Add 100MB buffer
        return availableSpace > (requiredBytes + 100 * 1024 * 1024)
    }

    /**
     * Create directory if it doesn't exist
     */
    fun ensureDirectoryExists(path: String): Boolean {
        return try {
            val dir = File(path)
            if (!dir.exists()) {
                dir.mkdirs()
            } else {
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get safe filename by removing invalid characters
     */
    fun getSafeFilename(filename: String): String {
        return filename
            .replace(Regex("[\\\\/:*?\"<>|]"), "_")
            .replace(Regex("\\s+"), " ")
            .trim()
            .take(255) // Limit filename length
    }

    /**
     * Generate unique filename if file already exists
     */
    fun getUniqueFilename(directory: String, filename: String): String {
        val file = File(directory, filename)
        if (!file.exists()) return filename

        val nameWithoutExt = file.nameWithoutExtension
        val extension = file.extension
        var counter = 1

        while (true) {
            val newFilename = if (extension.isNotEmpty()) {
                "${nameWithoutExt} ($counter).$extension"
            } else {
                "$nameWithoutExt ($counter)"
            }
            
            if (!File(directory, newFilename).exists()) {
                return newFilename
            }
            counter++
        }
    }

    /**
     * Delete file safely
     */
    fun deleteFile(filePath: String): Boolean {
        return try {
            File(filePath).delete()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Check if file exists and is readable
     */
    fun isFileAccessible(filePath: String): Boolean {
        return try {
            val file = File(filePath)
            file.exists() && file.canRead()
        } catch (e: Exception) {
            false
        }
    }
}

object UrlUtils {
    
    /**
     * Extract domain from URL
     */
    fun extractDomain(url: String): String {
        return try {
            val cleanUrl = url.removePrefix("http://").removePrefix("https://")
            val domain = cleanUrl.substringBefore("/")
            domain.substringAfter("www.")
        } catch (e: Exception) {
            "Unknown"
        }
    }

    /**
     * Check if URL is valid
     */
    fun isValidUrl(url: String): Boolean {
        return try {
            android.util.Patterns.WEB_URL.matcher(url).matches()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Normalize URL
     */
    fun normalizeUrl(url: String): String {
        return url.trim().let { trimmed ->
            if (!trimmed.startsWith("http://") && !trimmed.startsWith("https://")) {
                "https://$trimmed"
            } else {
                trimmed
            }
        }
    }
}

object TextUtils {
    
    /**
     * Truncate text with ellipsis
     */
    fun truncate(text: String, maxLength: Int): String {
        return if (text.length <= maxLength) {
            text
        } else {
            text.take(maxLength - 3) + "..."
        }
    }

    /**
     * Capitalize first letter of each word
     */
    fun capitalizeWords(text: String): String {
        return text.split(" ").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { 
                if (it.isLowerCase()) it.titlecase() else it.toString() 
            }
        }
    }

    /**
     * Format speed (bytes per second) to human readable format
     */
    fun formatSpeed(bytesPerSecond: Long): String {
        if (bytesPerSecond <= 0) return "0 B/s"
        
        val units = arrayOf("B/s", "KB/s", "MB/s", "GB/s")
        val digitGroups = (ln(bytesPerSecond.toDouble()) / ln(1024.0)).toInt()
        
        return String.format(
            "%.1f %s",
            bytesPerSecond / 1024.0.pow(digitGroups.toDouble()),
            units[digitGroups]
        )
    }

    /**
     * Format progress percentage
     */
    fun formatProgress(progress: Float): String {
        return "${(progress * 100).toInt()}%"
    }
}
