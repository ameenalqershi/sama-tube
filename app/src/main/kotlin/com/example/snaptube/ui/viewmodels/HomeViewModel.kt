package com.example.snaptube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.VideoInfoExtractor
import com.example.snaptube.models.VideoInfo
import com.example.snaptube.models.DownloadFormat
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel : ViewModel(), KoinComponent {
    
    // الحصول على التبعيات من Koin
    private val downloadManager: DownloadManager = get()
    private val videoInfoExtractor: VideoInfoExtractor = get()
    
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    private val _urlInput = MutableStateFlow("")
    val urlInput: StateFlow<String> = _urlInput.asStateFlow()
    
    fun updateUrl(url: String) {
        _urlInput.value = url
    }
    
    fun extractVideoInfo() {
        if (_urlInput.value.isBlank()) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                // استخراج معلومات الفيديو الأساسية
                val videoInfo = videoInfoExtractor.extractVideoInfo(_urlInput.value)
                Timber.d("تم استخراج معلومات الفيديو: ${videoInfo.title}")
                
                // جلب تنسيقات التحميل المتاحة
                val formats = videoInfoExtractor.getAvailableFormats(_urlInput.value)
                Timber.d("تم جلب ${formats.size} تنسيق متاح")
                
                // دمج المعلومات مع التنسيقات
                val completeVideoInfo = videoInfo.copy(formats = formats)
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    videoInfo = completeVideoInfo
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "خطأ في استخراج المعلومات"
                )
                Timber.e(e, "خطأ في استخراج معلومات الفيديو")
            }
        }
    }
    
    fun startDownload(format: DownloadFormat) {
        val videoInfo = _uiState.value.videoInfo ?: return
        
        viewModelScope.launch {
            try {
                val result = downloadManager.startDownload(
                    url = _urlInput.value,
                    selectedFormat = format,
                    downloadPath = "/storage/emulated/0/Download/Snaptube",
                    audioOnly = format.hasAudio && !format.hasVideo
                )
                
                if (result.isSuccess) {
                    Timber.d("تم بدء التحميل: ${videoInfo.title}")
                    // يمكن إضافة رسالة نجاح هنا
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = result.exceptionOrNull()?.message ?: "خطأ في بدء التحميل"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "خطأ في بدء التحميل"
                )
                Timber.e(e, "خطأ في بدء التحميل")
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun clearVideoInfo() {
        _uiState.value = _uiState.value.copy(videoInfo = null)
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val videoInfo: VideoInfo? = null,
    val error: String? = null
)
