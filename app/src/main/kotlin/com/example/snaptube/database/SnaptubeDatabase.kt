package com.example.snaptube.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.database.entities.VideoInfoEntity
import com.example.snaptube.database.entities.PlaylistEntity
import com.example.snaptube.database.dao.DownloadDao
import com.example.snaptube.database.dao.VideoInfoDao
import com.example.snaptube.database.dao.PlaylistDao
import com.example.snaptube.database.converters.DateConverter
import com.example.snaptube.database.converters.DownloadStatusConverter
import com.example.snaptube.database.converters.StringListConverter
import android.util.Log
import timber.log.Timber
import com.example.snaptube.download.DownloadStatus

@Database(
    entities = [
        DownloadEntity::class,
        VideoInfoEntity::class,
        PlaylistEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class,
    DownloadStatusConverter::class,
    StringListConverter::class
)
abstract class SnaptubeDatabase : RoomDatabase() {
    
    abstract fun downloadDao(): DownloadDao
    abstract fun videoInfoDao(): VideoInfoDao
    abstract fun playlistDao(): PlaylistDao
    
    companion object {
        private const val DATABASE_NAME = "snaptube_database"
        
        @Volatile
        private var INSTANCE: SnaptubeDatabase? = null
        
        fun getInstance(context: Context): SnaptubeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = createDatabase(context)
                INSTANCE = instance
                instance
            }
        }
        
        private fun createDatabase(context: Context): SnaptubeDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                SnaptubeDatabase::class.java,
                DATABASE_NAME
            )
            .addMigrations(
                // Add future migrations here
                // MIGRATION_1_2,
                // MIGRATION_2_3,
            )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    Timber.i("SnaptubeDatabase created successfully")
                    
                    // Create indices for better performance
                    createIndices(db)
                }
                
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    Timber.d("SnaptubeDatabase opened")
                    
                    // Enable foreign key constraints
                    db.execSQL("PRAGMA foreign_keys = ON")
                    
                    // Set WAL mode for better performance
                    db.execSQL("PRAGMA journal_mode = WAL")
                    
                    // Set synchronous mode to NORMAL for better performance
                    db.execSQL("PRAGMA synchronous = NORMAL")
                    
                    // Set cache size to 10MB
                    db.execSQL("PRAGMA cache_size = -10240")
                    
                    // Set temp store to memory
                    db.execSQL("PRAGMA temp_store = MEMORY")
                }
            })
            .fallbackToDestructiveMigration() // Remove in production
            .build()
        }
        
        private fun createIndices(db: SupportSQLiteDatabase) {
            try {
                // Downloads table indices
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_status ON downloads(status)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_platform ON downloads(platform)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_created_at ON downloads(createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_url ON downloads(url)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_worker_id ON downloads(workerId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_priority ON downloads(priority)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_visible ON downloads(isVisible)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_status_visible ON downloads(status, isVisible)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_author ON downloads(author)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_completed_at ON downloads(completedAt)")
                
                // Video info cache table indices
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_video_id ON video_info_cache(videoId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_platform ON video_info_cache(platform)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_author ON video_info_cache(author)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_last_accessed ON video_info_cache(lastAccessed)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_access_count ON video_info_cache(accessCount)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_updated_at ON video_info_cache(updatedAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_valid ON video_info_cache(isValid)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_expiry ON video_info_cache(cacheExpiryDate)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_duration ON video_info_cache(duration)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_title ON video_info_cache(title)")
                
                // Playlists table indices
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_playlist_id ON playlists(playlistId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_platform ON playlists(platform)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_author ON playlists(author)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_last_accessed ON playlists(lastAccessed)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_access_count ON playlists(accessCount)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_bookmarked ON playlists(isBookmarked)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_download_progress ON playlists(downloadProgress)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_video_count ON playlists(videoCount)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_downloaded_count ON playlists(downloadedCount)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_failed_count ON playlists(failedCount)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_created_at ON playlists(createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_title ON playlists(title)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_priority ON playlists(priority)")
                
                // Composite indices for common queries
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_status_priority_created ON downloads(status, priority, createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_platform_status ON downloads(platform, status)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_platform_valid ON video_info_cache(platform, isValid)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_platform_bookmarked ON playlists(platform, isBookmarked)")
                
                Timber.i("Database indices created successfully")
            } catch (e: Exception) {
                Timber.e(e, "Error creating database indices")
            }
        }
        
        // Future migration examples
        /*
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: Add new column
                database.execSQL("ALTER TABLE downloads ADD COLUMN new_column TEXT")
            }
        }
        
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: Create new table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS new_table (
                        id TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }
        */
    }
    
    // Database utility functions
    suspend fun clearAllData() {
        downloadDao().deleteAllDownloads()
        videoInfoDao().deleteAllVideoInfo()
        playlistDao().deleteAllPlaylists()
        Timber.i("All database data cleared")
    }
    
    suspend fun getDatabaseSize(): Long {
        return try {
            val downloads = downloadDao().getTotalDownloadCount()
            val videos = videoInfoDao().getTotalVideoCount()
            val playlists = playlistDao().getTotalPlaylistCount()
            
            Timber.d("Database size: Downloads=$downloads, Videos=$videos, Playlists=$playlists")
            (downloads + videos + playlists).toLong()
        } catch (e: Exception) {
            Timber.e(e, "Error getting database size")
            0L
        }
    }
    
    suspend fun performMaintenanceTasks() {
        try {
            // Clean up expired cache entries
            videoInfoDao().cleanupCache()
            
            // Clean up old playlists
            playlistDao().cleanupOldPlaylists()
            
            // Clean up stale downloads
            val oneDayAgo = java.util.Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000)
            downloadDao().cleanupStaleDownloads(
                DownloadStatus.DOWNLOADING,
                DownloadStatus.FAILED,
                oneDayAgo
            )
            
            // Clear inactive worker IDs
            downloadDao().clearInactiveWorkerIds(
                listOf(
                    DownloadStatus.DOWNLOADING,
                    DownloadStatus.PROCESSING,
                    DownloadStatus.EXTRACTING
                )
            )
            
            Timber.i("Database maintenance tasks completed successfully")
        } catch (e: Exception) {
            Timber.e(e, "Error performing database maintenance")
        }
    }
    
    suspend fun getDatabaseStatistics(): DatabaseStatistics {
        return try {
            val downloadStats = downloadDao().getDownloadStatistics()
            val cacheStats = videoInfoDao().getCacheStatistics()
            val playlistStats = playlistDao().getPlaylistStatistics()
            
            DatabaseStatistics(
                totalDownloads = downloadStats.totalDownloads,
                completedDownloads = downloadStats.completedDownloads,
                failedDownloads = downloadStats.failedDownloads,
                activeDownloads = downloadStats.activeDownloads,
                totalDownloadBytes = downloadStats.totalSize,
                
                totalVideosInCache = cacheStats.total,
                totalPlatforms = cacheStats.platforms,
                totalCacheSize = cacheStats.totalSize,
                
                totalPlaylists = playlistStats.total,
                totalPlaylistVideos = playlistStats.totalVideos,
                totalPlaylistDownloaded = playlistStats.totalDownloaded,
                bookmarkedPlaylists = playlistStats.bookmarked
            )
        } catch (e: Exception) {
            Timber.e(e, "Error getting database statistics")
            DatabaseStatistics()
        }
    }
    
    data class DatabaseStatistics(
        val totalDownloads: Int = 0,
        val completedDownloads: Int = 0,
        val failedDownloads: Int = 0,
        val activeDownloads: Int = 0,
        val totalDownloadBytes: Long = 0L,
        
        val totalVideosInCache: Int = 0,
        val totalPlatforms: Int = 0,
        val totalCacheSize: Long = 0L,
        
        val totalPlaylists: Int = 0,
        val totalPlaylistVideos: Int = 0,
        val totalPlaylistDownloaded: Int = 0,
        val bookmarkedPlaylists: Int = 0
    ) {
        fun getFormattedTotalSize(): String {
            val totalSize = totalDownloadBytes + totalCacheSize
            return when {
                totalSize > 1024 * 1024 * 1024 -> "${"%.1f".format(totalSize.toDouble() / (1024 * 1024 * 1024))} GB"
                totalSize > 1024 * 1024 -> "${"%.1f".format(totalSize.toDouble() / (1024 * 1024))} MB"
                totalSize > 1024 -> "${"%.1f".format(totalSize.toDouble() / 1024)} KB"
                else -> "$totalSize B"
            }
        }
        
        fun getDownloadSuccessRate(): Float {
            return if (totalDownloads > 0) {
                completedDownloads.toFloat() / totalDownloads
            } else 0f
        }
        
        fun getPlaylistCompletionRate(): Float {
            return if (totalPlaylistVideos > 0) {
                totalPlaylistDownloaded.toFloat() / totalPlaylistVideos
            } else 0f
        }
    }
}
