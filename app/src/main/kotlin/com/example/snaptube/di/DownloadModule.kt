package com.example.snaptube.di

import android.app.NotificationManager
import android.content.Context
import androidx.work.WorkManager
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.ProgressTracker
import com.example.snaptube.download.VideoInfoExtractor
import com.example.snaptube.services.DownloadService
import com.example.snaptube.services.NotificationService
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.LogUtils
import com.example.snaptube.utils.NetworkUtils
import com.example.snaptube.utils.PreferenceUtils
import com.example.snaptube.utils.VideoUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * وحدة حقن التبعيات للتحميل
 * توفر جميع مكونات نظام التحميل والمعالجة
 */
val downloadModule = module {
    
    // مستخرج معلومات الفيديو
    single<VideoInfoExtractor> {
        VideoInfoExtractor(
            context = androidContext()
        )
    }
    
    // متتبع التقدم
    single<ProgressTracker> {
        ProgressTracker()
    }
    
    // مدير التحميل الرئيسي
    single<DownloadManager> {
        DownloadManager(
            context = androidContext(),
            videoInfoExtractor = get(),
            fileUtils = get(),
            networkUtils = get()
        )
    }
    
    // مجمع خيوط التحميل
    single<ThreadPoolExecutor>(named("downloadExecutor")) {
        ThreadPoolExecutor(
            2, // الحد الأدنى للخيوط
            4, // الحد الأقصى للخيوط
            60L, // وقت انتظار الخيط الخالي
            TimeUnit.SECONDS,
            java.util.concurrent.LinkedBlockingQueue<Runnable>(),
            { runnable ->
                Thread(runnable).apply {
                    name = "download-thread-${id}"
                    isDaemon = true
                    priority = Thread.NORM_PRIORITY - 1 // أولوية أقل قليلاً
                }
            }
        ).apply {
            // السماح بإنهاء الخيوط الأساسية عند عدم الاستخدام
            allowCoreThreadTimeOut(true)
        }
    }
    
    // نطاق كوروتين للتحميل
    single<CoroutineScope>(named("downloadScope")) {
        CoroutineScope(
            Dispatchers.IO + SupervisorJob() + 
            kotlinx.coroutines.CoroutineName("DownloadScope")
        )
    }
    
    // نطاق كوروتين لاستخراج المعلومات
    single<CoroutineScope>(named("extractionScope")) {
        CoroutineScope(
            Dispatchers.Default + SupervisorJob() + 
            kotlinx.coroutines.CoroutineName("ExtractionScope")
        )
    }
    
    // مدير العمل (WorkManager)
    single<WorkManager> {
        WorkManager.getInstance(androidContext())
    }
    
    // مدير الإشعارات
    single<NotificationManager> {
        androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    
    // خدمة الإشعارات
    single<NotificationService> {
        NotificationService(
            context = androidContext(),
            settingsRepository = get(),
            fileUtils = get(),
            logUtils = get()
        )
    }
    
    // مراقب التحميل
    single<DownloadMonitor> {
        DownloadMonitor(
            downloadManager = get(),
            downloadRepository = get(),
            logUtils = get(),
            monitorScope = get(named("monitorScope"))
        )
    }
    
    // نطاق كوروتين للمراقبة
    single<CoroutineScope>(named("monitorScope")) {
        CoroutineScope(
            Dispatchers.Default + SupervisorJob() + 
            kotlinx.coroutines.CoroutineName("MonitorScope")
        )
    }
    
    // مجمع استخراج المعلومات
    single<VideoInfoExtractionPool> {
        VideoInfoExtractionPool(
            videoInfoExtractor = get(),
            extractionScope = get(named("extractionScope")),
            logUtils = get()
        )
    }
    
    // مدير حالة الشبكة للتحميل
    single<DownloadNetworkManager> {
        DownloadNetworkManager(
            context = androidContext(),
            networkUtils = get(),
            downloadManager = get(),
            preferenceUtils = get(),
            logUtils = get()
        )
    }
    
    // V2 Task System Dependencies
    single<com.example.snaptube.utils.DownloadPreferencesManager> {
        com.example.snaptube.utils.DownloadPreferencesManager(androidContext())
    }
    
    single<com.example.snaptube.utils.DownloadNotificationManager> {
        com.example.snaptube.utils.DownloadNotificationManager(androidContext())
    }
    
    single<com.example.snaptube.download.TaskFactory> {
        com.example.snaptube.download.TaskFactory
    }
    
    factory<com.example.snaptube.download.TaskDownloaderV2> {
        com.example.snaptube.download.TaskDownloaderV2(
            appContext = androidContext(),
            notificationManager = get(),
            preferencesManager = get(),
            downloadManager = get(),
            fileUtils = get(),
            videoInfoExtractor = get()
        )
    }
}

/**
 * مراقب التحميل
 * يراقب حالة التحميلات ويدير العمليات التلقائية
 */
class DownloadMonitor(
    private val downloadManager: DownloadManager,
    private val downloadRepository: com.example.snaptube.repository.DownloadRepository,
    private val logUtils: LogUtils,
    private val monitorScope: CoroutineScope
) {
    private var isMonitoring = false
    
    /**
     * بدء مراقبة التحميلات
     */
    fun startMonitoring() {
        if (isMonitoring) return
        
        isMonitoring = true
        logUtils.debug("DownloadMonitor", "Started monitoring downloads")
        
        monitorScope.launch {
            while (isMonitoring) {
                try {
                    // فحص التحميلات المعلقة
                    checkStuckDownloads()
                    
                    // فحص التحميلات المنتهية
                    checkCompletedDownloads()
                    
                    // تنظيف الملفات المؤقتة
                    cleanupTempFiles()
                    
                    // انتظار قبل الفحص التالي
                    kotlinx.coroutines.delay(30_000) // 30 ثانية
                    
                } catch (e: Exception) {
                    logUtils.error("DownloadMonitor", "Error in monitoring loop", e)
                    kotlinx.coroutines.delay(60_000) // انتظار أطول عند الخطأ
                }
            }
        }
    }
    
    /**
     * إيقاف مراقبة التحميلات
     */
    fun stopMonitoring() {
        isMonitoring = false
        logUtils.debug("DownloadMonitor", "Stopped monitoring downloads")
    }
    
    private suspend fun checkStuckDownloads() {
        val stuckDownloads = downloadRepository.getStuckDownloads(
            timeoutMinutes = 30
        )
        
        for (download in stuckDownloads) {
            logUtils.warn("DownloadMonitor", "Found stuck download: ${download.id}")
            downloadManager.cancelDownload(download.id)
        }
    }
    
    private suspend fun checkCompletedDownloads() {
        val completedDownloads = downloadRepository.getCompletedDownloadsWithoutFile()
        
        for (download in completedDownloads) {
            logUtils.warn("DownloadMonitor", "Found completed download without file: ${download.id}")
            downloadRepository.markDownloadFailed(
                download.id,
                "File not found after completion"
            )
        }
    }
    
    private suspend fun cleanupTempFiles() {
        try {
            val tempFiles = downloadRepository.getTemporaryFiles()
            var cleanedCount = 0
            
            for (file in tempFiles) {
                if (file.exists() && file.delete()) {
                    cleanedCount++
                }
            }
            
            if (cleanedCount > 0) {
                logUtils.debug("DownloadMonitor", "Cleaned up $cleanedCount temporary files")
            }
        } catch (e: Exception) {
            logUtils.error("DownloadMonitor", "Error cleaning temporary files", e)
        }
    }
}

/**
 * مجمع استخراج معلومات الفيديو
 * يدير طلبات استخراج المعلومات بكفاءة
 */
class VideoInfoExtractionPool(
    private val videoInfoExtractor: VideoInfoExtractor,
    private val extractionScope: CoroutineScope,
    private val logUtils: LogUtils
) {
    private val pendingExtractions = mutableMapOf<String, Deferred<com.example.snaptube.models.VideoInfo?>>()
    
    /**
     * استخراج معلومات الفيديو مع تجميع الطلبات المتشابهة
     */
    suspend fun extractVideoInfo(url: String): com.example.snaptube.models.VideoInfo? {
        // التحقق من وجود استخراج جاري للرابط نفسه
        pendingExtractions[url]?.let { existingDeferred ->
            logUtils.debug("VideoInfoExtractionPool", "Reusing existing extraction for: $url")
            return existingDeferred.await()
        }
        
        // بدء استخراج جديد
        val deferred = extractionScope.async {
            try {
                logUtils.debug("VideoInfoExtractionPool", "Starting extraction for: $url")
                val result = videoInfoExtractor.extractVideoInfo(url)
                logUtils.debug("VideoInfoExtractionPool", "Completed extraction for: $url")
                result
            } catch (e: Exception) {
                logUtils.error("VideoInfoExtractionPool", "Failed extraction for: $url", e)
                null
            } finally {
                // إزالة من الطلبات المعلقة
                pendingExtractions.remove(url)
            }
        }
        
        pendingExtractions[url] = deferred
        return deferred.await()
    }
    
    /**
     * إلغاء جميع الاستخراجات المعلقة
     */
    fun cancelAllExtractions() {
        pendingExtractions.values.forEach { it.cancel() }
        pendingExtractions.clear()
        logUtils.debug("VideoInfoExtractionPool", "Cancelled all pending extractions")
    }
    
    /**
     * عدد الاستخراجات المعلقة
     */
    fun getPendingCount(): Int = pendingExtractions.size
}

/**
 * مدير شبكة التحميل
 * يدير سلوك التحميل حسب حالة الشبكة
 */
class DownloadNetworkManager(
    private val context: Context,
    private val networkUtils: NetworkUtils,
    private val downloadManager: DownloadManager,
    private val preferenceUtils: PreferenceUtils,
    private val logUtils: LogUtils
) {
    
    init {
        // مراقبة تغييرات الشبكة
        networkUtils.registerNetworkCallback { isConnected ->
            if (isConnected) {
                handleNetworkReconnected()
            } else {
                handleNetworkDisconnected()
            }
        }
    }
    
    private fun handleNetworkReconnected() {
        logUtils.debug("DownloadNetworkManager", "Network reconnected")
        
        CoroutineScope(Dispatchers.Main).launch {
            // استئناف التحميلات المتوقفة إذا كان مسموحاً
            if (shouldResumeOnNetworkReconnect()) {
                downloadManager.resumePausedDownloads()
            }
        }
    }
    
    private fun handleNetworkDisconnected() {
        logUtils.debug("DownloadNetworkManager", "Network disconnected")
        
        CoroutineScope(Dispatchers.Main).launch {
            // إيقاف التحميلات مؤقتاً
            downloadManager.pauseAllDownloads()
        }
    }
    
    private fun shouldResumeOnNetworkReconnect(): Boolean {
        // التحقق من إعدادات المستخدم
        val wifiOnly = preferenceUtils.getBoolean("wifi_only_downloads", false)
        val currentNetworkType = networkUtils.getNetworkType()
        
        return when {
            !wifiOnly -> true // السماح بجميع أنواع الشبكات
            currentNetworkType == NetworkUtils.NetworkType.WIFI -> true
            else -> false
        }
    }
}
