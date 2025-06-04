package com.example.snaptube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.activity.result.contract.ActivityResultContracts
import timber.log.Timber
import com.example.snaptube.ui.navigation.SnaptubeNavigation
import com.example.snaptube.ui.theme.SnaptubeTheme
class MainActivity : ComponentActivity() {
        
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.WAKE_LOCK
        )
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val deniedPermissions = permissions.filterValues { !it }.keys
        if (deniedPermissions.isNotEmpty()) {
            Timber.w("بعض الصلاحيات مرفوضة: ${deniedPermissions.joinToString()}")
        } else {
            Timber.d("جميع الصلاحيات ممنوحة")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // طلب الصلاحيات المطلوبة
        requestPermissions()
        
        setContent {
            SnaptubeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SnaptubeNavigation()
                }
            }
        }
    }

    private fun requestPermissions() {
        val permissionsToRequest = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        
        if (permissionsToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}
