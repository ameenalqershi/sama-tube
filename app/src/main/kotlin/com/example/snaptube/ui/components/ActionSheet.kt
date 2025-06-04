package com.example.snaptube.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snaptube.R
import com.example.snaptube.download.Task
import com.example.snaptube.download.Task.DownloadState.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionSheet(
    task: Task,
    viewState: Task.ViewState,
    downloadState: Task.DownloadState,
    onDismissRequest: () -> Unit,
    onActionPost: (Task, UiAction) -> Unit,
) {
    LazyColumn {
        item {
            ActionSheetTitle(
                imageModel = viewState.thumbnailUrl,
                title = viewState.title,
                author = viewState.uploader,
                downloadState = downloadState,
            )
        }

        item {
            ActionButtons(
                task = task,
                downloadState = downloadState,
                viewState = viewState,
                onDismissRequest = onDismissRequest,
                onActionPost = onActionPost,
            )
        }

        item { 
            ActionSheetInfo(task = task, viewState = viewState) 
        }
    }
}

@Composable
private fun ActionSheetTitle(
    imageModel: String?,
    title: String,
    author: String,
    downloadState: Task.DownloadState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageModel,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            if (author.isNotEmpty()) {
                Text(
                    text = author,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Status indicator
            Spacer(modifier = Modifier.height(4.dp))
            ActionSheetStateIndicator(downloadState = downloadState)
        }
    }
}

@Composable
private fun ActionSheetStateIndicator(
    downloadState: Task.DownloadState
) {
    val (text, color) = when (downloadState) {
        is Running -> {
            val progressText = if (downloadState.progress >= 0) {
                "${(downloadState.progress * 100).toInt()}%"
            } else "Processing..."
            progressText to MaterialTheme.colorScheme.primary
        }
        is FetchingInfo -> "Fetching info..." to MaterialTheme.colorScheme.primary
        is Error -> "Failed" to MaterialTheme.colorScheme.error
        is Canceled -> "Canceled" to MaterialTheme.colorScheme.onSurfaceVariant
        is Completed -> "Completed" to MaterialTheme.colorScheme.primary
        else -> "Ready" to MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = FontWeight.Medium
    )
}

@Composable
private fun ActionButtons(
    task: Task,
    downloadState: Task.DownloadState,
    viewState: Task.ViewState,
    onDismissRequest: () -> Unit,
    onActionPost: (Task, UiAction) -> Unit,
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    
    when (downloadState) {
        is Running -> {
            ActionButton(
                icon = Icons.Filled.Pause,
                label = "Pause",
                onClick = {
                    onActionPost(task, UiAction.Cancel)
                    onDismissRequest()
                }
            )
        }
        is Error -> {
            ActionButton(
                icon = Icons.Filled.Refresh,
                label = "Retry",
                onClick = {
                    onActionPost(task, UiAction.Resume)
                    onDismissRequest()
                }
            )
            ActionButton(
                icon = Icons.Outlined.BugReport,
                label = "Copy Error",
                onClick = {
                    onActionPost(task, UiAction.CopyErrorReport(downloadState.throwable))
                    onDismissRequest()
                }
            )
        }
        is Canceled -> {
            ActionButton(
                icon = Icons.Filled.PlayArrow,
                label = "Resume",
                onClick = {
                    onActionPost(task, UiAction.Resume)
                    onDismissRequest()
                }
            )
        }
        is Completed -> {
            downloadState.filePath?.let { filePath ->
                ActionButton(
                    icon = Icons.Filled.OpenInNew,
                    label = "Open",
                    onClick = {
                        onActionPost(task, UiAction.OpenFile(filePath))
                        onDismissRequest()
                    }
                )
                ActionButton(
                    icon = Icons.Filled.Share,
                    label = "Share",
                    onClick = {
                        onActionPost(task, UiAction.ShareFile(filePath))
                        onDismissRequest()
                    }
                )
            }
        }
        else -> {
            ActionButton(
                icon = Icons.Filled.Stop,
                label = "Cancel",
                onClick = {
                    onActionPost(task, UiAction.Cancel)
                    onDismissRequest()
                }
            )
        }
    }
    
    // Common actions
    ActionButton(
        icon = Icons.Outlined.ContentCopy,
        label = "Copy URL",
        onClick = {
            clipboardManager.setText(AnnotatedString(task.url))
            onDismissRequest()
        }
    )
    
    ActionButton(
        icon = Icons.Outlined.Delete,
        label = "Delete",
        onClick = {
            onActionPost(task, UiAction.Delete)
            onDismissRequest()
        }
    )
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActionSheetInfo(
    task: Task,
    viewState: Task.ViewState
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Download Information",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            InfoRow(label = "URL", value = task.url)
            
            if (viewState.extractorKey.isNotEmpty()) {
                InfoRow(label = "Platform", value = viewState.extractorKey)
            }
            
            if (viewState.duration > 0) {
                InfoRow(
                    label = "Duration", 
                    value = formatDuration(viewState.duration)
                )
            }
            
            if (viewState.fileSizeApprox > 0) {
                InfoRow(
                    label = "File Size", 
                    value = formatFileSize(viewState.fileSizeApprox)
                )
            }
            
            InfoRow(
                label = "Created", 
                value = formatTimestamp(task.timeCreated)
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(2f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Utility functions
private fun formatDuration(seconds: Int): String {
    val hours = seconds / 3600
    val minutes = (seconds % 3600) / 60
    val secs = seconds % 60
    
    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, secs)
    } else {
        String.format("%d:%02d", minutes, secs)
    }
}

private fun formatFileSize(bytes: Double): String {
    val kb = bytes / 1024
    val mb = kb / 1024
    val gb = mb / 1024
    
    return when {
        gb >= 1 -> String.format("%.1f GB", gb)
        mb >= 1 -> String.format("%.1f MB", mb)
        else -> String.format("%.0f KB", kb)
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    val minutes = diff / (60 * 1000)
    val hours = minutes / 60
    val days = hours / 24
    
    return when {
        days > 0 -> "${days}d ago"
        hours > 0 -> "${hours}h ago"
        minutes > 0 -> "${minutes}m ago"
        else -> "Just now"
    }
}
