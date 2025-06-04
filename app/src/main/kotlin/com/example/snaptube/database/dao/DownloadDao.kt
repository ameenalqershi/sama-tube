package com.example.snaptube.database.dao

import androidx.room.*
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.download.DownloadStatus
import com.example.snaptube.download.DownloadState
import com.example.snaptube.repository.DownloadRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface DownloadDao {
    
    @Query("SELECT * FROM downloads ORDER BY createdAt DESC")
    fun getAllDownloads(): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE id = :id")
    suspend fun getDownloadById(id: String): DownloadEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownload(download: DownloadEntity): Long
    
    @Update
    suspend fun updateDownload(download: DownloadEntity): Int
    
    @Delete
    suspend fun deleteDownload(download: DownloadEntity): Int
    
    @Query("SELECT * FROM downloads WHERE platform = :platform AND isVisible = 1 ORDER BY createdAt DESC")
    fun getDownloadsByPlatform(platform: String): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' AND isVisible = 1 ORDER BY createdAt DESC")
    fun searchDownloads(query: String): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE createdAt BETWEEN :startDate AND :endDate AND isVisible = 1 ORDER BY createdAt DESC")
    fun getDownloadsByDateRange(startDate: Date, endDate: Date): Flow<List<DownloadEntity>>
    
    @Query("SELECT COUNT(*) FROM downloads WHERE status = :status AND isVisible = 1")
    suspend fun getDownloadCountByStatus(status: DownloadStatus): Int
    
    @Query("SELECT COUNT(*) FROM downloads WHERE isVisible = 1")
    suspend fun getTotalDownloadCount(): Int
    
    @Query("SELECT SUM(downloadedBytes) FROM downloads WHERE status = :status AND isVisible = 1")
    suspend fun getTotalDownloadedBytes(status: DownloadStatus = DownloadStatus.COMPLETED): Long
    
    @Query("SELECT AVG(progress) FROM downloads WHERE status IN (:activeStatuses) AND isVisible = 1")
    suspend fun getAverageProgress(activeStatuses: List<DownloadStatus>): Float
    
    @Query("SELECT * FROM downloads WHERE status = :status AND retryCount < maxRetries AND isVisible = 1 ORDER BY retryCount ASC, createdAt ASC")
    suspend fun getRetryableDownloads(status: DownloadStatus = DownloadStatus.FAILED): List<DownloadEntity>
    
    @Query("SELECT * FROM downloads WHERE status IN (:statuses) AND isVisible = 1 ORDER BY priority DESC, createdAt ASC LIMIT 1")
    suspend fun getNextPendingDownload(statuses: List<DownloadStatus>): DownloadEntity?
    
    @Query("SELECT DISTINCT platform FROM downloads WHERE isVisible = 1 ORDER BY platform ASC")
    suspend fun getAllPlatforms(): List<String>
    
    @Query("SELECT DISTINCT author FROM downloads WHERE author IS NOT NULL AND isVisible = 1 ORDER BY author ASC")
    suspend fun getAllAuthors(): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDownloads(downloads: List<DownloadEntity>): List<Long>
    
    @Update
    suspend fun updateDownloads(downloads: List<DownloadEntity>): Int
    
    @Query("UPDATE downloads SET status = :status, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDownloadStatus(id: String, status: DownloadStatus, updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET progress = :progress, downloadedBytes = :downloadedBytes, speed = :speed, eta = :eta, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDownloadProgress(
        id: String, 
        progress: Float, 
        downloadedBytes: Long, 
        speed: Long, 
        eta: Long, 
        updatedAt: Date = Date()
    ): Int
    
    @Query("UPDATE downloads SET status = :status, error = :error, retryCount = retryCount + 1, updatedAt = :updatedAt WHERE id = :id")
    suspend fun markDownloadFailed(id: String, status: DownloadStatus, error: String, updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET status = :status, completedAt = :completedAt, progress = 1.0, updatedAt = :updatedAt WHERE id = :id")
    suspend fun markDownloadCompleted(id: String, status: DownloadStatus, completedAt: Date = Date(), updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET workerId = :workerId, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDownloadWorkerId(id: String, workerId: String, updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET priority = :priority, updatedAt = :updatedAt WHERE id = :id")
    suspend fun updateDownloadPriority(id: String, priority: Int, updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET isVisible = 0, updatedAt = :updatedAt WHERE id = :id")
    suspend fun hideDownload(id: String, updatedAt: Date = Date()): Int
    
    @Query("UPDATE downloads SET isVisible = :visible, updatedAt = :updatedAt WHERE id = :id")
    suspend fun setDownloadVisibility(id: String, visible: Boolean, updatedAt: Date = Date()): Int
    
    @Query("DELETE FROM downloads WHERE id = :id")
    suspend fun deleteDownloadById(id: String): Int
    
    @Query("DELETE FROM downloads WHERE status = :status")
    suspend fun deleteDownloadsByStatus(status: DownloadStatus): Int
    
    @Query("DELETE FROM downloads WHERE isVisible = 0")
    suspend fun deleteHiddenDownloads(): Int
    
    @Query("DELETE FROM downloads WHERE completedAt < :beforeDate AND status = :status")
    suspend fun deleteOldCompletedDownloads(beforeDate: Date, status: DownloadStatus = DownloadStatus.COMPLETED): Int
    
    @Query("DELETE FROM downloads")
    suspend fun deleteAllDownloads(): Int
    
    // Cleanup operations
    @Query("UPDATE downloads SET status = :newStatus WHERE status = :oldStatus AND updatedAt < :beforeDate")
    suspend fun cleanupStaleDownloads(oldStatus: DownloadStatus, newStatus: DownloadStatus, beforeDate: java.util.Date): Int
    
    @Query("UPDATE downloads SET workerId = NULL WHERE workerId IS NOT NULL AND status NOT IN (:activeStatuses)")
    suspend fun clearInactiveWorkerIds(activeStatuses: List<DownloadStatus>): Int
    
    // Statistics
    @Query("""
        SELECT 
            COUNT(*) as totalDownloads,
            SUM(CASE WHEN status = 'COMPLETED' THEN 1 ELSE 0 END) as completedDownloads,
            SUM(CASE WHEN status = 'FAILED' THEN 1 ELSE 0 END) as failedDownloads,
            SUM(CASE WHEN status IN ('PENDING', 'DOWNLOADING', 'PROCESSING', 'EXTRACTING') THEN 1 ELSE 0 END) as activeDownloads,
            SUM(downloadedBytes) as totalSize,
            AVG(CASE WHEN status IN ('DOWNLOADING', 'PROCESSING') AND speed > 0 THEN speed ELSE NULL END) as averageSpeed
        FROM downloads WHERE isVisible = 1
    """)
    suspend fun getDownloadStatistics(): DownloadRepository.DownloadStatistics
    
    @Query("DELETE FROM downloads WHERE status = 'FAILED' AND updatedAt < :timestamp")
    suspend fun deleteFailedDownloadsOlderThan(timestamp: Long): Int
    
    @Query("SELECT COUNT(*) FROM downloads")
    suspend fun getDownloadCount(): Int
    
    @Query("SELECT COUNT(*) FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING')")
    suspend fun getActiveDownloadCount(): Int
    
    // إضافة الدوال المفقودة
    @Query("SELECT * FROM downloads WHERE status = :status AND isVisible = 1 ORDER BY createdAt DESC")
    fun getDownloadsByStatusFlow(status: DownloadStatus): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads ORDER BY createdAt DESC")
    fun getAllDownloadsFlow(): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING', 'PENDING') AND isVisible = 1 ORDER BY createdAt DESC")
    fun getActiveDownloadsFlow(): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1 ORDER BY completedAt DESC")
    fun getCompletedDownloadsFlow(): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE status = 'FAILED' AND isVisible = 1 ORDER BY updatedAt DESC")
    fun getFailedDownloadsFlow(): Flow<List<DownloadEntity>>
    
    @Query("SELECT * FROM downloads WHERE id = :id")
    fun getDownloadByIdFlow(id: String): Flow<DownloadEntity?>
    
    @Query("SELECT * FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING') AND updatedAt < :staleTime")
    suspend fun getStuckDownloads(staleTime: Long): List<DownloadEntity>
    
    @Query("SELECT * FROM downloads WHERE status = 'COMPLETED' AND outputPath NOT NULL")
    suspend fun getCompletedDownloadsWithoutFile(): List<DownloadEntity>
    
    @Query("SELECT outputPath FROM downloads WHERE outputPath LIKE '%.tmp' OR outputPath LIKE '%.part'")
    suspend fun getTemporaryFiles(): List<String>
    
    @Query("SELECT COUNT(*) FROM downloads WHERE isVisible = 1")
    suspend fun getTotalDownloads(): Int
    
    @Query("SELECT COUNT(*) FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1")
    suspend fun getCompletedDownloadsCount(): Int
    
    @Query("SELECT COUNT(*) FROM downloads WHERE status = 'FAILED' AND isVisible = 1")
    suspend fun getFailedDownloadsCount(): Int
    
    @Query("SELECT COUNT(*) FROM downloads WHERE status IN ('DOWNLOADING', 'PROCESSING', 'EXTRACTING', 'PENDING') AND isVisible = 1")
    suspend fun getActiveDownloadsCount(): Int
    
    @Query("SELECT SUM(downloadedBytes) FROM downloads WHERE status = 'COMPLETED' AND isVisible = 1")
    suspend fun getTotalDownloadedSize(): Long
    
    @Query("SELECT AVG(speed) FROM downloads WHERE status = 'DOWNLOADING' AND speed > 0")
    suspend fun getAverageDownloadSpeed(): Double
    
    @Query("DELETE FROM downloads WHERE status = 'COMPLETED' AND completedAt < :timestamp")
    suspend fun deleteCompletedDownloadsOlderThan(timestamp: Long): Int
    
    @Query("SELECT * FROM downloads WHERE status = 'FAILED' AND isVisible = 1")
    suspend fun getFailedDownloads(): List<DownloadEntity>
    
    @Query("DELETE FROM downloads WHERE status = 'FAILED'")
    suspend fun deleteFailedDownloads(): Int
    
    @Query("SELECT * FROM downloads WHERE updatedAt > :timestamp ORDER BY updatedAt DESC")
    fun getDownloadStatisticsFlow(timestamp: Long): Flow<List<DownloadEntity>>
    
    @Query("UPDATE downloads SET outputPath = :filePath WHERE id = :id")
    suspend fun updateDownloadFilePath(id: String, filePath: String): Int

    // ...existing code...
}
