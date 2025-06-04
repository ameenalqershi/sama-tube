package com.example.snaptube.repository

import com.example.snaptube.database.SnaptubeDatabase
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.database.entities.VideoInfoEntity
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.DownloadStatus
import com.example.snaptube.download.DownloadTask
import com.example.snaptube.models.VideoInfo
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.models.QualityOption
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File
import java.util.Date
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Repository for managing download operations and data persistence
 * Provides a clean interface between the UI and download/database layers
 */
class DownloadRepository(
    private val database: SnaptubeDatabase,
    private val fileUtils: FileUtils,
    private val logUtils: LogUtils
) : KoinComponent {

    private val downloadDao = database.downloadDao()
    private val videoInfoDao = database.videoInfoDao()
    
    // Lazy injection لتجنب circular dependency
    private val downloadManager: DownloadManager by inject()

    /**
     * بدء تحميل فيديو جديد
     */
    suspend fun startDownload(
        videoInfo: VideoInfo,
        selectedFormat: DownloadFormat,
        outputDirectory: String,
        customFileName: String? = null
    ): Result<String> {
        return try {
            // اختيار الرابط الصحيح
            val url = videoInfo.webpage_url.ifEmpty { videoInfo.originalUrl }
            // تحديد اسم الملف
            val fileName = customFileName ?: "${videoInfo.title}_${selectedFormat.quality}.${selectedFormat.extension}"
            val downloadPath = File(outputDirectory, fileName).absolutePath

            logUtils.info("DownloadRepository", "Starting download for: ${videoInfo.title}")
            // تفويض مدير التحميل لإنشاء الكيان في قاعدة البيانات وبدء التحميل
            val result = downloadManager.startDownload(
                url = url,
                selectedFormat = selectedFormat,
                downloadPath = downloadPath
            )
            if (result.isSuccess) {
                logUtils.info("DownloadRepository", "Download started: ${result.getOrNull()}")
            } else {
                logUtils.error("DownloadRepository", "Failed to start download", result.exceptionOrNull())
            }
            result
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Error in startDownload", e)
            Result.failure(e)
        }
    }

    /**
     * Pause a download
     */
    suspend fun pauseDownload(downloadId: String): Result<Unit> {
        return try {
            downloadManager.pauseDownload(downloadId)
            updateDownloadStatus(downloadId, DownloadStatus.PAUSED)
            logUtils.info("DownloadRepository", "Download paused: $downloadId")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to pause download: $downloadId", e)
            Result.failure(e)
        }
    }

    /**
     * Resume a download
     */
    suspend fun resumeDownload(downloadId: String): Result<Unit> {
        return try {
            downloadManager.resumeDownload(downloadId)
            updateDownloadStatus(downloadId, DownloadStatus.DOWNLOADING)
            logUtils.info("DownloadRepository", "Download resumed: $downloadId")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to resume download: $downloadId", e)
            Result.failure(e)
        }
    }

    /**
     * Cancel a download
     */
    suspend fun cancelDownload(downloadId: String, deleteFile: Boolean = true): Result<Unit> {
        return try {
            downloadManager.cancelDownload(downloadId)
            
            if (deleteFile) {
                val download = downloadDao.getDownloadById(downloadId)
                download?.let {
                    fileUtils.deleteFile(File(it.downloadPath))
                }
            }
            
            updateDownloadStatus(downloadId, DownloadStatus.CANCELLED)
            logUtils.info("DownloadRepository", "Download cancelled: $downloadId")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to cancel download: $downloadId", e)
            Result.failure(e)
        }
    }

    /**
     * Retry a failed download
     */
    suspend fun retryDownload(downloadId: String): Result<Unit> {
        return try {
            val download = downloadDao.getDownloadById(downloadId)
                ?: return Result.failure(IllegalArgumentException("Download not found: $downloadId"))

            // Reset state in database
            updateDownloadStatus(downloadId, DownloadStatus.PENDING)

            // Start download again
            val result = downloadManager.startDownload(
                url = download.url,
                selectedFormat = DownloadFormat(
                    formatId = download.formatId,
                    extension = download.fileExtension,
                    quality = download.qualityLabel
                ),
                downloadPath = download.downloadPath,
                audioOnly = download.audioOnly
            )

            if (result.isFailure) {
                logUtils.error("DownloadRepository", "Failed to retry download: $downloadId", result.exceptionOrNull())
            } else {
                logUtils.info("DownloadRepository", "Download retried: $downloadId")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to retry download: $downloadId", e)
            Result.failure(e)
        }
    }

    /**
     * Delete a download and its file
     */
    suspend fun deleteDownload(downloadId: String, deleteFile: Boolean = true): Result<Unit> {
        return try {
            val download = downloadDao.getDownloadById(downloadId)

            // Cancel if still downloading
            if (download?.isInProgress() == true) {
                downloadManager.cancelDownload(downloadId)
            }

            // Delete file if requested
            if (deleteFile && download != null) {
                fileUtils.deleteFile(File(download.outputPath))
            }

            // Remove from database
            downloadDao.deleteDownloadById(downloadId)

            logUtils.info("DownloadRepository", "Download deleted: $downloadId")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to delete download: $downloadId", e)
            Result.failure(e)
        }
    }

    /**
     * Get all downloads
     */
    fun getAllDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getAllDownloadsFlow()
    }

    /**
     * Get downloads by status
     */
    fun getDownloadsByStatus(status: DownloadStatus): Flow<List<DownloadEntity>> {
        return downloadDao.getDownloadsByStatusFlow(status)
    }

    /**
     * Get active downloads (downloading, queued, paused)
     */
    fun getActiveDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getActiveDownloadsFlow()
    }

    /**
     * Get completed downloads
     */
    fun getCompletedDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getCompletedDownloadsFlow()
    }

    /**
     * Get failed downloads
     */
    fun getFailedDownloads(): Flow<List<DownloadEntity>> {
        return downloadDao.getFailedDownloadsFlow()
    }

    /**
     * Get download by ID
     */
    suspend fun getDownloadById(downloadId: String): DownloadEntity? {
        return downloadDao.getDownloadById(downloadId)
    }

    /**
     * Get download with video info
     */
    suspend fun getDownloadWithVideoInfo(downloadId: String): Pair<DownloadEntity, VideoInfoEntity>? {
        val download = downloadDao.getDownloadById(downloadId) ?: return null
        val videoInfo = videoInfoDao.getVideoInfoById(download.id) ?: return null
        return Pair(download, videoInfo)
    }

    /**
     * Search downloads
     */
    fun searchDownloads(query: String): Flow<List<DownloadEntity>> {
        return downloadDao.searchDownloads("%$query%")
    }

    /**
     * Get download statistics
     */
    suspend fun getDownloadStatistics(): DownloadStatistics {
        val totalDownloads = downloadDao.getTotalDownloads()
        val completedDownloads = downloadDao.getCompletedDownloadsCount()
        val failedDownloads = downloadDao.getFailedDownloadsCount()
        val activeDownloads = downloadDao.getActiveDownloadsCount()
        val totalSize = downloadDao.getTotalDownloadedSize()
        val averageSpeed = downloadDao.getAverageDownloadSpeed()
        
        return DownloadStatistics(
            totalDownloads = totalDownloads,
            completedDownloads = completedDownloads,
            failedDownloads = failedDownloads,
            activeDownloads = activeDownloads,
            totalSize = totalSize,
            averageSpeed = averageSpeed
        )
    }

    /**
     * Clean up completed downloads older than specified days
     */
    suspend fun cleanupOldDownloads(olderThanDays: Int = 30): Result<Int> {
        return try {
            val cutoffDate = Date(System.currentTimeMillis() - (olderThanDays * 24 * 60 * 60 * 1000L))
            val deletedCount = downloadDao.deleteOldCompletedDownloads(cutoffDate)
            
            logUtils.info("DownloadRepository", "Cleaned up $deletedCount old downloads")
            Result.success(deletedCount)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to cleanup old downloads", e)
            Result.failure(e)
        }
    }

    /**
     * Clean up failed downloads
     */
    suspend fun cleanupFailedDownloads(deleteFiles: Boolean = true): Result<Int> {
        return try {
            val failedDownloads = downloadDao.getFailedDownloads()
            
            if (deleteFiles) {
                failedDownloads.forEach { download ->
                    fileUtils.deleteFile(File(download.outputPath))
                }
            }
            
            val deletedCount = downloadDao.deleteFailedDownloads()
            
            logUtils.info("DownloadRepository", "Cleaned up $deletedCount failed downloads")
            Result.success(deletedCount)
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to cleanup failed downloads", e)
            Result.failure(e)
        }
    }

    /**
     * Update download status in database
     */
    suspend fun updateDownloadStatus(downloadId: String, status: DownloadStatus) {
        downloadDao.getDownloadById(downloadId)?.let { download ->
            val updated = download.copy(
                status = status,
                updatedAt = Date()
            )
            downloadDao.updateDownload(updated)
        }
    }

    /**
     * Update download progress in database
     */
    suspend fun updateDownloadProgress(downloadId: String, progress: Int) {
        downloadDao.getDownloadById(downloadId)?.let { download ->
            val updated = download.copy(
                progress = progress / 100f,
                updatedAt = Date()
            )
            downloadDao.updateDownload(updated)
        }
    }

    /**
     * Mark download as completed
     */
    suspend fun markDownloadCompleted(downloadId: String, finalFileSize: Long) {
        try {
            val download = downloadDao.getDownloadById(downloadId)
            if (download != null) {
                val updatedDownload = download.copy(
                    status = DownloadStatus.COMPLETED,
                    progress = 100f,
                    downloadedBytes = finalFileSize,
                    totalBytes = finalFileSize,
                    completedAt = Date(),
                    updatedAt = Date()
                )
                downloadDao.updateDownload(updatedDownload)
                
                logUtils.info("DownloadRepository", "Download completed: $downloadId")
            }
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to mark download as completed: $downloadId", e)
        }
    }

    /**
     * Mark download as failed
     */
    suspend fun markDownloadFailed(downloadId: String, error: String) {
        try {
            val download = downloadDao.getDownloadById(downloadId)
            if (download != null) {
                val updatedDownload = download.copy(
                    status = DownloadStatus.FAILED,
                    error = error,
                    updatedAt = Date()
                )
                downloadDao.updateDownload(updatedDownload)
                
                logUtils.error("DownloadRepository", "Download failed: $downloadId - $error")
            }
        } catch (e: Exception) {
            logUtils.error("DownloadRepository", "Failed to mark download as failed: $downloadId", e)
        }
    }

    /**
     * Get download progress flow
     */
    fun getDownloadProgressFlow(downloadId: String): Flow<DownloadProgress?> {
        return downloadDao.getDownloadByIdFlow(downloadId).map { download ->
            download?.let {
                DownloadProgress(
                    downloadId = it.id,
                    progress = it.progress,
                    downloadedBytes = it.downloadedBytes,
                    totalBytes = it.totalBytes,
                    speed = it.speed,
                    status = it.status
                )
            }
        }.distinctUntilChanged()
    }

    /**
     * Get overall download progress
     */
    fun getOverallProgressFlow(): Flow<OverallProgress> {
        val statsFlow = flow { emit(getDownloadStatistics()) }
        return combine(
            downloadDao.getActiveDownloadsFlow(),
            statsFlow
        ) { activeDownloads, stats ->
            val totalActiveSize = activeDownloads.sumOf { it.totalBytes }
            val totalDownloadedSize = activeDownloads.sumOf { it.downloadedBytes }
            val overallProgress = if (totalActiveSize > 0) {
                ((totalDownloadedSize.toDouble() / totalActiveSize) * 100).toInt()
            } else 0
            
            OverallProgress(
                activeDownloads = activeDownloads.size,
                totalProgress = overallProgress,
                totalDownloadedSize = totalDownloadedSize,
                totalSize = totalActiveSize,
                averageSpeed = stats.averageSpeed
            )
        }.distinctUntilChanged()
    }

    /**
     * Generate unique download ID
     */
    private fun generateDownloadId(url: String, format: DownloadFormat): String {
        val timestamp = System.currentTimeMillis()
        val urlHash = url.hashCode().toString(16)
        val formatHash = format.formatId.hashCode().toString(16)
        return "dl_${timestamp}_${urlHash}_$formatHash"
    }

    /**
     * Generate file name for download
     */
    private fun generateFileName(videoInfo: VideoInfo, format: DownloadFormat): String {
        val sanitizedTitle = fileUtils.sanitizeFileName(videoInfo.title)
        // Determine file extension
        val extension = if (format.extension.isNotEmpty()) {
            format.extension
        } else {
            when {
                format.isVideoOnly() -> "mp4"
                format.isAudioOnly() -> "m4a"
                else -> "mp4"
            }
        }
        // Get quality descriptor
        val qualityDesc = format.getResolutionString().takeIf { it.isNotEmpty() } ?: format.quality
        return if (qualityDesc.isNotEmpty()) {
            "${sanitizedTitle}_${qualityDesc}.$extension"
        } else {
            "${sanitizedTitle}.$extension"
        }
    }

    /**
     * الحصول على التحميلات المعلقة (Stuck) بعد مدة معينة بالمسجات
     */
    suspend fun getStuckDownloads(timeoutMinutes: Int): List<DownloadEntity> {
        val staleTime = System.currentTimeMillis() - timeoutMinutes * 60 * 1000L
        return downloadDao.getStuckDownloads(staleTime)
    }

    /**
     * الحصول على التحميلات المكتملة بدون ملف
     */
    suspend fun getCompletedDownloadsWithoutFile(): List<DownloadEntity> {
        return downloadDao.getCompletedDownloadsWithoutFile()
    }

    /**
     * الحصول على الملفات المؤقتة للمحاولة
     */
    suspend fun getTemporaryFiles(): List<java.io.File> {
        return downloadDao.getTemporaryFiles().map { java.io.File(it) }
    }

    /**
     * Insert a new download entity into the database
     */
    suspend fun insertDownload(downloadEntity: DownloadEntity): Long {
        return downloadDao.insertDownload(downloadEntity)
    }

    /**
     * Data classes for repository responses
     */
    data class DownloadStatistics(
        val totalDownloads: Int,
        val completedDownloads: Int,
        val failedDownloads: Int,
        val activeDownloads: Int,
        val totalSize: Long,
        val averageSpeed: Double
    )

    data class DownloadProgress(
        val downloadId: String,
        val progress: Float,
        val downloadedBytes: Long,
        val totalBytes: Long,
        val speed: Long,
        val status: DownloadStatus
    )

    data class OverallProgress(
        val activeDownloads: Int,
        val totalProgress: Int,
        val totalDownloadedSize: Long,
        val totalSize: Long,
        val averageSpeed: Double
    )
}
