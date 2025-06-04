package com.example.snaptube.database.dao

import androidx.room.*
import com.example.snaptube.database.entities.PlaylistEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PlaylistDao {
    
    @Query("SELECT * FROM playlists ORDER BY lastAccessed DESC")
    fun getAllPlaylists(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE url = :url")
    suspend fun getPlaylistByUrl(url: String): PlaylistEntity?
    
    @Query("SELECT * FROM playlists WHERE playlistId = :playlistId LIMIT 1")
    suspend fun getPlaylistById(playlistId: String): PlaylistEntity?
    
    @Query("SELECT * FROM playlists WHERE platform = :platform ORDER BY lastAccessed DESC")
    fun getPlaylistsByPlatform(platform: String): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE author = :author ORDER BY lastAccessed DESC")
    fun getPlaylistsByAuthor(author: String): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE isBookmarked = 1 ORDER BY lastAccessed DESC")
    fun getBookmarkedPlaylists(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE downloadedCount > 0 ORDER BY downloadProgress DESC, lastAccessed DESC")
    fun getPlaylistsWithDownloads(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE downloadedCount = videoCount AND videoCount > 0 ORDER BY downloadCompletedAt DESC")
    fun getCompletedPlaylists(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE downloadedCount > 0 AND downloadedCount < videoCount ORDER BY downloadProgress DESC")
    fun getInProgressPlaylists(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE failedCount > 0 ORDER BY failedCount DESC, lastAccessed DESC")
    fun getPlaylistsWithFailures(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' ORDER BY accessCount DESC")
    fun searchPlaylists(query: String): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE videoCount BETWEEN :minCount AND :maxCount ORDER BY videoCount DESC")
    fun getPlaylistsByVideoCount(minCount: Int, maxCount: Int): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE totalDuration BETWEEN :minDuration AND :maxDuration ORDER BY totalDuration DESC")
    fun getPlaylistsByDuration(minDuration: Long, maxDuration: Long): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists WHERE createdAt BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    fun getPlaylistsByDateRange(startDate: Date, endDate: Date): Flow<List<PlaylistEntity>>
    
    @Query("SELECT * FROM playlists ORDER BY accessCount DESC LIMIT :limit")
    suspend fun getMostAccessedPlaylists(limit: Int = 10): List<PlaylistEntity>
    
    @Query("SELECT * FROM playlists WHERE lastAccessed >= :sinceDate ORDER BY lastAccessed DESC")
    suspend fun getRecentlyAccessedPlaylists(sinceDate: Date): List<PlaylistEntity>
    
    @Query("SELECT COUNT(*) FROM playlists")
    suspend fun getTotalPlaylistCount(): Int
    
    @Query("SELECT COUNT(*) FROM playlists WHERE platform = :platform")
    suspend fun getPlaylistCountByPlatform(platform: String): Int
    
    @Query("SELECT SUM(videoCount) FROM playlists")
    suspend fun getTotalVideoCount(): Int
    
    @Query("SELECT SUM(downloadedCount) FROM playlists")
    suspend fun getTotalDownloadedCount(): Int
    
    @Query("SELECT SUM(totalSize) FROM playlists")
    suspend fun getTotalPlaylistSize(): Long
    
    @Query("SELECT SUM(totalDuration) FROM playlists")
    suspend fun getTotalPlaylistDuration(): Long
    
    @Query("SELECT AVG(downloadProgress) FROM playlists WHERE downloadedCount > 0")
    suspend fun getAverageDownloadProgress(): Float
    
    @Query("SELECT DISTINCT platform FROM playlists ORDER BY platform ASC")
    suspend fun getAllPlatforms(): List<String>
    
    @Query("SELECT DISTINCT author FROM playlists WHERE author IS NOT NULL ORDER BY author ASC")
    suspend fun getAllAuthors(): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylists(playlists: List<PlaylistEntity>): List<Long>
    
    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity): Int
    
    @Update
    suspend fun updatePlaylists(playlists: List<PlaylistEntity>): Int
    
    @Query("UPDATE playlists SET accessCount = accessCount + 1, lastAccessed = :accessTime WHERE url = :url")
    suspend fun incrementAccessCount(url: String, accessTime: Date = Date()): Int
    
    @Query("UPDATE playlists SET isBookmarked = :isBookmarked WHERE url = :url")
    suspend fun updateBookmarkStatus(url: String, isBookmarked: Boolean): Int
    
    // إضافة الدوال المفقودة
    @Query("SELECT * FROM playlists ORDER BY lastAccessed DESC")
    fun getAllPlaylistsFlow(): Flow<List<PlaylistEntity>>
    
    @Query("SELECT COUNT(*) FROM playlists")
    suspend fun getPlaylistCount(): Int
    
    @Query("SELECT COUNT(*) FROM playlists WHERE lastAccessed < :timestamp")
    suspend fun getPlaylistsOlderThanCount(timestamp: Long): Int
    
    @Query("DELETE FROM playlists WHERE lastAccessed < :timestamp")
    suspend fun deletePlaylistsOlderThan(timestamp: Long): Int
    
    @Query("DELETE FROM playlists WHERE url NOT IN (SELECT url FROM playlists ORDER BY lastAccessed DESC LIMIT :maxCount)")
    suspend fun deleteExcessPlaylists(maxCount: Int): Int
    
    @Query("UPDATE playlists SET lastAccessed = :timestamp WHERE playlistId = :playlistId")
    suspend fun updateLastAccessed(playlistId: String, timestamp: Date): Int
    
    @Query("UPDATE playlists SET downloadedCount = :downloaded, failedCount = :failed, pendingCount = :pending, downloadProgress = :progress, updatedAt = :updatedAt WHERE url = :url")
    suspend fun updateDownloadStats(
        url: String, 
        downloaded: Int, 
        failed: Int, 
        pending: Int, 
        progress: Float, 
        updatedAt: Date = Date()
    ): Int
    
    @Query("UPDATE playlists SET downloadStartedAt = :startedAt, updatedAt = :updatedAt WHERE url = :url")
    suspend fun markDownloadStarted(url: String, startedAt: Date = Date(), updatedAt: Date = Date()): Int
    
    @Query("UPDATE playlists SET downloadCompletedAt = :completedAt, downloadProgress = 1.0, updatedAt = :updatedAt WHERE url = :url")
    suspend fun markDownloadCompleted(url: String, completedAt: Date = Date(), updatedAt: Date = Date()): Int
    
    @Query("UPDATE playlists SET selectedQuality = :quality, selectedFormat = :format, updatedAt = :updatedAt WHERE url = :url")
    suspend fun updateDownloadPreferences(url: String, quality: String?, format: String?, updatedAt: Date = Date()): Int
    
    @Query("UPDATE playlists SET downloadPath = :path, updatedAt = :updatedAt WHERE url = :url")
    suspend fun updateDownloadPath(url: String, path: String, updatedAt: Date = Date()): Int
    
    @Query("UPDATE playlists SET priority = :priority, updatedAt = :updatedAt WHERE url = :url")
    suspend fun updatePriority(url: String, priority: Int, updatedAt: Date = Date()): Int
    
    @Query("UPDATE playlists SET downloadAllSelected = :downloadAll, updatedAt = :updatedAt WHERE url = :url")
    suspend fun updateDownloadAllSelected(url: String, downloadAll: Boolean, updatedAt: Date = Date()): Int
    
    @Delete
    suspend fun deletePlaylist(playlist: PlaylistEntity): Int
    
    @Query("DELETE FROM playlists WHERE url = :url")
    suspend fun deletePlaylistByUrl(url: String): Int
    
    @Query("DELETE FROM playlists WHERE platform = :platform")
    suspend fun deletePlaylistsByPlatform(platform: String): Int
    
    @Query("DELETE FROM playlists WHERE downloadedCount = 0 AND createdAt < :beforeDate")
    suspend fun deleteUnusedOldPlaylists(beforeDate: Date): Int
    
    @Query("DELETE FROM playlists WHERE downloadedCount = videoCount AND downloadCompletedAt < :beforeDate")
    suspend fun deleteOldCompletedPlaylists(beforeDate: Date): Int
    
    @Query("DELETE FROM playlists WHERE isBookmarked = 0 AND lastAccessed < :beforeDate")
    suspend fun deleteUnusedPlaylists(beforeDate: Date): Int
    
    @Query("DELETE FROM playlists")
    suspend fun deleteAllPlaylists(): Int
    
    // Complex operations
    @Transaction
    suspend fun upsertPlaylist(playlist: PlaylistEntity): Long {
        val existing = getPlaylistByUrl(playlist.url)
        return if (existing != null) {
            val updated = playlist.copy(
                accessCount = existing.accessCount + 1,
                lastAccessed = Date(),
                createdAt = existing.createdAt
            )
            updatePlaylist(updated)
            0L
        } else {
            insertPlaylist(playlist)
        }
    }
    
    // Cleanup operations
    @Query("DELETE FROM playlists WHERE url IN (SELECT url FROM playlists WHERE isBookmarked = 0 ORDER BY lastAccessed ASC LIMIT :count)")
    suspend fun deleteLeastRecentlyUsed(count: Int): Int
    
    @Transaction
    suspend fun cleanupOldPlaylists(maxEntries: Int = 500, maxAge: Long = 30 * 24 * 60 * 60 * 1000L) {
        val cutoffDate = Date(System.currentTimeMillis() - maxAge)
        
        // Delete old unused playlists
        deleteUnusedPlaylists(cutoffDate)
        
        // Delete old completed playlists (keep bookmarked)
        deleteOldCompletedPlaylists(Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000L))
        
        // If still over limit, delete least recently used (except bookmarked)
        val totalCount = getTotalPlaylistCount()
        if (totalCount > maxEntries) {
            deleteLeastRecentlyUsed(totalCount - maxEntries)
        }
    }
    
    // Statistics and analytics
    @Query("""
        SELECT 
            COUNT(*) as total,
            COUNT(DISTINCT platform) as platforms,
            COUNT(DISTINCT author) as authors,
            SUM(videoCount) as totalVideos,
            SUM(downloadedCount) as totalDownloaded,
            SUM(failedCount) as totalFailed,
            AVG(downloadProgress) as avgProgress,
            SUM(totalSize) as totalSize,
            SUM(totalDuration) as totalDuration,
            COUNT(CASE WHEN isBookmarked = 1 THEN 1 END) as bookmarked
        FROM playlists
    """)
    suspend fun getPlaylistStatistics(): PlaylistStatistics
    
    @Query("""
        SELECT platform, 
               COUNT(*) as count, 
               SUM(videoCount) as totalVideos,
               SUM(downloadedCount) as totalDownloaded,
               AVG(downloadProgress) as avgProgress,
               SUM(totalSize) as totalSize
        FROM playlists 
        GROUP BY platform 
        ORDER BY count DESC
    """)
    suspend fun getPlatformStatistics(): List<PlatformStatistics>
    
    @Query("""
        SELECT author, 
               COUNT(*) as playlistCount, 
               SUM(videoCount) as totalVideos,
               SUM(downloadedCount) as totalDownloaded,
               AVG(downloadProgress) as avgProgress
        FROM playlists 
        WHERE author IS NOT NULL
        GROUP BY author 
        ORDER BY playlistCount DESC
        LIMIT :limit
    """)
    suspend fun getAuthorStatistics(limit: Int = 20): List<AuthorStatistics>
    
    data class PlaylistStatistics(
        val total: Int,
        val platforms: Int,
        val authors: Int,
        val totalVideos: Int,
        val totalDownloaded: Int,
        val totalFailed: Int,
        val avgProgress: Float?,
        val totalSize: Long,
        val totalDuration: Long,
        val bookmarked: Int
    )
    
    data class PlatformStatistics(
        val platform: String,
        val count: Int,
        val totalVideos: Int,
        val totalDownloaded: Int,
        val avgProgress: Float?,
        val totalSize: Long
    )
    
    data class AuthorStatistics(
        val author: String,
        val playlistCount: Int,
        val totalVideos: Int,
        val totalDownloaded: Int,
        val avgProgress: Float?
    )
    
    @Query("SELECT COUNT(*) FROM playlists WHERE playlistId = :playlistId")
    suspend fun getVideoCountForPlaylist(playlistId: String): Int
    
    @Query("SELECT totalDuration FROM playlists WHERE playlistId = :playlistId LIMIT 1")
    suspend fun getTotalDurationForPlaylist(playlistId: String): Long?
    
    @Query("UPDATE playlists SET videoCount = :videoCount, totalDuration = :totalDuration WHERE playlistId = :playlistId")
    suspend fun updatePlaylistStats(playlistId: String, videoCount: Int, totalDuration: Long): Int
    
    @Query("DELETE FROM playlists WHERE lastAccessed < :cutoffDate")
    suspend fun cleanupOldPlaylists(cutoffDate: Long = System.currentTimeMillis() - (90 * 24 * 60 * 60 * 1000L)): Int
    
    @Query("SELECT SUM(videoCount) FROM playlists")
    suspend fun getSumOfVideoCount(): Int
    
    @Query("SELECT SUM(downloadedCount) FROM playlists") 
    suspend fun getSumOfDownloadedCount(): Int
    
    @Query("SELECT COUNT(*) FROM playlists WHERE isBookmarked = 1")
    suspend fun getBookmarkedCount(): Int
    
    // Fixed version that doesn't conflict
    suspend fun getDetailedStatistics(): PlaylistStatistics {
        return PlaylistStatistics(
            total = getTotalPlaylistCount(),
            platforms = 0, // Placeholder implementation
            authors = 0, // Placeholder implementation
            totalVideos = getSumOfVideoCount(),
            totalDownloaded = getSumOfDownloadedCount(),
            totalFailed = 0, // Placeholder implementation
            avgProgress = null,
            totalSize = 0L,
            totalDuration = 0L,
            bookmarked = getBookmarkedCount()
        )
    }
}
