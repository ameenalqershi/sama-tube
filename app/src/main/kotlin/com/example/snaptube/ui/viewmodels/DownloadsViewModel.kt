package com.example.snaptube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.DownloadTask
import com.example.snaptube.download.DownloadState
import com.example.snaptube.repository.DownloadRepository
import com.example.snaptube.database.entities.DownloadEntity
import com.example.snaptube.database.entities.toDownloadTask
import com.example.snaptube.utils.FileUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.File

class DownloadsViewModel : ViewModel(), KoinComponent {
    
    // الحصول على التبعيات من Koin
    private val downloadManager: DownloadManager = get()
    private val downloadRepository: DownloadRepository = get()
    private val fileUtils: FileUtils = get()
    
    private val _uiState = MutableStateFlow(DownloadsUiState())
    val uiState: StateFlow<DownloadsUiState> = _uiState.asStateFlow()
    
    init {
        observeDownloads()
        observeDownloadStates()
    }
    
    private fun observeDownloads() {
        viewModelScope.launch {
            try {
                // استخدام Repository بدلاً من DownloadManager مباشرة لتتبع التحميلات
                downloadRepository.getAllDownloads().collect { downloads ->
                    _uiState.value = _uiState.value.copy(
                        downloads = downloads.map { it.toDownloadTask() },
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "خطأ في تحميل القائمة"
                )
                Timber.e(e, "خطأ في تحميل قائمة التحميلات")
            }
        }
    }
    
    private fun observeDownloadStates() {
        viewModelScope.launch {
            downloadManager.downloadStateFlow.collect { state ->
                when (state) {
                    is DownloadState.Progress -> {
                        updateDownloadProgress(state.downloadId, (state.progress * 100).toInt())
                    }
                    is DownloadState.Completed -> {
                        refreshDownloads()
                    }
                    is DownloadState.Failed -> {
                        refreshDownloads()
                    }
                    is DownloadState.Started -> {
                        refreshDownloads()
                    }
                    is DownloadState.Cancelled -> {
                        refreshDownloads()
                    }
                    else -> {
                        // معالجة باقي الحالات حسب الحاجة
                    }
                }
            }
        }
    }
    
    fun pauseDownload(downloadId: String) {
        viewModelScope.launch {
            try {
                downloadManager.pauseDownload(downloadId)
                Timber.d("تم إيقاف التحميل: $downloadId")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في إيقاف التحميل"
                )
                Timber.e(e, "خطأ في إيقاف التحميل")
            }
        }
    }
    
    fun resumeDownload(downloadId: String) {
        viewModelScope.launch {
            try {
                downloadManager.resumeDownload(downloadId)
                Timber.d("تم استكمال التحميل: $downloadId")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في استكمال التحميل"
                )
                Timber.e(e, "خطأ في استكمال التحميل")
            }
        }
    }
    
    fun cancelDownload(downloadId: String) {
        viewModelScope.launch {
            try {
                downloadManager.cancelDownload(downloadId)
                refreshDownloads()
                Timber.d("تم إلغاء التحميل: $downloadId")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في إلغاء التحميل"
                )
                Timber.e(e, "خطأ في إلغاء التحميل")
            }
        }
    }
    
    fun retryDownload(downloadId: String) {
        viewModelScope.launch {
            try {
                val download = downloadManager.getDownload(downloadId)
                if (download != null) {
                    downloadManager.startDownload(
                        url = download.url,
                        selectedFormat = download.downloadFormat,
                        downloadPath = download.downloadPath,
                        audioOnly = download.audioOnly
                    )
                    refreshDownloads()
                    Timber.d("تم إعادة بدء التحميل: $downloadId")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في إعادة المحاولة"
                )
                Timber.e(e, "خطأ في إعادة المحاولة")
            }
        }
    }
    
    fun deleteDownload(downloadId: String, deleteFile: Boolean = false) {
        viewModelScope.launch {
            try {
                downloadManager.deleteDownload(downloadId, deleteFile)
                refreshDownloads()
                Timber.d("تم حذف التحميل: $downloadId")
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في حذف التحميل"
                )
                Timber.e(e, "خطأ في حذف التحميل")
            }
        }
    }
    
    fun openFile(downloadId: String) {
        viewModelScope.launch {
            try {
                val download = downloadRepository.getDownloadById(downloadId)
                if (download != null) {
                    val file = File(download.outputPath)
                    if (file.exists()) {
                        fileUtils.openFile(file)
                        Timber.d("فتح الملف: ${download.title}")
                    } else {
                        _uiState.value = _uiState.value.copy(
                            error = "الملف غير موجود: ${download.title}"
                        )
                        Timber.w("الملف غير موجود: ${download.outputPath}")
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "لم يتم العثور على التحميل"
                    )
                    Timber.w("تحميل غير موجود: $downloadId")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "خطأ في فتح الملف: ${e.message}"
                )
                Timber.e(e, "خطأ في فتح الملف")
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    private fun updateDownloadProgress(downloadId: String, progress: Int) {
        val currentDownloads = _uiState.value.downloads
        val updatedDownloads = currentDownloads.map { download ->
            if (download.id == downloadId) {
                download.copy(progress = progress)
            } else {
                download
            }
        }
        _uiState.value = _uiState.value.copy(downloads = updatedDownloads)
    }
    
    private suspend fun refreshDownloads() {
        try {
            // استخدام Repository مباشرة للحصول على آخر البيانات
            val downloads = downloadRepository.getAllDownloads().first()
            _uiState.value = _uiState.value.copy(
                downloads = downloads.map { it.toDownloadTask() }
            )
        } catch (e: Exception) {
            Timber.e(e, "خطأ في تحديث قائمة التحميلات")
        }
    }
}

data class DownloadsUiState(
    val downloads: List<DownloadTask> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
