package com.example.snaptube.repository

import com.example.snaptube.utils.PreferenceUtils
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.NetworkUtils
import com.example.snaptube.utils.LogUtils
import com.example.snaptube.models.QualityOption
import com.google.gson.Gson
import com.example.snaptube.models.DownloadFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import java.io.File

/**
 * Repository for managing application settings and preferences
 * Provides centralized settings management with real-time updates
 */
class SettingsRepository(
    private val preferenceUtils: PreferenceUtils,
    private val fileUtils: FileUtils,
    private val networkUtils: NetworkUtils,
    private val logUtils: LogUtils
) {

    companion object {
        // Default settings values
        const val DEFAULT_DOWNLOAD_LOCATION = "Download/Snaptube"
        const val DEFAULT_VIDEO_QUALITY = "720p"
        const val DEFAULT_AUDIO_QUALITY = "128k"
        const val DEFAULT_MAX_CONCURRENT_DOWNLOADS = 3
        const val DEFAULT_NETWORK_TIMEOUT = 30
        const val DEFAULT_MAX_RETRIES = 3
        const val DEFAULT_THEME = "system"
        const val DEFAULT_LANGUAGE = "en"
    }

    // Settings state flows for real-time updates
    private val _appSettings = MutableStateFlow(loadAppSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings.asStateFlow()

    private val _downloadSettings = MutableStateFlow(loadDownloadSettings())
    val downloadSettings: StateFlow<DownloadSettings> = _downloadSettings.asStateFlow()

    private val _networkSettings = MutableStateFlow(loadNetworkSettings())
    val networkSettings: StateFlow<NetworkSettings> = _networkSettings.asStateFlow()

    private val _uiSettings = MutableStateFlow(loadUiSettings())
    val uiSettings: StateFlow<UiSettings> = _uiSettings.asStateFlow()

    init {
        logUtils.info("SettingsRepository", "Settings repository initialized")
    }

    /**
     * Get all settings as a single object
     */
    fun getAllSettings(): AllSettings {
        return AllSettings(
            app = _appSettings.value,
            download = _downloadSettings.value,
            network = _networkSettings.value,
            ui = _uiSettings.value
        )
    }

    /**
     * Update app settings
     */
    suspend fun updateAppSettings(settings: AppSettings): Result<Unit> {
        return try {
            saveAppSettings(settings)
            _appSettings.value = settings
            logUtils.info("SettingsRepository", "App settings updated")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to update app settings", e)
            Result.failure(e)
        }
    }

    /**
     * Update download settings
     */
    suspend fun updateDownloadSettings(settings: DownloadSettings): Result<Unit> {
        return try {
            // Validate download location
            val downloadDir = File(settings.downloadLocation)
            if (!downloadDir.exists()) {
                fileUtils.createDirectoryIfNotExists(settings.downloadLocation)
            }

            saveDownloadSettings(settings)
            _downloadSettings.value = settings
            logUtils.info("SettingsRepository", "Download settings updated")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to update download settings", e)
            Result.failure(e)
        }
    }

    /**
     * Update network settings
     */
    suspend fun updateNetworkSettings(settings: NetworkSettings): Result<Unit> {
        return try {
            saveNetworkSettings(settings)
            _networkSettings.value = settings
            logUtils.info("SettingsRepository", "Network settings updated")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to update network settings", e)
            Result.failure(e)
        }
    }

    /**
     * Update UI settings
     */
    suspend fun updateUiSettings(settings: UiSettings): Result<Unit> {
        return try {
            saveUiSettings(settings)
            _uiSettings.value = settings
            logUtils.info("SettingsRepository", "UI settings updated")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to update UI settings", e)
            Result.failure(e)
        }
    }

    /**
     * Reset all settings to defaults
     */
    suspend fun resetAllSettings(): Result<Unit> {
        return try {
            val defaultApp = getDefaultAppSettings()
            val defaultDownload = getDefaultDownloadSettings()
            val defaultNetwork = getDefaultNetworkSettings()
            val defaultUi = getDefaultUiSettings()

            saveAppSettings(defaultApp)
            saveDownloadSettings(defaultDownload)
            saveNetworkSettings(defaultNetwork)
            saveUiSettings(defaultUi)

            _appSettings.value = defaultApp
            _downloadSettings.value = defaultDownload
            _networkSettings.value = defaultNetwork
            _uiSettings.value = defaultUi

            logUtils.info("SettingsRepository", "All settings reset to defaults")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to reset settings", e)
            Result.failure(e)
        }
    }

    /**
     * Export settings to file
     */
    suspend fun exportSettings(exportPath: String): Result<File> {
        return try {
            val allSettings = getAllSettings()
            val json = Gson().toJson(allSettings)
            val exportFile = File(exportPath)
            exportFile.parentFile?.mkdirs()
            exportFile.writeText(json)
            logUtils.info("SettingsRepository", "Settings exported to: $exportPath")
            Result.success(exportFile)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to export settings", e)
            Result.failure(e)
        }
    }

    /**
     * Import settings from file
     */
    suspend fun importSettings(importPath: String): Result<Unit> {
        return try {
            val json = File(importPath).readText()
            val importedSettings: AllSettings = Gson().fromJson(json, AllSettings::class.java)
            
            // Apply imported settings
            saveAppSettings(importedSettings.app)
            saveDownloadSettings(importedSettings.download)
            saveNetworkSettings(importedSettings.network)
            saveUiSettings(importedSettings.ui)

            // Update state flows
            _appSettings.value = importedSettings.app
            _downloadSettings.value = importedSettings.download
            _networkSettings.value = importedSettings.network
            _uiSettings.value = importedSettings.ui

            logUtils.info("SettingsRepository", "Settings imported from: $importPath")
            Result.success(Unit)
        } catch (e: Exception) {
            logUtils.error("SettingsRepository", "Failed to import settings", e)
            Result.failure(e)
        }
    }

    /**
     * Get recommended quality based on network
     */
    fun getRecommendedQuality(): QualityOption {
        val recommended = networkUtils.getRecommendedQualityForNetwork()
        val qualityInt = recommended.filter { it.isDigit() }.toIntOrNull()
            ?: DEFAULT_VIDEO_QUALITY.filter { it.isDigit() }.toInt()
        val format = DownloadFormat(formatId = recommended, extension = recommended, quality = recommended)
        return QualityOption(
            id = recommended,
            name = recommended,
            quality = qualityInt,
            format = format,
            isRecommended = true
        )
    }

    /**
     * Get available storage space
     */
    fun getAvailableStorageSpace(): Long {
        val downloadLocation = _downloadSettings.value.downloadLocation
        return File(downloadLocation).freeSpace
    }

    /**
     * Get download location usage
     */
    fun getDownloadLocationUsage(): StorageUsage {
        val downloadLocation = _downloadSettings.value.downloadLocation
        val downloadDir = File(downloadLocation)
        
        return if (downloadDir.exists()) {
            val totalSpace = downloadDir.totalSpace
            val usedSpace = fileUtils.getDirectorySize(downloadDir)
            val availableSpace = downloadDir.freeSpace
            
            StorageUsage(
                totalSpace = totalSpace,
                usedSpace = usedSpace,
                availableSpace = availableSpace,
                usagePercentage = if (totalSpace > 0) ((usedSpace.toDouble() / totalSpace) * 100).toInt() else 0
            )
        } else {
            StorageUsage(0, 0, 0, 0)
        }
    }

    /**
     * Validate settings
     */
    fun validateSettings(): List<SettingsValidationError> {
        val errors = mutableListOf<SettingsValidationError>()
        
        // Validate download settings
        val downloadSettings = _downloadSettings.value
        if (!File(downloadSettings.downloadLocation).canWrite()) {
            errors.add(SettingsValidationError("Download location is not writable"))
        }
        
        if (downloadSettings.maxConcurrentDownloads < 1 || downloadSettings.maxConcurrentDownloads > 10) {
            errors.add(SettingsValidationError("Max concurrent downloads must be between 1 and 10"))
        }
        
        // Validate network settings
        val networkSettings = _networkSettings.value
        if (networkSettings.timeoutSeconds < 5 || networkSettings.timeoutSeconds > 300) {
            errors.add(SettingsValidationError("Network timeout must be between 5 and 300 seconds"))
        }
        
        if (networkSettings.maxRetries < 0 || networkSettings.maxRetries > 10) {
            errors.add(SettingsValidationError("Max retries must be between 0 and 10"))
        }
        
        return errors
    }

    /**
     * Load app settings from preferences
     */
    private fun loadAppSettings(): AppSettings {
        return AppSettings(
            language = preferenceUtils.getString("app_language", DEFAULT_LANGUAGE),
            autoCleanup = preferenceUtils.getBoolean("app_auto_cleanup", true),
            cleanupIntervalDays = preferenceUtils.getInt("app_cleanup_interval_days", 30),
            analyticsEnabled = preferenceUtils.getBoolean("app_analytics_enabled", false),
            crashReportingEnabled = preferenceUtils.getBoolean("app_crash_reporting_enabled", true),
            automaticUpdates = preferenceUtils.getBoolean("app_automatic_updates", true),
            backgroundDownloads = preferenceUtils.getBoolean("app_background_downloads", true)
        )
    }

    /**
     * Load download settings from preferences
     */
    private fun loadDownloadSettings(): DownloadSettings {
        val defaultLocation = fileUtils.getDownloadsDirectory().absolutePath
        
        return DownloadSettings(
            downloadLocation = preferenceUtils.getString("download_location", defaultLocation),
            videoQuality = preferenceUtils.getString("download_video_quality", DEFAULT_VIDEO_QUALITY),
            audioQuality = preferenceUtils.getString("download_audio_quality", DEFAULT_AUDIO_QUALITY),
            audioOnly = preferenceUtils.getBoolean("download_audio_only", false),
            subtitles = preferenceUtils.getBoolean("download_subtitles", false),
            subtitleLanguages = preferenceUtils.getList("download_subtitle_languages", listOf("en")),
            maxConcurrentDownloads = preferenceUtils.getInt("download_max_concurrent", DEFAULT_MAX_CONCURRENT_DOWNLOADS),
            pauseOnLowBattery = preferenceUtils.getBoolean("download_pause_low_battery", true),
            onlyWifi = preferenceUtils.getBoolean("download_only_wifi", false),
            notifications = preferenceUtils.getBoolean("download_notifications", true),
            vibration = preferenceUtils.getBoolean("download_vibration", true),
            autoStart = preferenceUtils.getBoolean("download_auto_start", true)
        )
    }

    /**
     * Load network settings from preferences
     */
    private fun loadNetworkSettings(): NetworkSettings {
        return NetworkSettings(
            timeoutSeconds = preferenceUtils.getInt("network_timeout", DEFAULT_NETWORK_TIMEOUT),
            maxRetries = preferenceUtils.getInt("network_max_retries", DEFAULT_MAX_RETRIES),
            useProxy = preferenceUtils.getBoolean("network_use_proxy", false),
            proxyHost = preferenceUtils.getString("network_proxy_host", ""),
            proxyPort = preferenceUtils.getInt("network_proxy_port", 8080),
            proxyUsername = preferenceUtils.getString("network_proxy_username", ""),
            proxyPassword = preferenceUtils.getEncryptedString("network_proxy_password", ""),
            userAgent = preferenceUtils.getString("network_user_agent", ""),
            adaptiveQuality = preferenceUtils.getBoolean("network_adaptive_quality", true),
            bandwidthLimit = preferenceUtils.getLong("network_bandwidth_limit", 0L)
        )
    }

    /**
     * Load UI settings from preferences
     */
    private fun loadUiSettings(): UiSettings {
        return UiSettings(
            theme = preferenceUtils.getString("ui_theme", DEFAULT_THEME),
            darkMode = preferenceUtils.getBoolean("ui_dark_mode", false),
            useSystemTheme = preferenceUtils.getBoolean("ui_use_system_theme", true),
            showThumbnails = preferenceUtils.getBoolean("ui_show_thumbnails", true),
            gridView = preferenceUtils.getBoolean("ui_grid_view", false),
            gridColumns = preferenceUtils.getInt("ui_grid_columns", 2),
            keepScreenOn = preferenceUtils.getBoolean("ui_keep_screen_on", false),
            showDownloadSpeed = preferenceUtils.getBoolean("ui_show_download_speed", true)
        )
    }

    /**
     * Save app settings to preferences
     */
    private fun saveAppSettings(settings: AppSettings) {
        preferenceUtils.putString("app_language", settings.language)
        preferenceUtils.putBoolean("app_auto_cleanup", settings.autoCleanup)
        preferenceUtils.putInt("app_cleanup_interval_days", settings.cleanupIntervalDays)
        preferenceUtils.putBoolean("app_analytics_enabled", settings.analyticsEnabled)
        preferenceUtils.putBoolean("app_crash_reporting_enabled", settings.crashReportingEnabled)
        preferenceUtils.putBoolean("app_automatic_updates", settings.automaticUpdates)
        preferenceUtils.putBoolean("app_background_downloads", settings.backgroundDownloads)
    }

    /**
     * Save download settings to preferences
     */
    private fun saveDownloadSettings(settings: DownloadSettings) {
        preferenceUtils.putString("download_location", settings.downloadLocation)
        preferenceUtils.putString("download_video_quality", settings.videoQuality)
        preferenceUtils.putString("download_audio_quality", settings.audioQuality)
        preferenceUtils.putBoolean("download_audio_only", settings.audioOnly)
        preferenceUtils.putBoolean("download_subtitles", settings.subtitles)
        preferenceUtils.putList("download_subtitle_languages", settings.subtitleLanguages)
        preferenceUtils.putInt("download_max_concurrent", settings.maxConcurrentDownloads)
        preferenceUtils.putBoolean("download_pause_low_battery", settings.pauseOnLowBattery)
        preferenceUtils.putBoolean("download_only_wifi", settings.onlyWifi)
        preferenceUtils.putBoolean("download_notifications", settings.notifications)
        preferenceUtils.putBoolean("download_vibration", settings.vibration)
        preferenceUtils.putBoolean("download_auto_start", settings.autoStart)
    }

    /**
     * Save network settings to preferences
     */
    private fun saveNetworkSettings(settings: NetworkSettings) {
        preferenceUtils.putInt("network_timeout", settings.timeoutSeconds)
        preferenceUtils.putInt("network_max_retries", settings.maxRetries)
        preferenceUtils.putBoolean("network_use_proxy", settings.useProxy)
        preferenceUtils.putString("network_proxy_host", settings.proxyHost)
        preferenceUtils.putInt("network_proxy_port", settings.proxyPort)
        preferenceUtils.putString("network_proxy_username", settings.proxyUsername)
        preferenceUtils.putEncryptedString("network_proxy_password", settings.proxyPassword)
        preferenceUtils.putString("network_user_agent", settings.userAgent)
        preferenceUtils.putBoolean("network_adaptive_quality", settings.adaptiveQuality)
        preferenceUtils.putLong("network_bandwidth_limit", settings.bandwidthLimit)
    }

    /**
     * Save UI settings to preferences
     */
    private fun saveUiSettings(settings: UiSettings) {
        preferenceUtils.putString("ui_theme", settings.theme)
        preferenceUtils.putBoolean("ui_dark_mode", settings.darkMode)
        preferenceUtils.putBoolean("ui_use_system_theme", settings.useSystemTheme)
        preferenceUtils.putBoolean("ui_show_thumbnails", settings.showThumbnails)
        preferenceUtils.putBoolean("ui_grid_view", settings.gridView)
        preferenceUtils.putInt("ui_grid_columns", settings.gridColumns)
        preferenceUtils.putBoolean("ui_keep_screen_on", settings.keepScreenOn)
        preferenceUtils.putBoolean("ui_show_download_speed", settings.showDownloadSpeed)
    }

    /**
     * Get default app settings
     */
    private fun getDefaultAppSettings(): AppSettings {
        return AppSettings(
            language = DEFAULT_LANGUAGE,
            autoCleanup = true,
            cleanupIntervalDays = 30,
            analyticsEnabled = false,
            crashReportingEnabled = true,
            automaticUpdates = true,
            backgroundDownloads = true
        )
    }

    /**
     * Get default download settings
     */
    private fun getDefaultDownloadSettings(): DownloadSettings {
        val defaultLocation = fileUtils.getDownloadsDirectory().absolutePath
        
        return DownloadSettings(
            downloadLocation = defaultLocation,
            videoQuality = DEFAULT_VIDEO_QUALITY,
            audioQuality = DEFAULT_AUDIO_QUALITY,
            audioOnly = false,
            subtitles = false,
            subtitleLanguages = listOf("en"),
            maxConcurrentDownloads = DEFAULT_MAX_CONCURRENT_DOWNLOADS,
            pauseOnLowBattery = true,
            onlyWifi = false,
            notifications = true,
            vibration = true,
            autoStart = true
        )
    }

    /**
     * Get default network settings
     */
    private fun getDefaultNetworkSettings(): NetworkSettings {
        return NetworkSettings(
            timeoutSeconds = DEFAULT_NETWORK_TIMEOUT,
            maxRetries = DEFAULT_MAX_RETRIES,
            useProxy = false,
            proxyHost = "",
            proxyPort = 8080,
            proxyUsername = "",
            proxyPassword = "",
            userAgent = "",
            adaptiveQuality = true,
            bandwidthLimit = 0L
        )
    }

    /**
     * Get default UI settings
     */
    private fun getDefaultUiSettings(): UiSettings {
        return UiSettings(
            theme = DEFAULT_THEME,
            darkMode = false,
            useSystemTheme = true,
            showThumbnails = true,
            gridView = false,
            gridColumns = 2,
            keepScreenOn = false,
            showDownloadSpeed = true
        )
    }

    /**
     * Data classes for settings
     */
    data class AppSettings(
        val language: String,
        val autoCleanup: Boolean,
        val cleanupIntervalDays: Int,
        val analyticsEnabled: Boolean,
        val crashReportingEnabled: Boolean,
        val automaticUpdates: Boolean,
        val backgroundDownloads: Boolean
    )

    data class DownloadSettings(
        val downloadLocation: String,
        val videoQuality: String,
        val audioQuality: String,
        val audioOnly: Boolean,
        val subtitles: Boolean,
        val subtitleLanguages: List<String>,
        val maxConcurrentDownloads: Int,
        val pauseOnLowBattery: Boolean,
        val onlyWifi: Boolean,
        val notifications: Boolean,
        val vibration: Boolean,
        val autoStart: Boolean
    )

    data class NetworkSettings(
        val timeoutSeconds: Int,
        val maxRetries: Int,
        val useProxy: Boolean,
        val proxyHost: String,
        val proxyPort: Int,
        val proxyUsername: String,
        val proxyPassword: String,
        val userAgent: String,
        val adaptiveQuality: Boolean,
        val bandwidthLimit: Long
    )

    data class UiSettings(
        val theme: String,
        val darkMode: Boolean,
        val useSystemTheme: Boolean,
        val showThumbnails: Boolean,
        val gridView: Boolean,
        val gridColumns: Int,
        val keepScreenOn: Boolean,
        val showDownloadSpeed: Boolean
    )

    data class AllSettings(
        val app: AppSettings,
        val download: DownloadSettings,
        val network: NetworkSettings,
        val ui: UiSettings
    )

    data class StorageUsage(
        val totalSpace: Long,
        val usedSpace: Long,
        val availableSpace: Long,
        val usagePercentage: Int
    )

    data class SettingsValidationError(
        val message: String
    )
}
