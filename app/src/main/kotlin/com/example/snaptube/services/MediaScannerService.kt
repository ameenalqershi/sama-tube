package com.example.snaptube.services

import android.content.Context
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.LogUtils
import com.example.snaptube.utils.VideoUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import timber.log.Timber
import java.io.File

/**
 * Service for scanning and indexing downloaded media files
 * Handles media library integration and file organization
 */
class MediaScannerService(
    private val context: Context,
    private val fileUtils: FileUtils,
    private val videoUtils: VideoUtils,
    private val logUtils: LogUtils
) {

    private val scanScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * Scan completed download file and add to media library
     */
    suspend fun scanCompletedDownload(download: DownloadEntity): Result<ScanResult> {
        return withContext(Dispatchers.IO) {
            try {
                val file = File(download.outputPath)
                if (!file.exists()) {
                    return@withContext Result.failure(Exception("File not found: ${download.outputPath}"))
                }

                logUtils.info("MediaScannerService", "Scanning completed download: ${file.name}")
                
                val startTime = System.currentTimeMillis()
                
                // Extract media metadata
                val metadata = extractMediaMetadata(file)
                
                // Scan file into media library
                val mediaScanResult = scanFileIntoMediaLibrary(file)
                
                // Generate thumbnail if it's a video
                val thumbnailPath = if (videoUtils.isVideoFile(file)) {
                    generateThumbnail(file)
                } else null
                
                val scanDuration = System.currentTimeMillis() - startTime
                
                val result = ScanResult(
                    filePath = download.outputPath,
                    fileSize = file.length(),
                    metadata = metadata,
                    thumbnailPath = thumbnailPath,
                    addedToMediaLibrary = mediaScanResult,
                    scanDurationMs = scanDuration
                )
                
                logUtils.info("MediaScannerService", "Scan completed in ${scanDuration}ms: ${file.name}")
                Result.success(result)
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to scan download: ${download.outputPath}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Batch scan multiple files
     */
    suspend fun batchScanFiles(filePaths: List<String>): Result<List<ScanResult>> {
        return withContext(Dispatchers.IO) {
            try {
                logUtils.info("MediaScannerService", "Starting batch scan of ${filePaths.size} files")
                
                val results = mutableListOf<ScanResult>()
                val startTime = System.currentTimeMillis()
                
                // Process files sequentially to avoid complexity
                filePaths.forEach { filePath ->
                    try {
                        val result = scanSingleFile(filePath)
                        result.fold(
                            onSuccess = { scanResult -> results.add(scanResult) },
                            onFailure = { /* ignore failed scans */ }
                        )
                    } catch (e: Exception) {
                        logUtils.error("MediaScannerService", "Failed to scan file: $filePath", e)
                    }
                }
                
                val totalDuration = System.currentTimeMillis() - startTime
                logUtils.info("MediaScannerService", "Batch scan completed: ${results.size}/${filePaths.size} successful in ${totalDuration}ms")
                
                Result.success(results)
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to batch scan files", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Scan single file
     */
    private suspend fun scanSingleFile(filePath: String): Result<ScanResult> {
        return try {
            val file = File(filePath)
            if (!file.exists()) {
                return Result.failure(Exception("File not found: $filePath"))
            }

            val metadata = extractMediaMetadata(file)
            val mediaScanResult = scanFileIntoMediaLibrary(file)
            val thumbnailPath = if (videoUtils.isVideoFile(file)) {
                generateThumbnail(file)
            } else null

            val result = ScanResult(
                filePath = filePath,
                fileSize = file.length(),
                metadata = metadata,
                thumbnailPath = thumbnailPath,
                addedToMediaLibrary = mediaScanResult,
                scanDurationMs = 0L // Not tracking individual scan time in batch
            )

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Extract metadata from media file
     */
    private suspend fun extractMediaMetadata(file: File): MediaMetadata {
        return withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(file.absolutePath)
                
                val metadata = MediaMetadata(
                    title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
                    artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
                    album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
                    duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull(),
                    width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH)?.toIntOrNull(),
                    height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT)?.toIntOrNull(),
                    bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE)?.toLongOrNull(),
                    mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE),
                    hasAudio = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO) == "yes",
                    hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO) == "yes"
                )
                
                retriever.release()
                metadata
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to extract metadata: ${file.name}", e)
                MediaMetadata() // Return empty metadata
            }
        }
    }

    /**
     * Scan file into Android media library
     */
    private suspend fun scanFileIntoMediaLibrary(file: File): Boolean {
        return withContext(Dispatchers.Main) {
            try {
                val deferred = CompletableDeferred<Boolean>()
                
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(file.absolutePath),
                    null
                ) { path, uri ->
                    logUtils.debug("MediaScannerService", "Media scan result: $path -> $uri")
                    deferred.complete(uri != null)
                }
                
                // Wait for scan to complete with timeout
                withTimeoutOrNull(10000) { // 10 seconds timeout
                    deferred.await()
                } ?: false
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to scan file into media library: ${file.name}", e)
                false
            }
        }
    }

    /**
     * Generate thumbnail for video file
     */
    private suspend fun generateThumbnail(file: File): String? {
        return withContext(Dispatchers.IO) {
            try {
                val thumbnailsDir = File(context.cacheDir, "thumbnails")
                if (!thumbnailsDir.exists()) {
                    thumbnailsDir.mkdirs()
                }
                
                val thumbnailFile = File(thumbnailsDir, "${file.nameWithoutExtension}_thumb.jpg")
                
                // Use VideoUtils to generate thumbnail
                val success = videoUtils.generateThumbnailFile(file.absolutePath, thumbnailFile.absolutePath)
                
                if (success && thumbnailFile.exists()) {
                    logUtils.debug("MediaScannerService", "Thumbnail generated: ${thumbnailFile.name}")
                    thumbnailFile.absolutePath
                } else {
                    null
                }
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to generate thumbnail: ${file.name}", e)
                null
            }
        }
    }

    /**
     * Organize downloaded files by type
     */
    suspend fun organizeDownloadedFiles(downloadDirectory: String): Result<OrganizationResult> {
        return withContext(Dispatchers.IO) {
            try {
                logUtils.info("MediaScannerService", "Starting file organization in: $downloadDirectory")
                
                val downloadDir = File(downloadDirectory)
                if (!downloadDir.exists()) {
                    return@withContext Result.failure(Exception("Download directory not found"))
                }
                
                val files = downloadDir.listFiles { file ->
                    file.isFile && (videoUtils.isVideoFile(file) || videoUtils.isAudioFile(file))
                } ?: emptyArray()
                
                val videoDir = File(downloadDir, "Videos")
                val audioDir = File(downloadDir, "Audio")
                
                var movedVideos = 0
                var movedAudio = 0
                val errors = mutableListOf<String>()
                
                files.forEach { file ->
                    try {
                        val targetDir = when {
                            videoUtils.isVideoFile(file) -> {
                                if (!videoDir.exists()) videoDir.mkdirs()
                                videoDir
                            }
                            videoUtils.isAudioFile(file) -> {
                                if (!audioDir.exists()) audioDir.mkdirs()
                                audioDir
                            }
                            else -> return@forEach
                        }
                        
                        val targetFile = File(targetDir, file.name)
                        if (file.renameTo(targetFile)) {
                            if (videoUtils.isVideoFile(file)) movedVideos++ else movedAudio++
                            
                            // Re-scan moved file
                            scanFileIntoMediaLibrary(targetFile)
                        } else {
                            errors.add("Failed to move: ${file.name}")
                        }
                        
                    } catch (e: Exception) {
                        errors.add("Error moving ${file.name}: ${e.message}")
                    }
                }
                
                val result = OrganizationResult(
                    totalFiles = files.size,
                    movedVideos = movedVideos,
                    movedAudio = movedAudio,
                    errors = errors
                )
                
                logUtils.info("MediaScannerService", "Organization completed: ${movedVideos} videos, ${movedAudio} audio files moved")
                Result.success(result)
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to organize files", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Clean up old thumbnails
     */
    suspend fun cleanupOldThumbnails(maxAgeMs: Long = 7 * 24 * 60 * 60 * 1000L): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val thumbnailsDir = File(context.cacheDir, "thumbnails")
                if (!thumbnailsDir.exists()) {
                    return@withContext Result.success(0)
                }
                
                val cutoffTime = System.currentTimeMillis() - maxAgeMs
                val thumbnailFiles = thumbnailsDir.listFiles() ?: emptyArray()
                
                var deletedCount = 0
                thumbnailFiles.forEach { file ->
                    if (file.lastModified() < cutoffTime) {
                        if (file.delete()) {
                            deletedCount++
                        }
                    }
                }
                
                logUtils.info("MediaScannerService", "Cleaned up $deletedCount old thumbnails")
                Result.success(deletedCount)
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to cleanup thumbnails", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Get media library statistics
     */
    suspend fun getMediaLibraryStats(downloadDirectory: String): MediaLibraryStats {
        return withContext(Dispatchers.IO) {
            try {
                val downloadDir = File(downloadDirectory)
                if (!downloadDir.exists()) {
                    return@withContext MediaLibraryStats()
                }
                
                val allFiles = videoUtils.getAllFiles(downloadDir)
                val videoFiles = allFiles.filter { file -> videoUtils.isVideoFile(file) }
                val audioFiles = allFiles.filter { file -> videoUtils.isAudioFile(file) }
                
                val totalVideoSize = videoFiles.sumOf { file -> file.length() }
                val totalAudioSize = audioFiles.sumOf { file -> file.length() }
                
                val thumbnailsDir = File(context.cacheDir, "thumbnails")
                val thumbnailCount = thumbnailsDir.listFiles()?.size ?: 0
                
                MediaLibraryStats(
                    totalFiles = allFiles.size,
                    videoFiles = videoFiles.size,
                    audioFiles = audioFiles.size,
                    totalVideoSize = totalVideoSize,
                    totalAudioSize = totalAudioSize,
                    thumbnailCount = thumbnailCount
                )
                
            } catch (e: Exception) {
                logUtils.error("MediaScannerService", "Failed to get media library stats", e)
                MediaLibraryStats()
            }
        }
    }

    /**
     * Check if file exists in media library
     */
    suspend fun isFileInMediaLibrary(filePath: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // This is a simplified check - in a real implementation you might query ContentResolver
                val file = File(filePath)
                file.exists() && file.length() > 0
            } catch (e: Exception) {
                false
            }
        }
    }

    /**
     * Create file provider URI for sharing
     */
    fun createFileProviderUri(file: File): android.net.Uri? {
        return try {
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: Exception) {
            logUtils.error("MediaScannerService", "Failed to create file provider URI", e)
            null
        }
    }

    /**
     * Data classes for service responses
     */
    data class ScanResult(
        val filePath: String,
        val fileSize: Long,
        val metadata: MediaMetadata,
        val thumbnailPath: String?,
        val addedToMediaLibrary: Boolean,
        val scanDurationMs: Long
    )

    data class MediaMetadata(
        val title: String? = null,
        val artist: String? = null,
        val album: String? = null,
        val duration: Long? = null,
        val width: Int? = null,
        val height: Int? = null,
        val bitrate: Long? = null,
        val mimeType: String? = null,
        val hasAudio: Boolean = false,
        val hasVideo: Boolean = false
    )

    data class OrganizationResult(
        val totalFiles: Int,
        val movedVideos: Int,
        val movedAudio: Int,
        val errors: List<String>
    )

    data class MediaLibraryStats(
        val totalFiles: Int = 0,
        val videoFiles: Int = 0,
        val audioFiles: Int = 0,
        val totalVideoSize: Long = 0,
        val totalAudioSize: Long = 0,
        val thumbnailCount: Int = 0
    )

    /**
     * Clean up resources
     */
    fun cleanup() {
        scanScope.cancel()
    }
}
