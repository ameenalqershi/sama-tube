package com.example.snaptube.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import java.io.IOException
import java.net.*
import java.util.regex.Pattern

class NetworkUtils(private val context: Context) {
    
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    
    companion object {
        private const val PING_TIMEOUT = 3000 // 3 seconds
        private const val YOUTUBE_HOST = "www.youtube.com"
        private const val GOOGLE_DNS = "8.8.8.8"
        
        // URL validation patterns
        private val YOUTUBE_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?(m\\.)?(youtube\\.com|youtu\\.be)/(watch\\?v=|embed/|v/)?([a-zA-Z0-9_-]{11}).*"
        )
        
        private val TWITTER_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?(twitter\\.com|x\\.com)/[a-zA-Z0-9_]+/status/[0-9]+.*"
        )
        
        private val INSTAGRAM_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?instagram\\.com/(p|reel|tv)/[a-zA-Z0-9_-]+.*"
        )
        
        private val TIKTOK_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?(tiktok\\.com|vm\\.tiktok\\.com)/.*"
        )
        
        private val FACEBOOK_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?facebook\\.com/.*/(videos?|watch)/.*"
        )
        
        private val VIMEO_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?vimeo\\.com/[0-9]+.*"
        )
        
        private val DAILYMOTION_PATTERN = Pattern.compile(
            "^(https?)?(://)?(www\\.)?dailymotion\\.com/video/[a-zA-Z0-9]+.*"
        )
    }
    
    // Network connectivity checks
    fun isNetworkAvailable(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } catch (e: Exception) {
            Timber.e(e, "Error checking network availability")
            false
        }
    }
    
    fun isWifiConnected(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } catch (e: Exception) {
            Timber.e(e, "Error checking WiFi connection")
            false
        }
    }
    
    fun isMobileDataConnected(): Boolean {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } catch (e: Exception) {
            Timber.e(e, "Error checking mobile data connection")
            false
        }
    }
    
    fun isMeteredConnection(): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.isActiveNetworkMetered
            } else {
                isMobileDataConnected()
            }
        } catch (e: Exception) {
            Timber.e(e, "Error checking metered connection")
            true // Default to metered for safety
        }
    }
    
    fun getNetworkType(): NetworkType {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return NetworkType.NONE
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return NetworkType.NONE
            
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.MOBILE
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> NetworkType.ETHERNET
                else -> NetworkType.OTHER
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting network type")
            NetworkType.NONE
        }
    }
    
    fun getNetworkSpeed(): NetworkSpeed {
        return try {
            val activeNetwork = connectivityManager.activeNetwork ?: return NetworkSpeed.UNKNOWN
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return NetworkSpeed.UNKNOWN
            
            when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    // For WiFi, assume high speed but could be improved with actual speed test
                    NetworkSpeed.HIGH
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    // Try to determine cellular speed based on capabilities
                    when {
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && 
                        networkCapabilities.linkDownstreamBandwidthKbps > 10000 -> NetworkSpeed.HIGH
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && 
                        networkCapabilities.linkDownstreamBandwidthKbps > 2000 -> NetworkSpeed.MEDIUM
                        else -> NetworkSpeed.LOW
                    }
                }
                else -> NetworkSpeed.MEDIUM
            }
        } catch (e: Exception) {
            Timber.e(e, "Error getting network speed")
            NetworkSpeed.UNKNOWN
        }
    }
    
    // Network monitoring
    fun observeNetworkState(): Flow<NetworkState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(NetworkState.Available(getNetworkType(), isMeteredConnection()))
            }
            
            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(NetworkState.Lost)
            }
            
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                trySend(NetworkState.Available(getNetworkType(), isMeteredConnection()))
            }
        }
        
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(request, callback)
        
        // Send initial state
        if (isNetworkAvailable()) {
            trySend(NetworkState.Available(getNetworkType(), isMeteredConnection()))
        } else {
            trySend(NetworkState.Lost)
        }
        
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }
    
    /**
     * Register a callback for network connectivity changes.
     * @param onConnectivityChanged lambda receiving true when connected, false when disconnected
     */
    fun registerNetworkCallback(onConnectivityChanged: (Boolean) -> Unit) {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onConnectivityChanged(true)
            }
            override fun onLost(network: Network) {
                onConnectivityChanged(false)
            }
        })
    }
    
    // Connectivity tests
    suspend fun pingHost(host: String = GOOGLE_DNS, timeoutMs: Int = PING_TIMEOUT): Boolean {
        return try {
            val address = InetAddress.getByName(host)
            address.isReachable(timeoutMs)
        } catch (e: Exception) {
            Timber.d("Ping to $host failed: ${e.message}")
            false
        }
    }
    
    suspend fun testInternetConnectivity(): Boolean {
        if (!isNetworkAvailable()) return false
        
        return try {
            val socket = Socket()
            val socketAddress = InetSocketAddress(GOOGLE_DNS, 53)
            socket.connect(socketAddress, PING_TIMEOUT)
            socket.close()
            true
        } catch (e: Exception) {
            Timber.d("Internet connectivity test failed: ${e.message}")
            false
        }
    }
    
    suspend fun testVideoHostConnectivity(host: String = YOUTUBE_HOST): Boolean {
        return try {
            val url = URL("https://$host")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            connection.connectTimeout = PING_TIMEOUT
            connection.readTimeout = PING_TIMEOUT
            
            val responseCode = connection.responseCode
            connection.disconnect()
            
            responseCode in 200..299
        } catch (e: Exception) {
            Timber.d("Video host connectivity test failed for $host: ${e.message}")
            false
        }
    }
    
    // URL validation and platform detection
    fun isValidUrl(url: String): Boolean {
        return try {
            URL(url)
            true
        } catch (e: MalformedURLException) {
            false
        }
    }
    
    fun isSupportedVideoUrl(url: String): Boolean {
        return isYouTubeUrl(url) || 
               isTwitterUrl(url) || 
               isInstagramUrl(url) || 
               isTikTokUrl(url) || 
               isFacebookUrl(url) || 
               isVimeoUrl(url) || 
               isDailymotionUrl(url)
    }
    
    fun isYouTubeUrl(url: String): Boolean = YOUTUBE_PATTERN.matcher(url).matches()
    
    fun isTwitterUrl(url: String): Boolean = TWITTER_PATTERN.matcher(url).matches()
    
    fun isInstagramUrl(url: String): Boolean = INSTAGRAM_PATTERN.matcher(url).matches()
    
    fun isTikTokUrl(url: String): Boolean = TIKTOK_PATTERN.matcher(url).matches()
    
    fun isFacebookUrl(url: String): Boolean = FACEBOOK_PATTERN.matcher(url).matches()
    
    fun isVimeoUrl(url: String): Boolean = VIMEO_PATTERN.matcher(url).matches()
    
    fun isDailymotionUrl(url: String): Boolean = DAILYMOTION_PATTERN.matcher(url).matches()
    
    fun detectPlatform(url: String): String {
        return when {
            isYouTubeUrl(url) -> "youtube"
            isTwitterUrl(url) -> "twitter"
            isInstagramUrl(url) -> "instagram"
            isTikTokUrl(url) -> "tiktok"
            isFacebookUrl(url) -> "facebook"
            isVimeoUrl(url) -> "vimeo"
            isDailymotionUrl(url) -> "dailymotion"
            else -> "unknown"
        }
    }
    
    // URL processing
    fun normalizeUrl(url: String): String {
        var normalized = url.trim()
        
        // Add protocol if missing
        if (!normalized.startsWith("http://") && !normalized.startsWith("https://")) {
            normalized = "https://$normalized"
        }
        
        // Remove unnecessary parameters for certain platforms
        when (detectPlatform(normalized)) {
            "youtube" -> {
                // Extract video ID and create clean URL
                val videoIdRegex = Regex("[?&]v=([a-zA-Z0-9_-]{11})")
                val match = videoIdRegex.find(normalized)
                if (match != null) {
                    val videoId = match.groupValues[1]
                    normalized = "https://www.youtube.com/watch?v=$videoId"
                }
            }
            "twitter" -> {
                // Remove tracking parameters
                normalized = normalized.split("?")[0]
            }
        }
        
        return normalized
    }
    
    fun extractVideoId(url: String): String? {
        return when (detectPlatform(url)) {
            "youtube" -> {
                val regex = Regex("[?&]v=([a-zA-Z0-9_-]{11})")
                regex.find(url)?.groupValues?.get(1)
            }
            "twitter" -> {
                val regex = Regex("/status/([0-9]+)")
                regex.find(url)?.groupValues?.get(1)
            }
            "instagram" -> {
                val regex = Regex("/(p|reel|tv)/([a-zA-Z0-9_-]+)")
                regex.find(url)?.groupValues?.get(2)
            }
            "vimeo" -> {
                val regex = Regex("/([0-9]+)")
                regex.find(url)?.groupValues?.get(1)
            }
            else -> null
        }
    }
    
    // Network quality assessment
    fun getDownloadRecommendation(): DownloadRecommendation {
        val networkType = getNetworkType()
        val isMetered = isMeteredConnection()
        val networkSpeed = getNetworkSpeed()
        
        return when {
            networkType == NetworkType.NONE -> DownloadRecommendation.NO_NETWORK
            !isNetworkAvailable() -> DownloadRecommendation.NO_INTERNET
            isMetered && networkSpeed == NetworkSpeed.LOW -> DownloadRecommendation.METERED_LOW_QUALITY
            isMetered && networkSpeed == NetworkSpeed.MEDIUM -> DownloadRecommendation.METERED_MEDIUM_QUALITY
            isMetered && networkSpeed == NetworkSpeed.HIGH -> DownloadRecommendation.METERED_HIGH_QUALITY
            networkSpeed == NetworkSpeed.LOW -> DownloadRecommendation.LOW_QUALITY
            networkSpeed == NetworkSpeed.MEDIUM -> DownloadRecommendation.MEDIUM_QUALITY
            networkSpeed == NetworkSpeed.HIGH -> DownloadRecommendation.HIGH_QUALITY
            else -> DownloadRecommendation.MEDIUM_QUALITY
        }
    }
    
    // Data classes and enums
    enum class NetworkType {
        NONE, WIFI, MOBILE, ETHERNET, OTHER
    }
    
    enum class NetworkSpeed {
        UNKNOWN, LOW, MEDIUM, HIGH
    }
    
    sealed class NetworkState {
        data class Available(val type: NetworkType, val isMetered: Boolean) : NetworkState()
        object Lost : NetworkState()
    }
    
    enum class DownloadRecommendation {
        NO_NETWORK,
        NO_INTERNET,
        METERED_LOW_QUALITY,
        METERED_MEDIUM_QUALITY,
        METERED_HIGH_QUALITY,
        LOW_QUALITY,
        MEDIUM_QUALITY,
        HIGH_QUALITY
    }
    
    // Utility functions
    fun getNetworkStateDescription(): String {
        return when {
            !isNetworkAvailable() -> "لا يوجد اتصال بالإنترنت"
            isWifiConnected() -> "متصل بالواي فاي"
            isMobileDataConnected() -> "متصل ببيانات الهاتف"
            else -> "متصل بالإنترنت"
        }
    }
    
    fun shouldWarnAboutMeteredConnection(): Boolean {
        return isMeteredConnection() && isMobileDataConnected()
    }
    
    fun getRecommendedQualityForNetwork(): String {
        return when (getDownloadRecommendation()) {
            DownloadRecommendation.HIGH_QUALITY -> "1080p"
            DownloadRecommendation.MEDIUM_QUALITY -> "720p"
            DownloadRecommendation.LOW_QUALITY -> "480p"
            DownloadRecommendation.METERED_HIGH_QUALITY -> "720p"
            DownloadRecommendation.METERED_MEDIUM_QUALITY -> "480p"
            DownloadRecommendation.METERED_LOW_QUALITY -> "360p"
            else -> "480p"
        }
    }
}
