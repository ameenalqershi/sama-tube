package com.example.snaptube.repository

import com.example.snaptube.database.SnaptubeDatabase
import com.example.snaptube.database.entities.VideoInfoEntity
import com.example.snaptube.database.entities.PlaylistEntity
import com.example.snaptube.download.VideoInfoExtractor
import com.example.snaptube.models.VideoInfo
import com.example.snaptube.models.PlaylistInfo
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.utils.NetworkUtils
import com.example.snaptube.utils.LogUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.util.Date
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import com.example.snaptube.database.entities.toVideoInfo
import com.example.snaptube.database.entities.toVideoInfoEntity
import com.example.snaptube.database.entities.toPlaylistInfo
import com.example.snaptube.database.entities.toPlaylistEntity

/**
 * Repository for managing video information extraction and caching
 * Provides efficient video info retrieval with intelligent caching strategy
 */
class VideoInfoRepository(
    private val database: SnaptubeDatabase,
    private val videoInfoExtractor: VideoInfoExtractor,
    private val networkUtils: NetworkUtils,
    private val logUtils: LogUtils
) {

    private val videoInfoDao = database.videoInfoDao()
    private val playlistDao = database.playlistDao()

    companion object {
        private const val CACHE_VALIDITY_HOURS = 24
        private const val PLAYLIST_CACHE_VALIDITY_HOURS = 6
        private const val MAX_CACHE_ENTRIES = 1000
        private const val MAX_PLAYLIST_CACHE_ENTRIES = 100
    }

    /**
     * Get video information with intelligent caching
     */
    suspend fun getVideoInfo(url: String, forceRefresh: Boolean = false): Result<VideoInfo> {
        return try {
            logUtils.info("VideoInfoRepository", "Getting video info for: $url")
            
            // Check cache first if not forcing refresh
            if (!forceRefresh) {
                val cachedInfo = getCachedVideoInfo(url)
                if (cachedInfo != null) {
                    logUtils.debug("VideoInfoRepository", "Returning cached video info for: $url")
                    return Result.success(cachedInfo.toVideoInfo())
                }
            }
            
            // Check network connectivity
            if (!networkUtils.isNetworkAvailable()) {
                val cachedInfo = videoInfoDao.getVideoInfoByUrl(url)
                if (cachedInfo != null) {
                    logUtils.warn("VideoInfoRepository", "No network, returning stale cache for: $url")
                    return Result.success(cachedInfo.toVideoInfo())
                } else {
                    return Result.failure(Exception("No network connection and no cached data available"))
                }
            }
            
            // Extract fresh video info
            val startTime = System.currentTimeMillis()
            val videoInfo = videoInfoExtractor.extractVideoInfo(url)
            val duration = System.currentTimeMillis() - startTime
            
            // Cache the result
            cacheVideoInfo(videoInfo)
            
            logUtils.logVideoExtraction(url, duration, true)
            Result.success(videoInfo)
            
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to get video info for: $url", e)
            Timber.e(e, "Failed to get video info for: $url")
            
            // Try to return cached info as fallback
            val cachedInfo = videoInfoDao.getVideoInfoByUrl(url)
            if (cachedInfo != null) {
                logUtils.warn("VideoInfoRepository", "Returning stale cache due to error for: $url")
                Result.success(cachedInfo.toVideoInfo())
            } else {
                Result.failure(e)
            }
        }
    }

    /**
     * Get playlist information with caching
     */
    suspend fun getPlaylistInfo(url: String, forceRefresh: Boolean = false): Result<PlaylistInfo> {
        return try {
            logUtils.info("VideoInfoRepository", "Getting playlist info for: $url")
            
            // Check cache first if not forcing refresh
            if (!forceRefresh) {
                val cachedPlaylist = getCachedPlaylistInfo(url)
                if (cachedPlaylist != null) {
                    logUtils.debug("VideoInfoRepository", "Returning cached playlist info for: $url")
                    return Result.success(cachedPlaylist.toPlaylistInfo())
                }
            }
            
            // Check network connectivity
            if (!networkUtils.isNetworkAvailable()) {
                val cachedPlaylist = playlistDao.getPlaylistByUrl(url)
                if (cachedPlaylist != null) {
                    logUtils.warn("VideoInfoRepository", "No network, returning stale playlist cache for: $url")
                    return Result.success(cachedPlaylist.toPlaylistInfo())
                } else {
                    return Result.failure(Exception("No network connection and no cached playlist data available"))
                }
            }
            
            // Extract fresh playlist info
            val startTime = System.currentTimeMillis()
            val playlistInfo = videoInfoExtractor.extractPlaylistInfo(url)
            val duration = System.currentTimeMillis() - startTime
            
            // Cache the result
            cachePlaylistInfo(playlistInfo)
            
            logUtils.info("VideoInfoRepository", "Playlist extracted successfully in ${duration}ms: ${playlistInfo.title}")
            Result.success(playlistInfo)
            
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to get playlist info for: $url", e)
            Timber.e(e, "Failed to get playlist info for: $url")
            
            // Try to return cached info as fallback
            val cachedPlaylist = playlistDao.getPlaylistByUrl(url)
            if (cachedPlaylist != null) {
                logUtils.warn("VideoInfoRepository", "Returning stale playlist cache due to error for: $url")
                Result.success(cachedPlaylist.toPlaylistInfo())
            } else {
                Result.failure(e)
            }
        }
    }

    /**
     * Get available formats for a video
     */
    suspend fun getAvailableFormats(url: String): Result<List<DownloadFormat>> {
        return try {
            logUtils.debug("VideoInfoRepository", "Getting available formats for: $url")
            
            // Try to get from cached video info first
            val cachedInfo = getCachedVideoInfo(url)
            if (cachedInfo != null && cachedInfo.formats.isNotEmpty()) {
                val formats = cachedInfo.formats.mapNotNull { formatJson ->
                    try {
                        Gson().fromJson(formatJson, DownloadFormat::class.java)
                    } catch (e: Exception) {
                        logUtils.error("VideoInfoRepository", "Failed to parse format JSON: $formatJson", e)
                        null
                    }
                }
                
                if (formats.isNotEmpty()) {
                    logUtils.debug("VideoInfoRepository", "Returning ${formats.size} cached formats")
                    return Result.success(formats)
                }
            }
            
            // Extract fresh video info to get formats
            val videoInfoResult = getVideoInfo(url)
            if (videoInfoResult.isSuccess) {
                val formats = videoInfoResult.getOrNull()?.formats ?: emptyList()
                logUtils.debug("VideoInfoRepository", "Extracted ${formats.size} formats")
                Result.success(formats)
            } else {
                Result.failure(videoInfoResult.exceptionOrNull() ?: Exception("Failed to get formats"))
            }
            
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to get formats for: $url", e)
            Result.failure(e)
        }
    }

    /**
     * Search video history
     */
    fun searchVideoHistory(query: String): Flow<List<VideoInfoEntity>> {
        return videoInfoDao.searchVideoInfo("%$query%")
    }

    /**
     * Get recent video info entries
     */
    fun getRecentVideoInfo(limit: Int = 50): Flow<List<VideoInfoEntity>> = flow {
        emit(videoInfoDao.getRecentVideoInfo(limit))
    }

    /**
     * Get frequently accessed video info
     */
    fun getFrequentVideoInfo(limit: Int = 20): Flow<List<VideoInfoEntity>> = flow {
        emit(videoInfoDao.getFrequentVideoInfo(limit))
    }

    /**
     * Get cached playlists
     */
    fun getCachedPlaylists(): Flow<List<PlaylistEntity>> {
        return playlistDao.getAllPlaylistsFlow()
    }

    /**
     * Get playlist by URL
     */
    suspend fun getCachedPlaylistByUrl(url: String): PlaylistEntity? {
        return playlistDao.getPlaylistByUrl(url)
    }

    /**
     * Delete video info from cache
     */
    suspend fun deleteVideoInfoFromCache(videoId: String): Result<Unit> {
        return try {
            val entity = videoInfoDao.getVideoInfoById(videoId)
            if (entity != null) {
                videoInfoDao.deleteVideoInfo(entity)
                logUtils.debug("VideoInfoRepository", "Deleted video info from cache: $videoId")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to delete video info from cache: $videoId", e)
            Result.failure(e)
        }
    }

    /**
     * Delete playlist from cache
     */
    suspend fun deletePlaylistFromCache(playlistId: String): Result<Unit> {
        return try {
            val entity = playlistDao.getPlaylistById(playlistId)
            if (entity != null) {
                playlistDao.deletePlaylist(entity)
                logUtils.debug("VideoInfoRepository", "Deleted playlist from cache: $playlistId")
            }
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to delete playlist from cache: $playlistId", e)
            Result.failure(e)
        }
    }

    /**
     * Clear video info cache
     */
    suspend fun clearVideoInfoCache(): Result<Unit> {
        return try {
            val deletedCount = videoInfoDao.deleteAllVideoInfo()
            logUtils.info("VideoInfoRepository", "Cleared video info cache: $deletedCount entries")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to clear video info cache", e)
            Result.failure(e)
        }
    }

    /**
     * Clear playlist cache
     */
    suspend fun clearPlaylistCache(): Result<Unit> {
        return try {
            val deletedCount = playlistDao.deleteAllPlaylists()
            logUtils.info("VideoInfoRepository", "Cleared playlist cache: $deletedCount entries")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to clear playlist cache", e)
            Result.failure(e)
        }
    }

    /**
     * Clean up old cache entries
     */
    suspend fun cleanupOldCache(): Result<CacheCleanupResult> {
        return try {
            val videoInfoCutoff = Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(CACHE_VALIDITY_HOURS.toLong()))
            val playlistCutoff = Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(PLAYLIST_CACHE_VALIDITY_HOURS.toLong()))
            
            val deletedVideoInfo = videoInfoDao.deleteVideoInfoOlderThan(videoInfoCutoff.time)
            val deletedPlaylists = playlistDao.deletePlaylistsOlderThan(playlistCutoff.time)
            
            // Also clean up excess entries to maintain cache size limits
            val excessVideoInfo = videoInfoDao.deleteExcessVideoInfo(MAX_CACHE_ENTRIES)
            val excessPlaylists = playlistDao.deleteExcessPlaylists(MAX_PLAYLIST_CACHE_ENTRIES)
            
            val result = CacheCleanupResult(
                deletedVideoInfoCount = deletedVideoInfo + excessVideoInfo,
                deletedPlaylistsCount = deletedPlaylists + excessPlaylists
            )
            
            logUtils.info("VideoInfoRepository", "Cache cleanup completed: ${result.deletedVideoInfoCount} video info, ${result.deletedPlaylistsCount} playlists")
            Result.success(result)
            
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to cleanup old cache", e)
            Result.failure(e)
        }
    }

    /**
     * Get cache statistics
     */
    suspend fun getCacheStatistics(): CacheStatistics {
        val videoInfoCount = videoInfoDao.getVideoInfoCount()
        val playlistCount = playlistDao.getPlaylistCount()
        val videoInfoSize = videoInfoDao.getTotalVideoInfoSize()
        val playlistSize = playlistDao.getTotalPlaylistSize()
        val cutoffVideoInfo = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(CACHE_VALIDITY_HOURS.toLong())
        val cutoffPlaylist = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(PLAYLIST_CACHE_VALIDITY_HOURS.toLong())
        val oldVideoInfoCount = videoInfoDao.getVideoInfoOlderThanCount(cutoffVideoInfo)
        val oldPlaylistCount = playlistDao.getPlaylistsOlderThanCount(cutoffPlaylist)
        
        return CacheStatistics(
            videoInfoCount = videoInfoCount,
            playlistCount = playlistCount,
            totalSize = videoInfoSize + playlistSize,
            oldVideoInfoCount = oldVideoInfoCount,
            oldPlaylistCount = oldPlaylistCount
        )
    }

    /**
     * Validate video URL
     */
    suspend fun validateVideoUrl(url: String): Result<Boolean> {
        return try {
            val isValid = videoInfoExtractor.isUrlSupported(url)
            logUtils.debug("VideoInfoRepository", "URL validation for $url: $isValid")
            Result.success(isValid)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to validate URL: $url", e)
            Result.failure(e)
        }
    }

    /**
     * Get supported platforms
     */
    suspend fun getSupportedPlatforms(): List<String> {
        return videoInfoDao.getAllPlatforms()
    }

    /**
     * Check if URL is playlist
     */
    suspend fun isPlaylistUrl(url: String): Result<Boolean> {
        return try {
            val isPlaylist = url.contains("list=", ignoreCase = true)
            logUtils.debug("VideoInfoRepository", "Playlist check for $url: $isPlaylist")
            Result.success(isPlaylist)
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to check if URL is playlist: $url", e)
            Result.failure(e)
        }
    }

    /**
     * Get cached video info if valid
     */
    private suspend fun getCachedVideoInfo(url: String): VideoInfoEntity? {
        val cached = videoInfoDao.getVideoInfoByUrl(url) ?: return null
        
        // Check if cache is still valid
        val cacheAge = System.currentTimeMillis() - cached.lastAccessed.time
        val cacheValidityMs = TimeUnit.HOURS.toMillis(CACHE_VALIDITY_HOURS.toLong())
        
        return if (cacheAge < cacheValidityMs) {
            // Update access time
            videoInfoDao.updateLastAccessed(cached.videoId, Date())
            cached
        } else {
            null
        }
    }

    /**
     * Get cached playlist info if valid
     */
    private suspend fun getCachedPlaylistInfo(url: String): PlaylistEntity? {
        val cached = playlistDao.getPlaylistByUrl(url) ?: return null
        
        // Check if cache is still valid
        val cacheAge = System.currentTimeMillis() - cached.lastAccessed.time
        val cacheValidityMs = TimeUnit.HOURS.toMillis(PLAYLIST_CACHE_VALIDITY_HOURS.toLong())
        
        return if (cacheAge < cacheValidityMs) {
            // Update access time
            playlistDao.updateLastAccessed(cached.playlistId, Date())
            cached
        } else {
            null
        }
    }

    /**
     * Cache video info
     */
    private suspend fun cacheVideoInfo(videoInfo: VideoInfo) {
        try {
            // تحويل VideoInfo إلى VideoInfoEntity باستخدام الامتداد
            val entity = videoInfo.toVideoInfoEntity()
            videoInfoDao.insertVideoInfo(entity)
            logUtils.debug("VideoInfoRepository", "Cached video info: ${videoInfo.title}")
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to cache video info", e)
        }
    }

    /**
     * Cache playlist info
     */
    private suspend fun cachePlaylistInfo(playlistInfo: PlaylistInfo) {
        try {
            // تحويل PlaylistInfo إلى PlaylistEntity باستخدام الامتداد
            val entity = playlistInfo.toPlaylistEntity()
            playlistDao.insertPlaylist(entity)
            
            // Also cache individual video info from playlist
            playlistInfo.videos.forEach { videoInfo ->
                cacheVideoInfo(videoInfo)
            }
            
            logUtils.debug("VideoInfoRepository", "Cached playlist info: ${playlistInfo.title}")
        } catch (e: Exception) {
            logUtils.error("VideoInfoRepository", "Failed to cache playlist info", e)
        }
    }

    /**
     * Data classes for repository responses
     */
    data class CacheCleanupResult(
        val deletedVideoInfoCount: Int,
        val deletedPlaylistsCount: Int
    )

    data class CacheStatistics(
        val videoInfoCount: Int,
        val playlistCount: Int,
        val totalSize: Long,
        val oldVideoInfoCount: Int,
        val oldPlaylistCount: Int
    )
}
