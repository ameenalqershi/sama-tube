package com.example.snaptube.download

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import com.example.snaptube.models.*
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.repository.DownloadRepository
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.NetworkUtils
import java.util.*
import kotlin.collections.ArrayList
import com.example.snaptube.database.entities.toDownloadTask
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest

/**
 * مدير التحميل الرئيسي - مسؤول عن إدارة جميع عمليات التحميل
 * يستخدم yt-dlp لتحميل الفيديوهات والصوتيات
 */
class DownloadManager(
    private val context: Context,
    private val videoInfoExtractor: VideoInfoExtractor,
    private val fileUtils: FileUtils,
    private val networkUtils: NetworkUtils
) : KoinComponent {
    private val TAG = "DownloadManager"
    
    // Lazy injection لتجنب circular dependency
    private val downloadRepository: DownloadRepository by inject()
    
    // خريطة لتتبع مهام التحميل النشطة
    private val activeDownloads = ConcurrentHashMap<String, DownloadTask>()
    
    // Scope للكورتينات
    private val downloadScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // مدير Progress Tracker
    private val progressTracker = ProgressTracker()
    
    // Flow لتتبع حالة التحميلات
    private val _downloadStateFlow = MutableSharedFlow<DownloadState>()
    val downloadStateFlow: SharedFlow<DownloadState> = _downloadStateFlow.asSharedFlow()
    
    /**
     * بدء تحميل فيديو جديد
     */
    suspend fun startDownload(
        url: String,
        selectedFormat: DownloadFormat,
        downloadPath: String,
        audioOnly: Boolean = false
    ): Result<String> {
        return try {
            Log.d(TAG, "بدء تحميل جديد: $url")
            
            // التحقق من صحة الرابط
            if (!networkUtils.isValidUrl(url)) {
                throw IllegalArgumentException("رابط غير صحيح")
            }
            
            // التحقق من اتصال الإنترنت
            if (!networkUtils.isNetworkAvailable()) {
                throw IllegalStateException("لا يوجد اتصال بالإنترنت")
            }
            
            // استخراج معلومات الفيديو
            val videoInfo = videoInfoExtractor.extractVideoInfo(url)
            
            // إنشاء معرف فريد للتحميل
            val downloadId = generateDownloadId()
            
            // إنشاء مهمة تحميل جديدة
            val downloadTask = DownloadTask(
                id = downloadId,
                url = url,
                title = videoInfo.title,
                downloadFormat = selectedFormat,
                downloadPath = downloadPath,
                audioOnly = audioOnly,
                totalSize = videoInfo.fileSize,
                status = DownloadStatus.PENDING
            )
            
            // حفظ في قاعدة البيانات
            val downloadEntity = downloadTask.toEntity()
            downloadRepository.insertDownload(downloadEntity)
            
            // إضافة إلى المهام النشطة
            activeDownloads[downloadId] = downloadTask
            
            // بدء التحميل في الخلفية
            downloadScope.launch {
                executeDownload(downloadTask, videoInfo)
            }
            
            // إرسال إشعار بدء التحميل
            _downloadStateFlow.emit(
                DownloadState.Started(downloadId, downloadTask.title)
            )
            
            Result.success(downloadId)
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في بدء التحميل", e)
            Result.failure(e)
        }
    }
    
    /**
     * تنفيذ عملية التحميل الفعلية
     */
    @Suppress("UNUSED_PARAMETER")
    private suspend fun executeDownload(downloadTask: DownloadTask, _videoInfo: VideoInfo) {
        try {
            Log.d(TAG, "تنفيذ التحميل: ${downloadTask.id}")
            
            // تحديث حالة إلى DOWNLOADING
            updateDownloadStatus(downloadTask.id, DownloadStatus.DOWNLOADING)
            
            // إنشاء مجلد التحميل إذا لم يكن موجوداً
            val downloadDir = File(downloadTask.downloadPath)
            if (!downloadDir.exists()) {
                downloadDir.mkdirs()
            }
            
            // تحديد اسم الملف
            val fileName = fileUtils.generateUniqueFileName(
                downloadDir,
                downloadTask.title,
                downloadTask.downloadFormat.extension
            )
            val outputFile = File(downloadDir, fileName)
            
            // استخدام مكتبة YoutubeDL لتنفيذ التحميل
            val request = YoutubeDLRequest(downloadTask.url).apply {
                addOption("-o")
                addOption(outputFile.absolutePath)
                if (downloadTask.audioOnly) {
                    addOption("-x")
                    addOption("--audio-format")
                    addOption(downloadTask.downloadFormat.acodec ?: "mp3")
                } else {
                    addOption("-f")
                    addOption("${downloadTask.downloadFormat.vcodec}+${downloadTask.downloadFormat.acodec}")
                }
                addOption("--no-playlist")
                addOption("--ignore-errors")
                addOption("--newline")
            }
            val response = YoutubeDL.getInstance().execute(request)
            if (response.exitCode == 0 && outputFile.exists()) {
                completeDownload(downloadTask.id, outputFile.absolutePath)
            } else {
                failDownload(downloadTask.id, response.err ?: "فشل في التحميل")
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تنفيذ التحميل", e)
            failDownload(downloadTask.id, e.message ?: "خطأ غير معروف")
        }
    }
    
    /**
     * إيقاف تحميل مؤقتاً
     */
    suspend fun pauseDownload(downloadId: String): Result<Unit> {
        return try {
            activeDownloads[downloadId] ?: return Result.failure(IllegalArgumentException("تحميل غير موجود"))
            
            updateDownloadStatus(downloadId, DownloadStatus.PAUSED)
            
            _downloadStateFlow.emit(
                DownloadState.Paused(downloadId)
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * استئناف تحميل متوقف
     */
    suspend fun resumeDownload(downloadId: String): Result<Unit> {
        return try {
            activeDownloads[downloadId] ?: return Result.failure(IllegalArgumentException("تحميل غير موجود"))
            
            updateDownloadStatus(downloadId, DownloadStatus.DOWNLOADING)
            
            _downloadStateFlow.emit(
                DownloadState.Resumed(downloadId)
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * إلغاء تحميل
     */
    suspend fun cancelDownload(downloadId: String): Result<Unit> {
        return try {
            activeDownloads[downloadId] ?: return Result.failure(IllegalArgumentException("تحميل غير موجود"))
            
            // إزالة من المهام النشطة
            activeDownloads.remove(downloadId)
            
            // تحديث في قاعدة البيانات
            updateDownloadStatus(downloadId, DownloadStatus.CANCELLED)
            
            _downloadStateFlow.emit(
                DownloadState.Cancelled(downloadId)
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * الحصول على جميع التحميلات
     */
    suspend fun getAllDownloads(): List<DownloadTask> {
        return try {
            // نجلب قائمة التحميلات من الـ Flow ثم نحولها إلى مهام تحميل
            val entities = downloadRepository.getAllDownloads().first()
            entities.map { it.toDownloadTask() }
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في جلب التحميلات", e)
            emptyList()
        }
    }
    
    /**
     * الحصول على تحميل محدد
     */
    suspend fun getDownload(downloadId: String): DownloadTask? {
        return try {
            val download = downloadRepository.getDownloadById(downloadId)
            download?.toDownloadTask()
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في جلب التحميل", e)
            null
        }
    }
    
    /**
     * حذف تحميل
     */
    suspend fun deleteDownload(downloadId: String, deleteFile: Boolean = false): Result<Unit> {
        return try {
            val download = downloadRepository.getDownloadById(downloadId)
            
            if (download != null && deleteFile && download.filePath.isNotEmpty()) {
                // حذف الملف من التخزين
                val file = File(download.filePath)
                if (file.exists()) {
                    file.delete()
                }
            }
            
            // حذف من قاعدة البيانات
            downloadRepository.deleteDownload(downloadId)
            
            // إزالة من المهام النشطة
            activeDownloads.remove(downloadId)
            
            _downloadStateFlow.emit(
                DownloadState.Deleted(downloadId)
            )
            
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * تحديث حالة التحميل
     */
    private suspend fun updateDownloadStatus(downloadId: String, status: DownloadStatus) {
        try {
            // تحديث في المهام النشطة
            activeDownloads[downloadId]?.status = status
            // تحديث في قاعدة البيانات
            downloadRepository.updateDownloadStatus(downloadId, status)
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحديث حالة التحميل", e)
        }
    }
    
    /**
     * تحديث تقدم التحميل
     */
    private suspend fun updateDownloadProgress(downloadId: String, progress: Int) {
        try {
            // تحديث في المهام النشطة
            activeDownloads[downloadId]?.progress = progress
            
            // تحديث في قاعدة البيانات
            downloadRepository.updateDownloadProgress(downloadId, progress)
            // إرسال إشعار بالتقدم
            _downloadStateFlow.emit(
                DownloadState.Progress(downloadId, progress)
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في تحديث تقدم التحميل", e)
        }
    }
    
    /**
     * إكمال التحميل بنجاح
     */
    private suspend fun completeDownload(downloadId: String, filePath: String) {
        try {
            Log.d(TAG, "تم إكمال التحميل: $downloadId")
            
            // تحديث في المهام النشطة
            activeDownloads[downloadId]?.let { task ->
                task.status = DownloadStatus.COMPLETED
                task.progress = 100
                task.filePath = filePath
            }
            
            // إزالة من المهام النشطة
            activeDownloads.remove(downloadId)
            
            // تحديث في قاعدة البيانات بوضع الحالة مكتملة وحجم الملف النهائي
            downloadRepository.markDownloadCompleted(downloadId, File(filePath).length())
            
            // إرسال إشعار بالإكمال
            _downloadStateFlow.emit(
                DownloadState.Completed(downloadId, filePath)
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في إكمال التحميل", e)
        }
    }
    
    /**
     * فشل التحميل
     */
    private suspend fun failDownload(downloadId: String, errorMessage: String) {
        try {
            Log.e(TAG, "فشل التحميل: $downloadId - $errorMessage")
            
            // تحديث في المهام النشطة
            activeDownloads[downloadId]?.status = DownloadStatus.FAILED
            
            // إزالة من المهام النشطة
            activeDownloads.remove(downloadId)
            
            // تحديث في قاعدة البيانات بحالة فشل مع رسالة الخطأ
            downloadRepository.markDownloadFailed(downloadId, errorMessage)
            
            // إرسال إشعار بالفشل
            _downloadStateFlow.emit(
                DownloadState.Failed(downloadId, errorMessage)
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "خطأ في معالجة فشل التحميل", e)
        }
    }
    
    /**
     * توليد معرف فريد للتحميل
     */
    private fun generateDownloadId(): String {
        return UUID.randomUUID().toString()
    }
    
    /**
     * تنظيف الموارد
     */
    fun cleanup() {
        downloadScope.cancel()
        activeDownloads.clear()
        progressTracker.cleanup()
    }

    /**
     * Pause all active downloads
     */
    suspend fun pauseAllDownloads() {
        activeDownloads.keys.forEach { id ->
            pauseDownload(id)
        }
    }

    /**
     * Resume all downloads
     */
    suspend fun resumeAllDownloads() {
        activeDownloads.keys.forEach { id ->
            resumeDownload(id)
        }
    }

    /**
     * Resume only paused downloads
     */
    suspend fun resumePausedDownloads() {
        activeDownloads.filter { it.value.status == DownloadStatus.PAUSED }.keys.forEach { id ->
            resumeDownload(id)
        }
    }

    /**
     * Cancel all active downloads
     */
    suspend fun cancelAllDownloads() {
        activeDownloads.keys.forEach { id ->
            cancelDownload(id)
        }
    }
}
