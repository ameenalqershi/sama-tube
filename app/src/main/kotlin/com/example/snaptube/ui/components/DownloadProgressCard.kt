package com.example.snaptube.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snaptube.download.DownloadTask
import com.example.snaptube.download.DownloadStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadProgressCard(
    downloadTask: DownloadTask,
    onPauseClick: (String) -> Unit,
    onResumeClick: (String) -> Unit,
    onCancelClick: (String) -> Unit,
    onRetryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // معلومات التحميل
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // صورة مصغرة - placeholder لحين إضافة thumbnail للـ DownloadTask
                Card(
                    modifier = Modifier
                        .size(60.dp)
                        .aspectRatio(16f / 9f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = downloadTask.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = downloadTask.downloadFormat.quality,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // حالة التحميل
                    AssistChip(
                        onClick = { },
                        label = { Text(getStatusDisplayName(downloadTask.status)) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = getStatusColor(downloadTask.status)
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // شريط التقدم والمعلومات
            if (downloadTask.status != DownloadStatus.FAILED) {
                LinearProgressIndicator(
                    progress = downloadTask.progress / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${downloadTask.progress}%",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    Text(
                        text = "${formatFileSize(downloadTask.downloadedBytes)} / ${formatFileSize(downloadTask.totalSize)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                if (downloadTask.speed > 0) {
                    Text(
                        text = "السرعة: ${formatSpeed(downloadTask.speed)} - المتبقي: ${formatTime(downloadTask.getEstimatedTimeRemaining())}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                // عرض رسالة الخطأ
                Text(
                    text = downloadTask.errorMessage.ifEmpty { "حدث خطأ غير محدد" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // أزرار التحكم
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (downloadTask.status) {
                    DownloadStatus.DOWNLOADING -> {
                        OutlinedButton(
                            onClick = { onPauseClick(downloadTask.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Pause, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إيقاف")
                        }
                    }
                    
                    DownloadStatus.PAUSED -> {
                        Button(
                            onClick = { onResumeClick(downloadTask.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("استكمال")
                        }
                    }
                    
                    DownloadStatus.FAILED -> {
                        Button(
                            onClick = { onRetryClick(downloadTask.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إعادة المحاولة")
                        }
                    }
                    
                    DownloadStatus.COMPLETED -> {
                        // لا توجد أزرار للتحميلات المكتملة
                    }
                    
                    else -> {
                        // للحالات الأخرى (PENDING, PROCESSING, EXTRACTING)
                        OutlinedButton(
                            onClick = { onCancelClick(downloadTask.id) },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("إلغاء")
                        }
                    }
                }
                
                // زر الإلغاء متاح دائماً (إلا للمكتملة)
                if (downloadTask.status != DownloadStatus.COMPLETED) {
                    OutlinedButton(
                        onClick = { onCancelClick(downloadTask.id) },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.Cancel, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun getStatusColor(status: DownloadStatus): androidx.compose.ui.graphics.Color {
    return when (status) {
        DownloadStatus.DOWNLOADING -> MaterialTheme.colorScheme.primary
        DownloadStatus.COMPLETED -> MaterialTheme.colorScheme.primary
        DownloadStatus.FAILED -> MaterialTheme.colorScheme.error
        DownloadStatus.PAUSED -> MaterialTheme.colorScheme.secondary
        DownloadStatus.CANCELLED -> MaterialTheme.colorScheme.outline
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}

private fun getStatusDisplayName(status: DownloadStatus): String {
    return when (status) {
        DownloadStatus.PENDING -> "في الانتظار"
        DownloadStatus.DOWNLOADING -> "يتم التحميل"
        DownloadStatus.PROCESSING -> "جاري المعالجة"
        DownloadStatus.EXTRACTING -> "جاري الاستخراج"
        DownloadStatus.COMPLETED -> "مكتمل"
        DownloadStatus.FAILED -> "فشل"
        DownloadStatus.CANCELLED -> "ملغي"
        DownloadStatus.PAUSED -> "متوقف"
    }
}

private fun formatSpeed(speed: Long): String {
    return when {
        speed > 1024 * 1024 -> "${"%.1f".format(speed.toDouble() / (1024 * 1024))} MB/s"
        speed > 1024 -> "${"%.1f".format(speed.toDouble() / 1024)} KB/s"
        else -> "$speed B/s"
    }
}

private fun formatTime(seconds: Long): String {
    if (seconds <= 0) return "غير محدد"
    
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    
    return when {
        hours > 0 -> "%d:%02d:%02d".format(hours, minutes, secs)
        else -> "%d:%02d".format(minutes, secs)
    }
}

private fun formatFileSize(fileSize: Long): String {
    return when {
        fileSize > 1024 * 1024 * 1024 -> "${"%.1f".format(fileSize.toDouble() / (1024 * 1024 * 1024))} GB"
        fileSize > 1024 * 1024 -> "${"%.1f".format(fileSize.toDouble() / (1024 * 1024))} MB"
        fileSize > 1024 -> "${"%.1f".format(fileSize.toDouble() / 1024)} KB"
        else -> "$fileSize B"
    }
}
