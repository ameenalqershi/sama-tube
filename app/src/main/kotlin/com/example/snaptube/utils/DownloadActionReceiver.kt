package com.example.snaptube.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.example.snaptube.download.TaskDownloaderV2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.File

class DownloadActionReceiver : BroadcastReceiver(), KoinComponent {

    private val taskDownloader: TaskDownloaderV2 by inject()

    companion object {
        const val ACTION_CANCEL = "com.example.snaptube.ACTION_CANCEL"
        const val ACTION_PAUSE = "com.example.snaptube.ACTION_PAUSE"
        const val ACTION_RETRY = "com.example.snaptube.ACTION_RETRY"
        const val ACTION_OPEN_FILE = "com.example.snaptube.ACTION_OPEN_FILE"
        const val ACTION_SHARE_FILE = "com.example.snaptube.ACTION_SHARE_FILE"
        
        const val EXTRA_TASK_ID = "task_id"
        const val EXTRA_FILE_PATH = "file_path"
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_CANCEL -> {
                val taskId = intent.getStringExtra(EXTRA_TASK_ID) ?: return
                CoroutineScope(Dispatchers.IO).launch {
                    taskDownloader.cancelTask(taskId)
                }
            }
            
            ACTION_PAUSE -> {
                val taskId = intent.getStringExtra(EXTRA_TASK_ID) ?: return
                CoroutineScope(Dispatchers.IO).launch {
                    taskDownloader.pauseTask(taskId)
                }
            }
            
            ACTION_RETRY -> {
                val taskId = intent.getStringExtra(EXTRA_TASK_ID) ?: return
                CoroutineScope(Dispatchers.IO).launch {
                    taskDownloader.retryTask(taskId)
                }
            }
            
            ACTION_OPEN_FILE -> {
                val filePath = intent.getStringExtra(EXTRA_FILE_PATH) ?: return
                openFile(context, filePath)
            }
            
            ACTION_SHARE_FILE -> {
                val filePath = intent.getStringExtra(EXTRA_FILE_PATH) ?: return
                shareFile(context, filePath)
            }
        }
    }

    private fun openFile(context: Context, filePath: String) {
        try {
            val file = File(filePath)
            if (!file.exists()) return

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val mimeType = getMimeType(filePath) ?: "*/*"
            
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimeType)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            context.startActivity(Intent.createChooser(intent, "Open with"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun shareFile(context: Context, filePath: String) {
        try {
            val file = File(filePath)
            if (!file.exists()) return

            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val mimeType = getMimeType(filePath) ?: "*/*"
            
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = mimeType
                putExtra(Intent.EXTRA_STREAM, uri)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            context.startActivity(Intent.createChooser(intent, "Share"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getMimeType(filePath: String): String? {
        val extension = File(filePath).extension.lowercase()
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
}
