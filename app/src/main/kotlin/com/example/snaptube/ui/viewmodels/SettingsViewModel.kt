package com.example.snaptube.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snaptube.repository.SettingsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import java.io.File
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SettingsViewModel : ViewModel(), KoinComponent {
    
    // الحصول على التبعيات من Koin
    private val settingsRepository: SettingsRepository = get()
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    // إضافة الخصائص المفقودة
    private val _downloadDirectory = MutableStateFlow("/storage/emulated/0/Download/SnapTube")
    val downloadDirectory: StateFlow<String> = _downloadDirectory.asStateFlow()
    
    private val _useSystemTheme = MutableStateFlow(true)
    val useSystemTheme: StateFlow<Boolean> = _useSystemTheme.asStateFlow()
    
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()
        
    private val _appVersion = MutableStateFlow("1.0.0")
    val appVersion: StateFlow<String> = _appVersion.asStateFlow()
    
    init {
        loadSettings()
        loadVersionInfo()
    }
    
    private fun loadSettings() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // تحميل جميع الإعدادات
                combine(
                    settingsRepository.appSettings,
                    settingsRepository.downloadSettings,
                    settingsRepository.uiSettings
                ) { appSettings, downloadSettings, uiSettings ->
                    Triple(appSettings, downloadSettings, uiSettings)
                }.collect { (appSettings, downloadSettings, uiSettings) ->
                    _uiState.value = _uiState.value.copy(
                        appSettings = appSettings,
                        downloadSettings = downloadSettings,
                        uiSettings = uiSettings,
                        isLoading = false
                    )
                    
                    // تحديث الخصائص
                    _downloadDirectory.value = downloadSettings.downloadLocation
                    _useSystemTheme.value = uiSettings.useSystemTheme
                    _isDarkTheme.value = uiSettings.darkMode
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "خطأ في تحميل الإعدادات"
                )
                Timber.e(e, "خطأ في تحميل الإعدادات")
            }
        }
    }
    
    private fun loadVersionInfo() {
        try {
            // في مشروع حقيقي، سيتم الحصول على إصدار التطبيق من BuildConfig
            _appVersion.value = "1.0.0"
        } catch (e: Exception) {
            Timber.e(e, "خطأ في الحصول على إصدار التطبيق")
            _appVersion.value = "غير معروف"
        }
    }
    
    // إعدادات التطبيق العامة
    fun updateLanguage(language: String) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.appSettings ?: return@launch
                val updatedSettings = currentSettings.copy(language = language)
                settingsRepository.updateAppSettings(updatedSettings)
                Timber.d("تم تحديث اللغة إلى: $language")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث اللغة")
            }
        }
    }
    
    // إضافة الوظائف المفقودة
    fun selectDownloadDirectory() {
        // هذه الوظيفة ستقوم بفتح مربع اختيار المجلد في تطبيق فعلي
        // ولكن هنا نقوم فقط بتحديث القيمة في الذاكرة
        _downloadDirectory.value = "/storage/emulated/0/Download/SnapTube"
        
        // تحديث الإعدادات في Repository
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.downloadSettings ?: return@launch
                val updatedSettings = currentSettings.copy(downloadLocation = _downloadDirectory.value)
                settingsRepository.updateDownloadSettings(updatedSettings)
                Timber.d("تم تحديث مجلد التحميل: ${_downloadDirectory.value}")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث مجلد التحميل")
            }
        }
    }
    
    fun setUseSystemTheme(enabled: Boolean) {
        _useSystemTheme.value = enabled
        
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.uiSettings ?: return@launch
                val updatedSettings = currentSettings.copy(useSystemTheme = enabled)
                settingsRepository.updateUiSettings(updatedSettings)
                Timber.d("تم تحديث استخدام سمة النظام: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث إعداد سمة النظام")
            }
        }
    }
    
    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
        
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.uiSettings ?: return@launch
                val updatedSettings = currentSettings.copy(darkMode = enabled)
                settingsRepository.updateUiSettings(updatedSettings)
                Timber.d("تم تحديث الوضع المظلم: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث الوضع المظلم")
            }
        }
    }
    
    fun openLicensesScreen() {
        // هذا سيفتح شاشة التراخيص في تطبيق فعلي
        Timber.d("فتح شاشة التراخيص")
    }
    
    fun openPrivacyPolicy() {
        // هذا سيفتح صفحة سياسة الخصوصية في تطبيق فعلي
        Timber.d("فتح صفحة سياسة الخصوصية")
    }
    
    fun updateAutoCleanup(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.appSettings ?: return@launch
                val updatedSettings = currentSettings.copy(autoCleanup = enabled)
                settingsRepository.updateAppSettings(updatedSettings)
                Timber.d("تم تحديث التنظيف التلقائي: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث التنظيف التلقائي")
            }
        }
    }
    
    fun updateCleanupInterval(days: Int) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.appSettings ?: return@launch
                val updatedSettings = currentSettings.copy(cleanupIntervalDays = days)
                settingsRepository.updateAppSettings(updatedSettings)
                Timber.d("تم تحديث فترة التنظيف: $days أيام")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث فترة التنظيف")
            }
        }
    }
    
    fun updateAnalyticsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.appSettings ?: return@launch
                val updatedSettings = currentSettings.copy(analyticsEnabled = enabled)
                settingsRepository.updateAppSettings(updatedSettings)
                Timber.d("تم تحديث التحليلات: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث إعدادات التحليلات")
            }
        }
    }
    
    // إعدادات التحميل
    fun updateVideoQuality(quality: String) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.downloadSettings ?: return@launch
                val updatedSettings = currentSettings.copy(videoQuality = quality)
                settingsRepository.updateDownloadSettings(updatedSettings)
                Timber.d("تم تحديث جودة الفيديو: $quality")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث جودة الفيديو")
            }
        }
    }
    
    fun updateAudioQuality(quality: String) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.downloadSettings ?: return@launch
                val updatedSettings = currentSettings.copy(audioQuality = quality)
                settingsRepository.updateDownloadSettings(updatedSettings)
                Timber.d("تم تحديث جودة الصوت: $quality")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث جودة الصوت")
            }
        }
    }
    
    fun updateMaxConcurrentDownloads(count: Int) {
        viewModelScope.launch {
            try {
                if (count in 1..10) {
                    val currentSettings = _uiState.value.downloadSettings ?: return@launch
                    val updatedSettings = currentSettings.copy(maxConcurrentDownloads = count)
                    settingsRepository.updateDownloadSettings(updatedSettings)
                    Timber.d("تم تحديث عدد التحميلات المتزامنة: $count")
                } else {
                    _uiState.value = _uiState.value.copy(
                        error = "عدد التحميلات المتزامنة يجب أن يكون بين 1 و 10"
                    )
                }
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث عدد التحميلات المتزامنة")
            }
        }
    }
    
    fun updatePauseOnLowBattery(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.downloadSettings ?: return@launch
                val updatedSettings = currentSettings.copy(pauseOnLowBattery = enabled)
                settingsRepository.updateDownloadSettings(updatedSettings)
                Timber.d("تم تحديث إيقاف التحميل عند انخفاض البطارية: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث إعدادات البطارية")
            }
        }
    }
    
    // إعدادات واجهة المستخدم
    fun updateTheme(theme: String) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.uiSettings ?: return@launch
                val updatedSettings = currentSettings.copy(theme = theme)
                settingsRepository.updateUiSettings(updatedSettings)
                Timber.d("تم تحديث السمة: $theme")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث السمة")
            }
        }
    }
    
    fun updateShowThumbnails(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.uiSettings ?: return@launch
                val updatedSettings = currentSettings.copy(showThumbnails = enabled)
                settingsRepository.updateUiSettings(updatedSettings)
                Timber.d("تم تحديث عرض الصور المصغرة: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث عرض الصور المصغرة")
            }
        }
    }
    
    fun updateGridView(enabled: Boolean) {
        viewModelScope.launch {
            try {
                val currentSettings = _uiState.value.uiSettings ?: return@launch
                val updatedSettings = currentSettings.copy(gridView = enabled)
                settingsRepository.updateUiSettings(updatedSettings)
                Timber.d("تم تحديث عرض الشبكة: $enabled")
            } catch (e: Exception) {
                handleError(e, "خطأ في تحديث عرض الشبكة")
            }
        }
    }
    
    // وظائف إضافية
    fun getStorageInfo(): StorageInfo {
        return try {
            val downloadLocation = _uiState.value.downloadSettings?.downloadLocation ?: "/storage/emulated/0/Download"
            val dir = File(downloadLocation)
            
            val totalSpace = dir.totalSpace
            val freeSpace = dir.freeSpace
            val usedSpace = totalSpace - freeSpace
            
            // حساب عدد الملفات
            val files = dir.listFiles() ?: emptyArray()
            val videoCount = files.count { it.extension.lowercase() in listOf("mp4", "mkv", "avi", "webm") }
            val audioCount = files.count { it.extension.lowercase() in listOf("mp3", "m4a", "webm", "ogg") }
            
            StorageInfo(
                totalFiles = files.size,
                totalSize = files.sumOf { it.length() },
                videoCount = videoCount,
                audioCount = audioCount,
                totalSpace = totalSpace,
                freeSpace = freeSpace,
                usedSpace = usedSpace
            )
        } catch (e: Exception) {
            Timber.e(e, "خطأ في الحصول على معلومات التخزين")
            StorageInfo(0, 0, 0, 0, 0, 0, 0)
        }
    }
    
    fun clearCache() {
        viewModelScope.launch {
            try {
                // حذف الملفات المؤقتة
                val cacheDir = File("/data/data/com.example.snaptube/cache")
                if (cacheDir.exists()) {
                    cacheDir.deleteRecursively()
                    cacheDir.mkdirs()
                }
                
                _uiState.value = _uiState.value.copy(
                    message = "تم حذف الملفات المؤقتة بنجاح"
                )
                Timber.d("تم حذف الملفات المؤقتة")
            } catch (e: Exception) {
                handleError(e, "خطأ في حذف الملفات المؤقتة")
            }
        }
    }
    
    fun resetToDefaults() {
        viewModelScope.launch {
            try {
                settingsRepository.resetAllSettings()
                _uiState.value = _uiState.value.copy(
                    message = "تم استعادة الإعدادات الافتراضية"
                )
                Timber.d("تم استعادة الإعدادات الافتراضية")
            } catch (e: Exception) {
                handleError(e, "خطأ في استعادة الإعدادات الافتراضية")
            }
        }
    }
    
    fun exportSettings() {
        viewModelScope.launch {
            try {
                val result = settingsRepository.exportSettings("/storage/emulated/0/Download/snaptube_settings.json")
                result.fold(
                    onSuccess = { file ->
                        _uiState.value = _uiState.value.copy(
                            message = "تم تصدير الإعدادات إلى: ${file.absolutePath}"
                        )
                    },
                    onFailure = { error ->
                        handleError(error as Exception, "خطأ في تصدير الإعدادات")
                    }
                )
            } catch (e: Exception) {
                handleError(e, "خطأ في تصدير الإعدادات")
            }
        }
    }
    
    fun importSettings(settingsJson: String) {
        viewModelScope.launch {
            try {
                val result = settingsRepository.importSettings(settingsJson)
                result.fold(
                    onSuccess = {
                        _uiState.value = _uiState.value.copy(
                            message = "تم استيراد الإعدادات بنجاح"
                        )
                    },
                    onFailure = { error ->
                        handleError(error as Exception, "خطأ في استيراد الإعدادات")
                    }
                )
                Timber.d("تم استيراد الإعدادات")
            } catch (e: Exception) {
                handleError(e, "خطأ في استيراد الإعدادات")
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(message = null)
    }
    
    private fun handleError(exception: Exception, message: String) {
        _uiState.value = _uiState.value.copy(error = message)
        Timber.e(exception, message)
    }
    
    // الحصول على قائمة الخيارات المتاحة
    fun getAvailableLanguages(): List<LanguageOption> {
        return listOf(
            LanguageOption("ar", "العربية"),
            LanguageOption("en", "English"),
            LanguageOption("fr", "Français"),
            LanguageOption("es", "Español"),
            LanguageOption("de", "Deutsch")
        )
    }
    
    fun getAvailableVideoQualities(): List<String> {
        return listOf("144p", "240p", "360p", "480p", "720p", "1080p", "1440p", "2160p")
    }
    
    fun getAvailableAudioQualities(): List<String> {
        return listOf("64kbps", "96kbps", "128kbps", "192kbps", "256kbps", "320kbps")
    }
    
    fun getAvailableThemes(): List<ThemeOption> {
        return listOf(
            ThemeOption("system", "حسب النظام"),
            ThemeOption("light", "فاتح"),
            ThemeOption("dark", "مظلم")
        )
    }
}

data class SettingsUiState(
    val appSettings: SettingsRepository.AppSettings? = null,
    val downloadSettings: SettingsRepository.DownloadSettings? = null,
    val uiSettings: SettingsRepository.UiSettings? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val message: String? = null
)

data class StorageInfo(
    val totalFiles: Int,
    val totalSize: Long,
    val videoCount: Int,
    val audioCount: Int,
    val totalSpace: Long = 0L,
    val freeSpace: Long = 0L,
    val usedSpace: Long = 0L
) {
    fun getFormattedTotalSize(): String = formatBytes(totalSize)
    fun getFormattedUsedSpace(): String = formatBytes(usedSpace)
    fun getFormattedTotalSpace(): String = formatBytes(totalSpace)
    
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes >= 1_073_741_824 -> "%.1f GB".format(bytes / 1_073_741_824.0)
            bytes >= 1_048_576 -> "%.1f MB".format(bytes / 1_048_576.0)
            bytes >= 1_024 -> "%.1f KB".format(bytes / 1_024.0)
            else -> "$bytes B"
        }
    }
    
    fun getUsagePercentage(): Float {
        return if (totalSpace > 0) (usedSpace.toFloat() / totalSpace * 100) else 0f
    }
}

data class LanguageOption(
    val code: String,
    val name: String
)

data class ThemeOption(
    val code: String,
    val name: String
)
