package com.example.snaptube.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.snaptube.database.SnaptubeDatabase
import com.example.snaptube.database.dao.DownloadDao
import com.example.snaptube.database.dao.PlaylistDao
import com.example.snaptube.database.dao.VideoInfoDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.util.concurrent.Executors
import kotlinx.coroutines.flow.first
import java.util.Date

/**
 * وحدة حقن التبعيات لقاعدة البيانات
 * توفر جميع مكونات قاعدة البيانات والـ DAOs
 */
val databaseModule = module {
    
    // قاعدة البيانات الرئيسية
    single<SnaptubeDatabase> {
        Room.databaseBuilder(
            androidContext(),
            SnaptubeDatabase::class.java,
            "snaptube_database"
        )
        .setQueryCallback(
            { sqlQuery, bindArgs ->
                // تسجيل الاستعلامات في وضع التطوير
                if (android.util.Log.isLoggable("Database", android.util.Log.DEBUG)) {
                    android.util.Log.d("Database", "SQL Query: $sqlQuery Args: $bindArgs")
                }
            },
            Executors.newSingleThreadExecutor()
        )
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // إعداد فهارس إضافية لتحسين الأداء
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_status_created_at ON downloads(status, createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_downloads_platform_created_at ON downloads(platform, createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_video_info_cache_expires_at ON video_info_cache(cacheExpiryDate)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_playlists_platform_created_at ON playlists(platform, createdAt)")
            }
            
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Removed PRAGMA statements to prevent execSQL query errors
            }
        })
        .fallbackToDestructiveMigration() // في النسخة التطويرية فقط
        .build()
    }
    
    // DAO للتحميلات
    single<DownloadDao> {
        get<SnaptubeDatabase>().downloadDao()
    }
    
    // DAO لمعلومات الفيديو
    single<VideoInfoDao> {
        get<SnaptubeDatabase>().videoInfoDao()
    }
    
    // DAO لقوائم التشغيل
    single<PlaylistDao> {
        get<SnaptubeDatabase>().playlistDao()
    }
    
    // خدمة صيانة قاعدة البيانات
    single<DatabaseMaintenanceService> {
        DatabaseMaintenanceService(
            database = get(),
            downloadDao = get(),
            videoInfoDao = get(),
            playlistDao = get()
        )
    }
}

/**
 * خدمة صيانة قاعدة البيانات
 * تقوم بمهام التنظيف والصيانة الدورية
 */
class DatabaseMaintenanceService(
    private val database: SnaptubeDatabase,
    private val downloadDao: DownloadDao,
    private val videoInfoDao: VideoInfoDao,
    private val playlistDao: PlaylistDao
) {
    
    /**
     * تنظيف البيانات القديمة والمؤقتة
     */
    suspend fun performMaintenance() {
        try {
            // حذف التحميلات الفاشلة القديمة (أكثر من شهر)
            val oneMonthAgo = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L)
            downloadDao.deleteFailedDownloadsOlderThan(oneMonthAgo)
            
            // حذف cache منتهي الصلاحية
            videoInfoDao.deleteExpiredCache(Date())
            
            // تحديث إحصائيات قوائم التشغيل - simplified
            // يمكن إضافة mantinance مخصص لاحقاً
            
            // تحسين قاعدة البيانات
            database.openHelper.writableDatabase.execSQL("PRAGMA optimize")
            database.openHelper.writableDatabase.execSQL("PRAGMA wal_checkpoint(TRUNCATE)")
            
        } catch (e: Exception) {
            android.util.Log.e("DatabaseMaintenance", "Error during maintenance", e)
        }
    }
    
    /**
     * حساب حجم قاعدة البيانات
     */
    fun getDatabaseSize(): Long {
        return try {
            val dbFile = database.openHelper.readableDatabase.path?.let { 
                java.io.File(it) 
            }
            dbFile?.length() ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
    
    /**
     * إحصائيات قاعدة البيانات
     */
    suspend fun getDatabaseStats(): DatabaseStats {
        return try {
            DatabaseStats(
                downloadsCount = downloadDao.getDownloadCount(),
                activeDownloadsCount = downloadDao.getActiveDownloadCount(),
                cachedVideosCount = videoInfoDao.getTotalVideoCount(),
                playlistsCount = playlistDao.getTotalPlaylistCount(),
                databaseSize = getDatabaseSize()
            )
        } catch (e: Exception) {
            DatabaseStats()
        }
    }
    
    /**
     * تصدير البيانات للنسخ الاحتياطي
     */
    suspend fun exportData(): DatabaseExport {
        return try {
            DatabaseExport(
                downloads = downloadDao.getAllDownloads().first(),
                playlists = playlistDao.getAllPlaylists().first(),
                exportTimestamp = System.currentTimeMillis()
            )
        } catch (e: Exception) {
            DatabaseExport(
                downloads = emptyList(),
                playlists = emptyList(),
                exportTimestamp = System.currentTimeMillis()
            )
        }
    }
}

/**
 * إحصائيات قاعدة البيانات
 */
data class DatabaseStats(
    val downloadsCount: Int = 0,
    val activeDownloadsCount: Int = 0,
    val cachedVideosCount: Int = 0,
    val playlistsCount: Int = 0,
    val databaseSize: Long = 0L
)

/**
 * بيانات التصدير
 */
data class DatabaseExport(
    val downloads: List<com.example.snaptube.database.entities.DownloadEntity>,
    val playlists: List<com.example.snaptube.database.entities.PlaylistEntity>,
    val exportTimestamp: Long
)
