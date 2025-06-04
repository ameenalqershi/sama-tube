package com.example.snaptube.database.dao

import androidx.room.*
import com.example.snaptube.database.entities.VideoInfoEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface VideoInfoDao {
    
    @Query("SELECT * FROM video_info_cache WHERE url = :url AND isValid = 1")
    suspend fun getVideoInfoByUrl(url: String): VideoInfoEntity?
    
    @Query("SELECT * FROM video_info_cache WHERE videoId = :videoId AND isValid = 1 LIMIT 1")
    suspend fun getVideoInfoById(videoId: String): VideoInfoEntity?
    
    @Query("SELECT * FROM video_info_cache WHERE platform = :platform AND isValid = 1 ORDER BY lastAccessed DESC")
    fun getVideoInfoByPlatform(platform: String): Flow<List<VideoInfoEntity>>
    
    @Query("SELECT * FROM video_info_cache WHERE isValid = 1 ORDER BY lastAccessed DESC LIMIT :limit")
    suspend fun getRecentVideoInfo(limit: Int = 50): List<VideoInfoEntity>
    
    @Query("SELECT * FROM video_info_cache WHERE accessCount >= :minCount AND isValid = 1 ORDER BY accessCount DESC LIMIT :limit")
    suspend fun getPopularVideoInfo(minCount: Int = 2, limit: Int = 20): List<VideoInfoEntity>
    
    @Query("SELECT * FROM video_info_cache WHERE title LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%' AND isValid = 1 ORDER BY accessCount DESC")
    fun searchVideoInfo(query: String): Flow<List<VideoInfoEntity>>
    
    @Query("SELECT * FROM video_info_cache WHERE author = :author AND isValid = 1 ORDER BY lastAccessed DESC")
    fun getVideoInfoByAuthor(author: String): Flow<List<VideoInfoEntity>>
    
    @Query("SELECT * FROM video_info_cache WHERE duration BETWEEN :minDuration AND :maxDuration AND isValid = 1 ORDER BY lastAccessed DESC")
    fun getVideoInfoByDurationRange(minDuration: Long, maxDuration: Long): Flow<List<VideoInfoEntity>>
    
    @Query("SELECT * FROM video_info_cache WHERE updatedAt < :beforeDate AND isValid = 1")
    suspend fun getExpiredVideoInfo(beforeDate: Date): List<VideoInfoEntity>
    
    @Query("SELECT * FROM video_info_cache WHERE cacheExpiryDate < :currentDate AND isValid = 1")
    suspend fun getExpiredCache(currentDate: Date = Date()): List<VideoInfoEntity>
    
    @Query("SELECT COUNT(*) FROM video_info_cache WHERE platform = :platform AND isValid = 1")
    suspend fun getVideoCountByPlatform(platform: String): Int
    
    @Query("SELECT COUNT(*) FROM video_info_cache WHERE isValid = 1")
    suspend fun getTotalVideoCount(): Int
    
    suspend fun getCacheStatistics(): CacheStatistics {
        return CacheStatistics(
            total = getTotalVideoCount(),
            platforms = getUniquePlatformCount(),
            authors = 0, // Will be implemented later
            avgDuration = 0.0,
            totalSize = getTotalCacheSize(),
            maxAccess = 0,
            avgAccess = 0.0
        )
    }
    
    @Query("SELECT COUNT(DISTINCT platform) FROM video_info_cache")
    suspend fun getUniquePlatformCount(): Int
    
    @Query("SELECT SUM(fileSize) FROM video_info_cache WHERE isValid = 1")
    suspend fun getTotalCacheSize(): Long
    
    @Query("SELECT DISTINCT platform FROM video_info_cache WHERE isValid = 1 ORDER BY platform ASC")
    suspend fun getAllPlatforms(): List<String>
    
    @Query("SELECT DISTINCT author FROM video_info_cache WHERE author IS NOT NULL AND isValid = 1 ORDER BY author ASC")
    suspend fun getAllAuthors(): List<String>
    
    @Query("SELECT DISTINCT language FROM video_info_cache WHERE language IS NOT NULL AND isValid = 1 ORDER BY language ASC")
    suspend fun getAllLanguages(): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoInfo(videoInfo: VideoInfoEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoInfoList(videoInfoList: List<VideoInfoEntity>): List<Long>
    
    @Update
    suspend fun updateVideoInfo(videoInfo: VideoInfoEntity): Int
    
    @Query("UPDATE video_info_cache SET accessCount = accessCount + 1, lastAccessed = :accessTime WHERE url = :url")
    suspend fun incrementAccessCount(url: String, accessTime: Date = Date()): Int
    
    @Query("UPDATE video_info_cache SET isValid = :isValid WHERE url = :url")
    suspend fun updateValidityStatus(url: String, isValid: Boolean): Int
    
    @Query("UPDATE video_info_cache SET cacheExpiryDate = :expiryDate WHERE url = :url")
    suspend fun updateCacheExpiry(url: String, expiryDate: Date): Int
    
    @Delete
    suspend fun deleteVideoInfo(videoInfo: VideoInfoEntity): Int
    
    @Query("DELETE FROM video_info_cache WHERE url = :url")
    suspend fun deleteVideoInfoByUrl(url: String): Int
    
    @Query("DELETE FROM video_info_cache WHERE platform = :platform")
    suspend fun deleteVideoInfoByPlatform(platform: String): Int
    
    @Query("DELETE FROM video_info_cache WHERE isValid = 0")
    suspend fun deleteInvalidVideoInfo(): Int
    
    @Query("DELETE FROM video_info_cache WHERE updatedAt < :beforeDate")
    suspend fun deleteOldVideoInfo(beforeDate: Date): Int
    
    @Query("SELECT * FROM video_info_cache WHERE accessCount >= :minCount AND isValid = 1 ORDER BY accessCount DESC LIMIT :limit")
    suspend fun getFrequentVideoInfo(minCount: Int = 2, limit: Int = 20): List<VideoInfoEntity>
    
    @Query("SELECT COUNT(*) FROM video_info_cache WHERE isValid = 1")
    suspend fun getVideoInfoCount(): Int
    
    @Query("SELECT SUM(fileSize) FROM video_info_cache WHERE isValid = 1")
    suspend fun getTotalVideoInfoSize(): Long
    
    @Query("SELECT COUNT(*) FROM video_info_cache WHERE updatedAt < :timestamp AND isValid = 1")
    suspend fun getVideoInfoOlderThanCount(timestamp: Long): Int
    
    @Query("DELETE FROM video_info_cache WHERE updatedAt < :timestamp")
    suspend fun deleteVideoInfoOlderThan(timestamp: Long): Int
    
    @Query("DELETE FROM video_info_cache WHERE url NOT IN (SELECT url FROM video_info_cache ORDER BY lastAccessed DESC LIMIT :maxCount)")
    suspend fun deleteExcessVideoInfo(maxCount: Int): Int
    
    @Query("UPDATE video_info_cache SET lastAccessed = :timestamp WHERE videoId = :videoId")
    suspend fun updateLastAccessed(videoId: String, timestamp: Date): Int
    
    @Query("DELETE FROM video_info_cache WHERE createdAt < :expiryDate")
    suspend fun deleteExpiredCache(expiryDate: Date): Int
    
    @Query("DELETE FROM video_info_cache")
    suspend fun deleteAllVideoInfo(): Int
    
    // Cache management
    @Query("SELECT COUNT(*) FROM video_info_cache")
    suspend fun getCacheEntryCount(): Int
    
    @Query("DELETE FROM video_info_cache WHERE url IN (SELECT url FROM video_info_cache ORDER BY lastAccessed ASC LIMIT :count)")
    suspend fun deleteLeastRecentlyUsed(count: Int): Int
    
    @Query("UPDATE video_info_cache SET isValid = 0 WHERE updatedAt < :staleDate")
    suspend fun markStaleEntriesInvalid(staleDate: Date): Int
    
    // Cleanup operations
    @Transaction
    suspend fun cleanupCache(maxEntries: Int = 1000, maxAge: Long = 7 * 24 * 60 * 60 * 1000L) {
        val currentTime = Date()
        val cutoffDate = Date(currentTime.time - maxAge)
        
        // Delete expired entries
        deleteExpiredCache(currentTime)
        
        // Delete old entries
        deleteOldVideoInfo(cutoffDate)
        
        // If still over limit, delete least recently used
        val entryCount = getCacheEntryCount()
        if (entryCount > maxEntries) {
            deleteLeastRecentlyUsed(entryCount - maxEntries)
        }
    }
    
    // Statistics and analytics
    @Query("""
        SELECT 
            COUNT(*) as total,
            COUNT(DISTINCT platform) as platforms,
            COUNT(DISTINCT author) as authors,
            AVG(duration) as avgDuration,
            SUM(fileSize) as totalSize,
            MAX(accessCount) as maxAccess,
            AVG(accessCount) as avgAccess
        FROM video_info_cache WHERE isValid = 1
    """)
    suspend fun getCacheStatisticsAdvanced(): CacheStatistics
    
    @Query("""
        SELECT platform, COUNT(*) as count, AVG(duration) as avgDuration, SUM(fileSize) as totalSize
        FROM video_info_cache 
        WHERE isValid = 1 
        GROUP BY platform 
        ORDER BY count DESC
    """)
    suspend fun getPlatformStatistics(): List<PlatformStatistics>
    
    data class CacheStatistics(
        val total: Int,
        val platforms: Int,
        val authors: Int,
        val avgDuration: Double?,
        val totalSize: Long,
        val maxAccess: Int,
        val avgAccess: Double?
    )
    
    data class PlatformStatistics(
        val platform: String,
        val count: Int,
        val avgDuration: Double?,
        val totalSize: Long
    )
}
