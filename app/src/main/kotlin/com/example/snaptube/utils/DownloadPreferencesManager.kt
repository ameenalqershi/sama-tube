package com.example.snaptube.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.snaptube.download.DownloadPreferences

class DownloadPreferencesManager(
    private val context: Context
) {
    companion object {
        private const val PREF_NAME = "download_preferences"
        
        // Keys for shared preferences
        private const val KEY_DEFAULT_FORMAT = "default_format"
        private const val KEY_AUDIO_ONLY = "audio_only"
        private const val KEY_EXTRACT_AUDIO = "extract_audio"
        private const val KEY_AUDIO_FORMAT = "audio_format"
        private const val KEY_VIDEO_FORMAT = "video_format"
        private const val KEY_DOWNLOAD_LOCATION = "download_location"
        private const val KEY_RESTRICT_FILENAMES = "restrict_filenames"
        private const val KEY_EMBED_METADATA = "embed_metadata"
        private const val KEY_EMBED_THUMBNAIL = "embed_thumbnail"
        private const val KEY_EMBED_SUBS = "embed_subs"
        private const val KEY_CONCURRENT_DOWNLOADS = "concurrent_downloads"
        private const val KEY_MAX_RETRIES = "max_retries"
        private const val KEY_FRAGMENT_RETRIES = "fragment_retries"
        private const val KEY_SPONSOR_BLOCK = "sponsor_block"
        private const val KEY_PRIVATE_MODE = "private_mode"
        private const val KEY_NOTIFICATIONS_ENABLED = "notifications_enabled"
        private const val KEY_NOTIFICATION_SOUND = "notification_sound"
        private const val KEY_AUTO_DELETE_COMPLETED = "auto_delete_completed"
        private const val KEY_WIFI_ONLY = "wifi_only"
        private const val KEY_BATTERY_OPTIMIZATION = "battery_optimization"
        
        // Default values
        private const val DEFAULT_AUDIO_FORMAT = "mp3"
        private const val DEFAULT_VIDEO_FORMAT = "mp4"
        private const val DEFAULT_CONCURRENT_DOWNLOADS = 3
        private const val DEFAULT_MAX_RETRIES = 10
        private const val DEFAULT_FRAGMENT_RETRIES = 10
    }

    private val sharedPrefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /**
     * Get current download preferences
     */
    fun getDownloadPreferences(): DownloadPreferences {
        return DownloadPreferences(
            formatId = sharedPrefs.getString(KEY_DEFAULT_FORMAT, "") ?: "",
            audioOnly = sharedPrefs.getBoolean(KEY_AUDIO_ONLY, false),
            extractAudio = sharedPrefs.getBoolean(KEY_EXTRACT_AUDIO, false),
            audioFormat = sharedPrefs.getString(KEY_AUDIO_FORMAT, DEFAULT_AUDIO_FORMAT) ?: DEFAULT_AUDIO_FORMAT,
            videoFormat = sharedPrefs.getString(KEY_VIDEO_FORMAT, DEFAULT_VIDEO_FORMAT) ?: DEFAULT_VIDEO_FORMAT,
            downloadLocation = sharedPrefs.getString(KEY_DOWNLOAD_LOCATION, getDefaultDownloadLocation()) ?: getDefaultDownloadLocation(),
            restrictFilenames = sharedPrefs.getBoolean(KEY_RESTRICT_FILENAMES, true),
            embedMetadata = sharedPrefs.getBoolean(KEY_EMBED_METADATA, true),
            embedThumbnail = sharedPrefs.getBoolean(KEY_EMBED_THUMBNAIL, false),
            embedSubs = sharedPrefs.getBoolean(KEY_EMBED_SUBS, false),
            sponsorBlock = sharedPrefs.getBoolean(KEY_SPONSOR_BLOCK, false),
            privateMode = sharedPrefs.getBoolean(KEY_PRIVATE_MODE, false),
            retries = sharedPrefs.getInt(KEY_MAX_RETRIES, DEFAULT_MAX_RETRIES),
            fragmentRetries = sharedPrefs.getInt(KEY_FRAGMENT_RETRIES, DEFAULT_FRAGMENT_RETRIES),
        )
    }

    /**
     * Save download preferences
     */
    fun saveDownloadPreferences(preferences: DownloadPreferences) {
        sharedPrefs.edit().apply {
            putString(KEY_DEFAULT_FORMAT, preferences.formatId)
            putBoolean(KEY_AUDIO_ONLY, preferences.audioOnly)
            putBoolean(KEY_EXTRACT_AUDIO, preferences.extractAudio)
            putString(KEY_AUDIO_FORMAT, preferences.audioFormat)
            putString(KEY_VIDEO_FORMAT, preferences.videoFormat)
            putString(KEY_DOWNLOAD_LOCATION, preferences.downloadLocation)
            putBoolean(KEY_RESTRICT_FILENAMES, preferences.restrictFilenames)
            putBoolean(KEY_EMBED_METADATA, preferences.embedMetadata)
            putBoolean(KEY_EMBED_THUMBNAIL, preferences.embedThumbnail)
            putBoolean(KEY_EMBED_SUBS, preferences.embedSubs)
            putBoolean(KEY_SPONSOR_BLOCK, preferences.sponsorBlock)
            putBoolean(KEY_PRIVATE_MODE, preferences.privateMode)
            putInt(KEY_MAX_RETRIES, preferences.retries)
            putInt(KEY_FRAGMENT_RETRIES, preferences.fragmentRetries)
            apply()
        }
    }

    /**
     * Get maximum concurrent downloads
     */
    fun getMaxConcurrentDownloads(): Int {
        return sharedPrefs.getInt(KEY_CONCURRENT_DOWNLOADS, DEFAULT_CONCURRENT_DOWNLOADS)
    }

    /**
     * Set maximum concurrent downloads
     */
    fun setMaxConcurrentDownloads(count: Int) {
        sharedPrefs.edit().putInt(KEY_CONCURRENT_DOWNLOADS, count).apply()
    }

    /**
     * Check if notifications are enabled
     */
    fun areNotificationsEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_NOTIFICATIONS_ENABLED, true)
    }

    /**
     * Set notifications enabled state
     */
    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply()
    }

    /**
     * Check if notification sound is enabled
     */
    fun isNotificationSoundEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_NOTIFICATION_SOUND, true)
    }

    /**
     * Set notification sound enabled state
     */
    fun setNotificationSoundEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_NOTIFICATION_SOUND, enabled).apply()
    }

    /**
     * Check if auto-delete completed downloads is enabled
     */
    fun isAutoDeleteCompletedEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_AUTO_DELETE_COMPLETED, false)
    }

    /**
     * Set auto-delete completed downloads state
     */
    fun setAutoDeleteCompletedEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_AUTO_DELETE_COMPLETED, enabled).apply()
    }

    /**
     * Check if WiFi-only downloads is enabled
     */
    fun isWifiOnlyEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_WIFI_ONLY, false)
    }

    /**
     * Set WiFi-only downloads state
     */
    fun setWifiOnlyEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_WIFI_ONLY, enabled).apply()
    }

    /**
     * Check if battery optimization is enabled
     */
    fun isBatteryOptimizationEnabled(): Boolean {
        return sharedPrefs.getBoolean(KEY_BATTERY_OPTIMIZATION, true)
    }

    /**
     * Set battery optimization state
     */
    fun setBatteryOptimizationEnabled(enabled: Boolean) {
        sharedPrefs.edit().putBoolean(KEY_BATTERY_OPTIMIZATION, enabled).apply()
    }

    /**
     * Get default download location
     */
    fun getDefaultDownloadLocation(): String {
        return context.getExternalFilesDir("Downloads")?.absolutePath 
            ?: "${context.filesDir}/Downloads"
    }

    /**
     * Reset all preferences to default values
     */
    fun resetToDefaults() {
        sharedPrefs.edit().clear().apply()
    }
}
