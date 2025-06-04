package com.example.snaptube.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import timber.log.Timber

/**
 * Utility class for managing system permissions required by the Snaptube app
 * Handles storage, network, and notification permissions with proper Android version compatibility
 */
class PermissionUtils(private val context: Context) {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
        
        // Required permissions for different Android versions
        val STORAGE_PERMISSIONS_LEGACY = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        
        val STORAGE_PERMISSIONS_MODERN = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        
        val NOTIFICATION_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            emptyArray()
        }
        
        val NETWORK_PERMISSIONS = arrayOf(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE
        )
    }

    /**
     * Check if all storage permissions are granted
     */
    fun hasStoragePermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ - Check for MANAGE_EXTERNAL_STORAGE
            Environment.isExternalStorageManager()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 - Use scoped storage permissions
            STORAGE_PERMISSIONS_MODERN.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        } else {
            // Android 9 and below - Legacy storage permissions
            STORAGE_PERMISSIONS_LEGACY.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
    }

    /**
     * Check if notification permissions are granted (Android 13+)
     */
    fun hasNotificationPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            NOTIFICATION_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        } else {
            true // Not required on older versions
        }
    }

    /**
     * Check if network permissions are granted
     */
    fun hasNetworkPermissions(): Boolean {
        return NETWORK_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    /**
     * Check if all required permissions are granted
     */
    fun hasAllRequiredPermissions(): Boolean {
        return hasStoragePermissions() && hasNotificationPermissions() && hasNetworkPermissions()
    }

    /**
     * Get missing permissions that need to be requested
     */
    fun getMissingPermissions(): List<String> {
        val missingPermissions = mutableListOf<String>()
        
        // Check storage permissions
        if (!hasStoragePermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // MANAGE_EXTERNAL_STORAGE needs special handling
                missingPermissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                STORAGE_PERMISSIONS_MODERN.forEach { permission ->
                    if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        missingPermissions.add(permission)
                    }
                }
            } else {
                STORAGE_PERMISSIONS_LEGACY.forEach { permission ->
                    if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                        missingPermissions.add(permission)
                    }
                }
            }
        }
        
        // Check notification permissions
        if (!hasNotificationPermissions()) {
            NOTIFICATION_PERMISSIONS.forEach { permission ->
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission)
                }
            }
        }
        
        // Network permissions are usually granted by default, but check anyway
        NETWORK_PERMISSIONS.forEach { permission ->
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission)
            }
        }
        
        return missingPermissions
    }

    /**
     * Request storage permissions appropriate for the Android version
     */
    fun requestStoragePermissions(activity: ComponentActivity, callback: (Boolean) -> Unit) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                // Android 11+ - Request MANAGE_EXTERNAL_STORAGE
                requestManageExternalStoragePermission(activity, callback)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                // Android 6+ - Request runtime permissions
                val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    STORAGE_PERMISSIONS_MODERN
                } else {
                    STORAGE_PERMISSIONS_LEGACY
                }
                requestRuntimePermissions(activity, permissions, callback)
            }
            else -> {
                // Below Android 6 - Permissions granted at install time
                callback(true)
            }
        }
    }

    /**
     * Request notification permissions (Android 13+)
     */
    fun requestNotificationPermissions(activity: ComponentActivity, callback: (Boolean) -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestRuntimePermissions(activity, NOTIFICATION_PERMISSIONS, callback)
        } else {
            callback(true) // Not required on older versions
        }
    }

    /**
     * Request all required permissions
     */
    fun requestAllPermissions(activity: ComponentActivity, callback: (Boolean) -> Unit) {
        val missingPermissions = getMissingPermissions()
        
        if (missingPermissions.isEmpty()) {
            callback(true)
            return
        }
        
        // Handle MANAGE_EXTERNAL_STORAGE separately
        if (missingPermissions.contains(Manifest.permission.MANAGE_EXTERNAL_STORAGE)) {
            requestManageExternalStoragePermission(activity) { storageGranted ->
                if (storageGranted) {
                    // Request other permissions
                    val otherPermissions = missingPermissions.filter { 
                        it != Manifest.permission.MANAGE_EXTERNAL_STORAGE 
                    }.toTypedArray()
                    
                    if (otherPermissions.isNotEmpty()) {
                        requestRuntimePermissions(activity, otherPermissions, callback)
                    } else {
                        callback(true)
                    }
                } else {
                    callback(false)
                }
            }
        } else {
            // Request all as runtime permissions
            requestRuntimePermissions(activity, missingPermissions.toTypedArray(), callback)
        }
    }

    /**
     * Request MANAGE_EXTERNAL_STORAGE permission (Android 11+)
     */
    private fun requestManageExternalStoragePermission(
        activity: ComponentActivity, 
        callback: (Boolean) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                callback(true)
                return
            }
            
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                    data = Uri.parse("package:${context.packageName}")
                }
                
                val launcher = activity.registerForActivityResult(
                    ActivityResultContracts.StartActivityForResult()
                ) { _ ->
                    val granted = Environment.isExternalStorageManager()
                    Timber.d("MANAGE_EXTERNAL_STORAGE permission result: $granted")
                    callback(granted)
                }
                
                launcher.launch(intent)
            } catch (e: Exception) {
                Timber.e(e, "Failed to request MANAGE_EXTERNAL_STORAGE permission")
                callback(false)
            }
        } else {
            callback(true)
        }
    }

    /**
     * Request runtime permissions
     */
    private fun requestRuntimePermissions(
        activity: ComponentActivity,
        permissions: Array<String>,
        callback: (Boolean) -> Unit
    ) {
        if (permissions.isEmpty()) {
            callback(true)
            return
        }
        
        val launcher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { results ->
            val allGranted = results.values.all { it }
            Timber.d("Runtime permissions result: $results, all granted: $allGranted")
            callback(allGranted)
        }
        
        launcher.launch(permissions)
    }

    /**
     * Check if permission is permanently denied
     */
    fun isPermissionPermanentlyDenied(activity: ComponentActivity, permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            !activity.shouldShowRequestPermissionRationale(permission) &&
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
    }

    /**
     * Open app settings for manual permission management
     */
    fun openAppSettings(activity: ComponentActivity) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.parse("package:${context.packageName}")
            }
            activity.startActivity(intent)
        } catch (e: Exception) {
            Timber.e(e, "Failed to open app settings")
            // Fallback to general settings
            try {
                val fallbackIntent = Intent(Settings.ACTION_SETTINGS)
                activity.startActivity(fallbackIntent)
            } catch (fallbackException: Exception) {
                Timber.e(fallbackException, "Failed to open settings")
            }
        }
    }

    /**
     * Get permission status summary for debugging
     */
    fun getPermissionStatusSummary(): Map<String, Boolean> {
        val status = mutableMapOf<String, Boolean>()
        
        // Storage permissions
        status["hasStoragePermissions"] = hasStoragePermissions()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            status["MANAGE_EXTERNAL_STORAGE"] = Environment.isExternalStorageManager()
        } else {
            val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                STORAGE_PERMISSIONS_MODERN
            } else {
                STORAGE_PERMISSIONS_LEGACY
            }
            permissions.forEach { permission ->
                status[permission] = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        
        // Notification permissions
        status["hasNotificationPermissions"] = hasNotificationPermissions()
        NOTIFICATION_PERMISSIONS.forEach { permission ->
            status[permission] = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
        
        // Network permissions
        status["hasNetworkPermissions"] = hasNetworkPermissions()
        NETWORK_PERMISSIONS.forEach { permission ->
            status[permission] = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
        
        return status
    }

    /**
     * Log current permission status for debugging
     */
    fun logPermissionStatus() {
        val status = getPermissionStatusSummary()
        Timber.d("Permission Status Summary:")
        status.forEach { (permission, granted) ->
            Timber.d("  $permission: $granted")
        }
    }
}
