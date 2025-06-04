package com.example.snaptube.utils

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow

class FileUtils(private val context: Context) {
    
    companion object {
        const val DOWNLOADS_FOLDER = "Snaptube"
        const val VIDEOS_FOLDER = "Videos"
        const val AUDIO_FOLDER = "Audio"
        const val IMAGES_FOLDER = "Images"
        const val TEMP_FOLDER = "Temp"
        
        private val SUPPORTED_VIDEO_EXTENSIONS = setOf(
            "mp4", "mkv", "avi", "mov", "wmv", "flv", "webm", "m4v", "3gp"
        )
        
        private val SUPPORTED_AUDIO_EXTENSIONS = setOf(
            "mp3", "m4a", "aac", "ogg", "flac", "wav", "opus", "wma"
        )
        
        private val SUPPORTED_IMAGE_EXTENSIONS = setOf(
            "jpg", "jpeg", "png", "gif", "bmp", "webp", "svg"
        )
    }
    
    // Directory management
    fun getDownloadsDirectory(): File {
        val externalDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
            ?: context.filesDir
        
        val snaptubeDir = File(externalDir, DOWNLOADS_FOLDER)
        if (!snaptubeDir.exists()) {
            snaptubeDir.mkdirs()
        }
        return snaptubeDir
    }
    
    fun getVideosDirectory(): File {
        val downloadsDir = getDownloadsDirectory()
        val videosDir = File(downloadsDir, VIDEOS_FOLDER)
        if (!videosDir.exists()) {
            videosDir.mkdirs()
        }
        return videosDir
    }
    
    fun getAudioDirectory(): File {
        val downloadsDir = getDownloadsDirectory()
        val audioDir = File(downloadsDir, AUDIO_FOLDER)
        if (!audioDir.exists()) {
            audioDir.mkdirs()
        }
        return audioDir
    }
    
    fun getImagesDirectory(): File {
        val downloadsDir = getDownloadsDirectory()
        val imagesDir = File(downloadsDir, IMAGES_FOLDER)
        if (!imagesDir.exists()) {
            imagesDir.mkdirs()
        }
        return imagesDir
    }
    
    fun getTempDirectory(): File {
        val cacheDir = context.cacheDir
        val tempDir = File(cacheDir, TEMP_FOLDER)
        if (!tempDir.exists()) {
            tempDir.mkdirs()
        }
        return tempDir
    }
    
    fun getPublicDownloadsDirectory(): File? {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    }
    
    fun getPublicMoviesDirectory(): File? {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
    }
    
    fun getPublicMusicDirectory(): File? {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
    }
    
    // File operations
    fun generateUniqueFileName(directory: File, baseName: String, extension: String): String {
        val sanitizedBaseName = sanitizeFileName(baseName)
        var fileName = "$sanitizedBaseName.$extension"
        var counter = 1
        
        while (File(directory, fileName).exists()) {
            fileName = "${sanitizedBaseName}_$counter.$extension"
            counter++
        }
        
        return fileName
    }
    
    fun sanitizeFileName(fileName: String): String {
        // Remove invalid characters for file names
        val invalidChars = charArrayOf('/', '\\', '?', '%', '*', ':', '|', '"', '<', '>', '.', ' ')
        var sanitized = fileName
        
        invalidChars.forEach { char ->
            sanitized = sanitized.replace(char, '_')
        }
        
        // Remove multiple consecutive underscores
        sanitized = sanitized.replace(Regex("_+"), "_")
        
        // Remove leading/trailing underscores
        sanitized = sanitized.trim('_')
        
        // Limit length
        if (sanitized.length > 100) {
            sanitized = sanitized.substring(0, 100)
        }
        
        return sanitized.ifEmpty { "download" }
    }
    
    fun createTempFile(prefix: String = "snaptube", suffix: String = ".tmp"): File {
        return File.createTempFile(prefix, suffix, getTempDirectory())
    }
    
    fun copyFile(source: File, destination: File): Boolean {
        return try {
            if (!destination.parentFile?.exists()!!) {
                destination.parentFile?.mkdirs()
            }
            
            FileInputStream(source).use { input ->
                FileOutputStream(destination).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: IOException) {
            Timber.e(e, "Failed to copy file from ${source.path} to ${destination.path}")
            false
        }
    }
    
    fun moveFile(source: File, destination: File): Boolean {
        return try {
            if (copyFile(source, destination)) {
                source.delete()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to move file from ${source.path} to ${destination.path}")
            false
        }
    }
    
    fun deleteFile(file: File): Boolean {
        return try {
            if (file.exists()) {
                val deleted = file.delete()
                if (deleted) {
                    Timber.d("Deleted file: ${file.path}")
                } else {
                    Timber.w("Failed to delete file: ${file.path}")
                }
                deleted
            } else {
                true // File doesn't exist, consider it deleted
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting file: ${file.path}")
            false
        }
    }
    
    fun deleteDirectory(directory: File): Boolean {
        return try {
            if (directory.exists()) {
                directory.deleteRecursively()
            } else {
                true
            }
        } catch (e: Exception) {
            Timber.e(e, "Error deleting directory: ${directory.path}")
            false
        }
    }
    
    // File information
    fun getFileSize(file: File): Long {
        return if (file.exists() && file.isFile) file.length() else 0L
    }
    
    fun getDirectorySize(directory: File): Long {
        return try {
            if (directory.exists() && directory.isDirectory) {
                directory.walkTopDown().filter { it.isFile }.map { it.length() }.sum()
            } else {
                0L
            }
        } catch (e: Exception) {
            Timber.e(e, "Error calculating directory size: ${directory.path}")
            0L
        }
    }
    
    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        
        val size = bytes / 1024.0.pow(digitGroups.toDouble())
        return "%.1f %s".format(size, units[digitGroups])
    }
    
    fun getFileExtension(fileName: String): String {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex > 0 && lastDotIndex < fileName.length - 1) {
            fileName.substring(lastDotIndex + 1).lowercase()
        } else {
            ""
        }
    }
    
    fun getFileNameWithoutExtension(fileName: String): String {
        val lastDotIndex = fileName.lastIndexOf('.')
        return if (lastDotIndex > 0) {
            fileName.substring(0, lastDotIndex)
        } else {
            fileName
        }
    }
    
    fun getMimeType(file: File): String? {
        val extension = getFileExtension(file.name)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
    
    fun isVideoFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName)
        return SUPPORTED_VIDEO_EXTENSIONS.contains(extension)
    }
    
    fun isAudioFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName)
        return SUPPORTED_AUDIO_EXTENSIONS.contains(extension)
    }
    
    fun isImageFile(fileName: String): Boolean {
        val extension = getFileExtension(fileName)
        return SUPPORTED_IMAGE_EXTENSIONS.contains(extension)
    }
    
    // Storage information
    fun getAvailableStorageSpace(): Long {
        return try {
            val downloadsDir = getDownloadsDirectory()
            downloadsDir.freeSpace
        } catch (e: Exception) {
            Timber.e(e, "Error getting available storage space")
            0L
        }
    }
    
    fun getTotalStorageSpace(): Long {
        return try {
            val downloadsDir = getDownloadsDirectory()
            downloadsDir.totalSpace
        } catch (e: Exception) {
            Timber.e(e, "Error getting total storage space")
            0L
        }
    }
    
    fun hasEnoughSpace(requiredBytes: Long): Boolean {
        val availableSpace = getAvailableStorageSpace()
        val buffer = 100 * 1024 * 1024L // 100 MB buffer
        return availableSpace > (requiredBytes + buffer)
    }
    
    // Media scanning
    fun scanMediaFile(file: File, callback: ((String?, Uri?) -> Unit)? = null) {
        MediaScannerConnection.scanFile(
            context,
            arrayOf(file.absolutePath),
            arrayOf(getMimeType(file)),
            callback
        )
    }
    
    fun scanMediaFiles(files: List<File>, callback: ((String?, Uri?) -> Unit)? = null) {
        val paths = files.map { it.absolutePath }.toTypedArray()
        val mimeTypes = files.map { getMimeType(it) }.toTypedArray()
        
        MediaScannerConnection.scanFile(context, paths, mimeTypes, callback)
    }
    
    // File sharing
    fun shareFile(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = getMimeType(file) ?: "*/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            val chooser = Intent.createChooser(intent, "مشاركة الملف")
            context.startActivity(chooser)
        } catch (e: Exception) {
            Timber.e(e, "Error sharing file: ${file.path}")
        }
    }
    
    fun openFile(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, getMimeType(file))
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            
            context.startActivity(intent)
        } catch (e: Exception) {
            Timber.e(e, "Error opening file: ${file.path}")
        }
    }
    
    // Cleanup operations
    fun cleanupTempFiles() {
        try {
            val tempDir = getTempDirectory()
            val files = tempDir.listFiles() ?: return
            
            val oneDayAgo = System.currentTimeMillis() - 24 * 60 * 60 * 1000L
            var deletedCount = 0
            
            files.forEach { file ->
                if (file.lastModified() < oneDayAgo) {
                    if (deleteFile(file)) {
                        deletedCount++
                    }
                }
            }
            
            Timber.i("Cleaned up $deletedCount temporary files")
        } catch (e: Exception) {
            Timber.e(e, "Error cleaning up temp files")
        }
    }
    
    fun cleanupOldDownloads(maxAge: Long = 30 * 24 * 60 * 60 * 1000L) {
        try {
            val downloadsDir = getDownloadsDirectory()
            val cutoffTime = System.currentTimeMillis() - maxAge
            var deletedCount = 0
            
            downloadsDir.walkTopDown().forEach { file ->
                if (file.isFile && file.lastModified() < cutoffTime) {
                    if (deleteFile(file)) {
                        deletedCount++
                    }
                }
            }
            
            Timber.i("Cleaned up $deletedCount old download files")
        } catch (e: Exception) {
            Timber.e(e, "Error cleaning up old downloads")
        }
    }
    
    // Filename generation
    fun generateTimestampedFileName(baseName: String, extension: String): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        val sanitizedBaseName = sanitizeFileName(baseName)
        return "${sanitizedBaseName}_$timestamp.$extension"
    }
    
    fun generateDownloadFileName(title: String, author: String?, quality: String?, extension: String): String {
        val parts = mutableListOf<String>()
        
        parts.add(sanitizeFileName(title))
        
        if (!author.isNullOrBlank()) {
            parts.add(sanitizeFileName(author))
        }
        
        if (!quality.isNullOrBlank()) {
            parts.add(quality)
        }
        
        val fileName = parts.joinToString("_")
        return "$fileName.$extension"
    }
    
    // File validation
    fun isValidDownloadPath(path: String): Boolean {
        return try {
            val file = File(path)
            file.parentFile?.canWrite() ?: false
        } catch (e: Exception) {
            false
        }
    }
    
    fun createDirectoryIfNotExists(path: String): Boolean {
        return try {
            val directory = File(path)
            if (!directory.exists()) {
                directory.mkdirs()
            } else {
                true
            }
        } catch (e: Exception) {
            Timber.e(e, "Error creating directory: $path")
            false
        }
    }
}
