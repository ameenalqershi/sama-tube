package com.example.snaptube.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import timber.log.Timber

class PreferenceUtils(private val context: Context) {
    
    companion object {
        private const val PREFS_NAME = "snaptube_preferences"
        private const val ENCRYPTED_PREFS_NAME = "snaptube_encrypted_preferences"
        
        // General preferences
        const val KEY_FIRST_LAUNCH = "first_launch"
        const val KEY_APP_VERSION = "app_version"
        const val KEY_LAST_UPDATE_CHECK = "last_update_check"
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_LANGUAGE = "language"
        
        // Download preferences
        const val KEY_DEFAULT_DOWNLOAD_PATH = "default_download_path"
        const val KEY_DEFAULT_VIDEO_QUALITY = "default_video_quality"
        const val KEY_DEFAULT_AUDIO_QUALITY = "default_audio_quality"
        const val KEY_PREFER_VIDEO_FORMAT = "prefer_video_format"
        const val KEY_PREFER_AUDIO_FORMAT = "prefer_audio_format"
        const val KEY_AUTO_DOWNLOAD_THUMBNAILS = "auto_download_thumbnails"
        const val KEY_AUTO_DOWNLOAD_SUBTITLES = "auto_download_subtitles"
        const val KEY_SUBTITLE_LANGUAGE = "subtitle_language"
        const val KEY_MAX_CONCURRENT_DOWNLOADS = "max_concurrent_downloads"
        const val KEY_RETRY_FAILED_DOWNLOADS = "retry_failed_downloads"
        const val KEY_MAX_RETRY_ATTEMPTS = "max_retry_attempts"
        
        // Network preferences
        const val KEY_ALLOW_CELLULAR_DOWNLOADS = "allow_cellular_downloads"
        const val KEY_CELLULAR_QUALITY_LIMIT = "cellular_quality_limit"
        const val KEY_WIFI_ONLY_LARGE_FILES = "wifi_only_large_files"
        const val KEY_LARGE_FILE_THRESHOLD = "large_file_threshold"
        const val KEY_CONNECTION_TIMEOUT = "connection_timeout"
        const val KEY_READ_TIMEOUT = "read_timeout"
        
        // UI preferences
        const val KEY_SHOW_DOWNLOAD_PROGRESS = "show_download_progress"
        const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
        const val KEY_NOTIFICATION_SOUND = "notification_sound"
        const val KEY_NOTIFICATION_VIBRATION = "notification_vibration"
        const val KEY_SHOW_SPEED_IN_NOTIFICATION = "show_speed_in_notification"
        const val KEY_AUTO_HIDE_COMPLETED = "auto_hide_completed"
        const val KEY_CONFIRM_DELETE = "confirm_delete"
        
        // Privacy preferences
        const val KEY_CLEAR_HISTORY_ON_EXIT = "clear_history_on_exit"
        const val KEY_SAVE_DOWNLOAD_HISTORY = "save_download_history"
        const val KEY_ANALYTICS_ENABLED = "analytics_enabled"
        const val KEY_CRASH_REPORTING_ENABLED = "crash_reporting_enabled"
        
        // Performance preferences
        const val KEY_CACHE_SIZE_LIMIT = "cache_size_limit"
        const val KEY_AUTO_CLEANUP_CACHE = "auto_cleanup_cache"
        const val KEY_CLEANUP_INTERVAL_DAYS = "cleanup_interval_days"
        const val KEY_DATABASE_OPTIMIZATION = "database_optimization"
        
        // Advanced preferences
        const val KEY_DEVELOPER_MODE = "developer_mode"
        const val KEY_DEBUG_LOGGING = "debug_logging"
        const val KEY_CUSTOM_USER_AGENT = "custom_user_agent"
        const val KEY_PROXY_ENABLED = "proxy_enabled"
        const val KEY_PROXY_HOST = "proxy_host"
        const val KEY_PROXY_PORT = "proxy_port"
        
        // Default values
        const val DEFAULT_MAX_CONCURRENT_DOWNLOADS = 3
        const val DEFAULT_MAX_RETRY_ATTEMPTS = 3
        const val DEFAULT_LARGE_FILE_THRESHOLD = 100L * 1024 * 1024 // 100MB
        const val DEFAULT_CONNECTION_TIMEOUT = 30000 // 30 seconds
        const val DEFAULT_READ_TIMEOUT = 60000 // 60 seconds
        const val DEFAULT_CACHE_SIZE_LIMIT = 500L * 1024 * 1024 // 500MB
        const val DEFAULT_CLEANUP_INTERVAL_DAYS = 7
    }
    
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    // Changed from internal to private and added non-inline helper
    private val gson = Gson()
    
    // Changed visibility for inline functions  
    fun <T> fromJsonHelper(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }
    
    fun toJsonHelper(obj: Any): String {
        return gson.toJson(obj)
    }
    
    // Simplified preferences for sensitive data (can be enhanced later with proper encryption)
    private val encryptedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(ENCRYPTED_PREFS_NAME, Context.MODE_PRIVATE)
    }
    
    // Basic operations
    fun getString(key: String, defaultValue: String = ""): String {
        return prefs.getString(key, defaultValue) ?: defaultValue
    }
    
    fun getInt(key: String, defaultValue: Int = 0): Int {
        return prefs.getInt(key, defaultValue)
    }
    
    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return prefs.getLong(key, defaultValue)
    }
    
    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return prefs.getFloat(key, defaultValue)
    }
    
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }
    
    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
    
    fun putInt(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
    
    fun putLong(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }
    
    fun putFloat(key: String, value: Float) {
        prefs.edit().putFloat(key, value).apply()
    }
    
    fun putBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }
    
    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
    
    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }
    
    fun clear() {
        prefs.edit().clear().apply()
    }
    
    // Encrypted operations
    fun getEncryptedString(key: String, defaultValue: String = ""): String {
        return encryptedPrefs.getString(key, defaultValue) ?: defaultValue
    }
    
    fun putEncryptedString(key: String, value: String) {
        encryptedPrefs.edit().putString(key, value).apply()
    }
    
    fun removeEncrypted(key: String) {
        encryptedPrefs.edit().remove(key).apply()
    }
    
    fun clearEncrypted() {
        encryptedPrefs.edit().clear().apply()
    }
    
    // Object operations (JSON serialization)
    inline fun <reified T> getObject(key: String, defaultValue: T? = null): T? {
        val json = getString(key)
        return if (json.isNotEmpty()) {
            try {
                fromJsonHelper(json, T::class.java)
            } catch (e: Exception) {
                Timber.e(e, "Failed to deserialize object for key: $key")
                defaultValue
            }
        } else {
            defaultValue
        }
    }
    
    fun <T> putObject(key: String, value: T) {
        val json = toJsonHelper(value as Any)
        putString(key, json)
    }
    
    inline fun <reified T> getList(key: String, defaultValue: List<T> = emptyList()): List<T> {
        val json = getString(key)
        return if (json.isNotEmpty()) {
            try {
                val type = object : TypeToken<List<T>>() {}.type
                fromJsonListHelper(json, type) ?: defaultValue
            } catch (e: Exception) {
                Timber.e(e, "Failed to deserialize list for key: $key")
                defaultValue
            }
        } else {
            defaultValue
        }
    }
    
    // Helper for list deserialization
    fun <T> fromJsonListHelper(json: String, type: java.lang.reflect.Type): List<T>? {
        return gson.fromJson(json, type)
    }
    
    fun <T> putList(key: String, value: List<T>) {
        val json = gson.toJson(value)
        putString(key, json)
    }
    
    // Convenience methods for common preferences
    fun isFirstLaunch(): Boolean = getBoolean(KEY_FIRST_LAUNCH, true)
    fun setFirstLaunchCompleted() = putBoolean(KEY_FIRST_LAUNCH, false)
    
    fun getAppVersion(): String = getString(KEY_APP_VERSION)
    fun setAppVersion(version: String) = putString(KEY_APP_VERSION, version)
    
    fun getThemeMode(): String = getString(KEY_THEME_MODE, "system")
    fun setThemeMode(mode: String) = putString(KEY_THEME_MODE, mode)
    
    fun getLanguage(): String = getString(KEY_LANGUAGE, "ar")
    fun setLanguage(language: String) = putString(KEY_LANGUAGE, language)
    
    // Download preferences
    fun getDefaultDownloadPath(): String = getString(KEY_DEFAULT_DOWNLOAD_PATH)
    fun setDefaultDownloadPath(path: String) = putString(KEY_DEFAULT_DOWNLOAD_PATH, path)
    
    fun getDefaultVideoQuality(): String = getString(KEY_DEFAULT_VIDEO_QUALITY, "720p")
    fun setDefaultVideoQuality(quality: String) = putString(KEY_DEFAULT_VIDEO_QUALITY, quality)
    
    fun getDefaultAudioQuality(): String = getString(KEY_DEFAULT_AUDIO_QUALITY, "medium")
    fun setDefaultAudioQuality(quality: String) = putString(KEY_DEFAULT_AUDIO_QUALITY, quality)
    
    fun getPreferredVideoFormat(): String = getString(KEY_PREFER_VIDEO_FORMAT, "mp4")
    fun setPreferredVideoFormat(format: String) = putString(KEY_PREFER_VIDEO_FORMAT, format)
    
    fun getPreferredAudioFormat(): String = getString(KEY_PREFER_AUDIO_FORMAT, "m4a")
    fun setPreferredAudioFormat(format: String) = putString(KEY_PREFER_AUDIO_FORMAT, format)
    
    fun shouldAutoDownloadThumbnails(): Boolean = getBoolean(KEY_AUTO_DOWNLOAD_THUMBNAILS, true)
    fun setAutoDownloadThumbnails(enable: Boolean) = putBoolean(KEY_AUTO_DOWNLOAD_THUMBNAILS, enable)
    
    fun shouldAutoDownloadSubtitles(): Boolean = getBoolean(KEY_AUTO_DOWNLOAD_SUBTITLES, false)
    fun setAutoDownloadSubtitles(enable: Boolean) = putBoolean(KEY_AUTO_DOWNLOAD_SUBTITLES, enable)
    
    fun getSubtitleLanguage(): String = getString(KEY_SUBTITLE_LANGUAGE, "ar")
    fun setSubtitleLanguage(language: String) = putString(KEY_SUBTITLE_LANGUAGE, language)
    
    fun getMaxConcurrentDownloads(): Int = getInt(KEY_MAX_CONCURRENT_DOWNLOADS, DEFAULT_MAX_CONCURRENT_DOWNLOADS)
    fun setMaxConcurrentDownloads(count: Int) = putInt(KEY_MAX_CONCURRENT_DOWNLOADS, count)
    
    fun shouldRetryFailedDownloads(): Boolean = getBoolean(KEY_RETRY_FAILED_DOWNLOADS, true)
    fun setRetryFailedDownloads(enable: Boolean) = putBoolean(KEY_RETRY_FAILED_DOWNLOADS, enable)
    
    fun getMaxRetryAttempts(): Int = getInt(KEY_MAX_RETRY_ATTEMPTS, DEFAULT_MAX_RETRY_ATTEMPTS)
    fun setMaxRetryAttempts(attempts: Int) = putInt(KEY_MAX_RETRY_ATTEMPTS, attempts)
    
    // Network preferences
    fun isAllowCellularDownloads(): Boolean = getBoolean(KEY_ALLOW_CELLULAR_DOWNLOADS, false)
    fun setAllowCellularDownloads(allow: Boolean) = putBoolean(KEY_ALLOW_CELLULAR_DOWNLOADS, allow)
    
    fun getCellularQualityLimit(): String = getString(KEY_CELLULAR_QUALITY_LIMIT, "480p")
    fun setCellularQualityLimit(quality: String) = putString(KEY_CELLULAR_QUALITY_LIMIT, quality)
    
    fun isWifiOnlyLargeFiles(): Boolean = getBoolean(KEY_WIFI_ONLY_LARGE_FILES, true)
    fun setWifiOnlyLargeFiles(enable: Boolean) = putBoolean(KEY_WIFI_ONLY_LARGE_FILES, enable)
    
    fun getLargeFileThreshold(): Long = getLong(KEY_LARGE_FILE_THRESHOLD, DEFAULT_LARGE_FILE_THRESHOLD)
    fun setLargeFileThreshold(threshold: Long) = putLong(KEY_LARGE_FILE_THRESHOLD, threshold)
    
    fun getConnectionTimeout(): Int = getInt(KEY_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT)
    fun setConnectionTimeout(timeout: Int) = putInt(KEY_CONNECTION_TIMEOUT, timeout)
    
    fun getReadTimeout(): Int = getInt(KEY_READ_TIMEOUT, DEFAULT_READ_TIMEOUT)
    fun setReadTimeout(timeout: Int) = putInt(KEY_READ_TIMEOUT, timeout)
    
    // UI preferences
    fun shouldShowDownloadProgress(): Boolean = getBoolean(KEY_SHOW_DOWNLOAD_PROGRESS, true)
    fun setShowDownloadProgress(show: Boolean) = putBoolean(KEY_SHOW_DOWNLOAD_PROGRESS, show)
    
    fun isNotificationEnabled(): Boolean = getBoolean(KEY_NOTIFICATION_ENABLED, true)
    fun setNotificationEnabled(enable: Boolean) = putBoolean(KEY_NOTIFICATION_ENABLED, enable)
    
    fun isNotificationSoundEnabled(): Boolean = getBoolean(KEY_NOTIFICATION_SOUND, true)
    fun setNotificationSoundEnabled(enable: Boolean) = putBoolean(KEY_NOTIFICATION_SOUND, enable)
    
    fun isNotificationVibrationEnabled(): Boolean = getBoolean(KEY_NOTIFICATION_VIBRATION, true)
    fun setNotificationVibrationEnabled(enable: Boolean) = putBoolean(KEY_NOTIFICATION_VIBRATION, enable)
    
    fun shouldShowSpeedInNotification(): Boolean = getBoolean(KEY_SHOW_SPEED_IN_NOTIFICATION, true)
    fun setShowSpeedInNotification(show: Boolean) = putBoolean(KEY_SHOW_SPEED_IN_NOTIFICATION, show)
    
    fun shouldAutoHideCompleted(): Boolean = getBoolean(KEY_AUTO_HIDE_COMPLETED, false)
    fun setAutoHideCompleted(enable: Boolean) = putBoolean(KEY_AUTO_HIDE_COMPLETED, enable)
    
    fun shouldConfirmDelete(): Boolean = getBoolean(KEY_CONFIRM_DELETE, true)
    fun setConfirmDelete(confirm: Boolean) = putBoolean(KEY_CONFIRM_DELETE, confirm)
    
    // Privacy preferences
    fun shouldClearHistoryOnExit(): Boolean = getBoolean(KEY_CLEAR_HISTORY_ON_EXIT, false)
    fun setClearHistoryOnExit(enable: Boolean) = putBoolean(KEY_CLEAR_HISTORY_ON_EXIT, enable)
    
    fun shouldSaveDownloadHistory(): Boolean = getBoolean(KEY_SAVE_DOWNLOAD_HISTORY, true)
    fun setSaveDownloadHistory(save: Boolean) = putBoolean(KEY_SAVE_DOWNLOAD_HISTORY, save)
    
    fun isAnalyticsEnabled(): Boolean = getBoolean(KEY_ANALYTICS_ENABLED, true)
    fun setAnalyticsEnabled(enable: Boolean) = putBoolean(KEY_ANALYTICS_ENABLED, enable)
    
    fun isCrashReportingEnabled(): Boolean = getBoolean(KEY_CRASH_REPORTING_ENABLED, true)
    fun setCrashReportingEnabled(enable: Boolean) = putBoolean(KEY_CRASH_REPORTING_ENABLED, enable)
    
    // Performance preferences
    fun getCacheSizeLimit(): Long = getLong(KEY_CACHE_SIZE_LIMIT, DEFAULT_CACHE_SIZE_LIMIT)
    fun setCacheSizeLimit(limit: Long) = putLong(KEY_CACHE_SIZE_LIMIT, limit)
    
    fun shouldAutoCleanupCache(): Boolean = getBoolean(KEY_AUTO_CLEANUP_CACHE, true)
    fun setAutoCleanupCache(enable: Boolean) = putBoolean(KEY_AUTO_CLEANUP_CACHE, enable)
    
    fun getCleanupIntervalDays(): Int = getInt(KEY_CLEANUP_INTERVAL_DAYS, DEFAULT_CLEANUP_INTERVAL_DAYS)
    fun setCleanupIntervalDays(days: Int) = putInt(KEY_CLEANUP_INTERVAL_DAYS, days)
    
    fun isDatabaseOptimizationEnabled(): Boolean = getBoolean(KEY_DATABASE_OPTIMIZATION, true)
    fun setDatabaseOptimizationEnabled(enable: Boolean) = putBoolean(KEY_DATABASE_OPTIMIZATION, enable)
    
    // Advanced preferences
    fun isDeveloperModeEnabled(): Boolean = getBoolean(KEY_DEVELOPER_MODE, false)
    fun setDeveloperModeEnabled(enable: Boolean) = putBoolean(KEY_DEVELOPER_MODE, enable)
    
    fun isDebugLoggingEnabled(): Boolean = getBoolean(KEY_DEBUG_LOGGING, false)
    fun setDebugLoggingEnabled(enable: Boolean) = putBoolean(KEY_DEBUG_LOGGING, enable)
    
    fun getCustomUserAgent(): String = getString(KEY_CUSTOM_USER_AGENT)
    fun setCustomUserAgent(userAgent: String) = putString(KEY_CUSTOM_USER_AGENT, userAgent)
    
    fun isProxyEnabled(): Boolean = getBoolean(KEY_PROXY_ENABLED, false)
    fun setProxyEnabled(enable: Boolean) = putBoolean(KEY_PROXY_ENABLED, enable)
    
    fun getProxyHost(): String = getEncryptedString(KEY_PROXY_HOST)
    fun setProxyHost(host: String) = putEncryptedString(KEY_PROXY_HOST, host)
    
    fun getProxyPort(): Int = getInt(KEY_PROXY_PORT, 8080)
    fun setProxyPort(port: Int) = putInt(KEY_PROXY_PORT, port)
    
    // Utility methods
    fun exportPreferences(): Map<String, Any?> {
        return prefs.all
    }
    
    fun importPreferences(preferences: Map<String, Any?>) {
        val editor = prefs.edit()
        preferences.forEach { (key, value) ->
            when (value) {
                is String -> editor.putString(key, value)
                is Int -> editor.putInt(key, value)
                is Long -> editor.putLong(key, value)
                is Float -> editor.putFloat(key, value)
                is Boolean -> editor.putBoolean(key, value)
                else -> Timber.w("Unknown preference type for key: $key")
            }
        }
        editor.apply()
    }
    
    fun resetToDefaults() {
        clear()
        clearEncrypted()
        Timber.i("All preferences reset to defaults")
    }
    
    fun getPreferenceSummary(): Map<String, Any> {
        return mapOf(
            "theme" to getThemeMode(),
            "language" to getLanguage(),
            "default_quality" to getDefaultVideoQuality(),
            "concurrent_downloads" to getMaxConcurrentDownloads(),
            "cellular_allowed" to isAllowCellularDownloads(),
            "notifications_enabled" to isNotificationEnabled(),
            "analytics_enabled" to isAnalyticsEnabled(),
            "cache_size_mb" to getCacheSizeLimit() / (1024 * 1024),
            "developer_mode" to isDeveloperModeEnabled()
        )
    }
}
