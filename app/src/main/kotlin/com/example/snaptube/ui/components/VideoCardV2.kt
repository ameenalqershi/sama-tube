package com.example.snaptube.ui.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snaptube.R
import com.example.snaptube.download.Task
import com.example.snaptube.download.Task.DownloadState.*
import com.example.snaptube.ui.theme.SnaptubeTheme
import kotlinx.coroutines.delay

@Composable
fun VideoCardV2(
    modifier: Modifier = Modifier,
    viewState: Task.ViewState,
    actionButton: @Composable () -> Unit = {},
    stateIndicator: @Composable () -> Unit = {},
    onButtonClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onButtonClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            // Thumbnail and basic info
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Video thumbnail
                AsyncImage(
                    model = viewState.thumbnailUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                // Video info
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = viewState.title.ifEmpty { viewState.url },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    
                    if (viewState.uploader.isNotEmpty()) {
                        Text(
                            text = viewState.uploader,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    
                    // Duration and file size
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (viewState.duration > 0) {
                            Text(
                                text = formatDuration(viewState.duration),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (viewState.duration > 0 && viewState.fileSizeApprox > 0) {
                            Text(
                                text = " • ",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        
                        if (viewState.fileSizeApprox > 0) {
                            Text(
                                text = formatFileSize(viewState.fileSizeApprox),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                
                // Action button
                actionButton()
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // State indicator
            stateIndicator()
        }
    }
}

@Composable
fun VideoListItem(
    modifier: Modifier = Modifier,
    viewState: Task.ViewState,
    stateIndicator: @Composable () -> Unit = {},
    onButtonClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onButtonClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Compact thumbnail
            AsyncImage(
                model = viewState.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = viewState.title.ifEmpty { viewState.url },
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (viewState.uploader.isNotEmpty()) {
                    Text(
                        text = viewState.uploader,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // State indicator in compact mode
                stateIndicator()
            }
            
            IconButton(
                onClick = onButtonClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    downloadState: Task.DownloadState,
    onAction: (UiAction) -> Unit = {}
) {
    val context = LocalContext.current
    
    when (downloadState) {
        is Running -> {
            CircularProgressIndicator(
                modifier = modifier.size(24.dp),
                strokeWidth = 2.dp,
                progress = { if (downloadState.progress >= 0) downloadState.progress else 0f }
            )
        }
        is FetchingInfo -> {
            CircularProgressIndicator(
                modifier = modifier.size(24.dp),
                strokeWidth = 2.dp
            )
        }
        is Error -> {
            IconButton(
                onClick = { onAction(UiAction.Resume) },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = "Retry",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
        is Canceled -> {
            IconButton(
                onClick = { onAction(UiAction.Resume) },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Resume",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        is Completed -> {
            IconButton(
                onClick = { 
                    downloadState.filePath?.let { path ->
                        onAction(UiAction.OpenFile(path))
                    }
                },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = "Open file",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        else -> {
            IconButton(
                onClick = { onAction(UiAction.Cancel) },
                modifier = modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Pause,
                    contentDescription = "Pause",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CardStateIndicator(
    modifier: Modifier = Modifier,
    downloadState: Task.DownloadState
) {
    when (downloadState) {
        is Running -> {
            Column(modifier = modifier) {
                LinearProgressIndicator(
                    progress = { if (downloadState.progress >= 0) downloadState.progress else 0f },
                    modifier = Modifier.fillMaxWidth(),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (downloadState.progress >= 0) {
                            "${(downloadState.progress * 100).toInt()}%"
                        } else {
                            "Processing..."
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    if (downloadState.progressText.isNotEmpty()) {
                        Text(
                            text = downloadState.progressText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
        is FetchingInfo -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Fetching info...",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        is Error -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Failed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        is Canceled -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Canceled",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        is Completed -> {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        else -> {
            Text(
                text = "Ready",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListItemStateText(
    modifier: Modifier = Modifier,
    downloadState: Task.DownloadState
) {
    val stateText = when (downloadState) {
        is Running -> {
            if (downloadState.progress >= 0) {
                "${(downloadState.progress * 100).toInt()}% • ${downloadState.progressText}"
            } else {
                "Processing..."
            }
        }
        is FetchingInfo -> "Fetching info..."
        is Error -> "Failed"
        is Canceled -> "Canceled"
        is Completed -> "Completed"
        else -> "Ready"
    }
    
    val stateColor = when (downloadState) {
        is Running, is FetchingInfo -> MaterialTheme.colorScheme.primary
        is Error -> MaterialTheme.colorScheme.error
        is Completed -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Text(
        text = stateText,
        style = MaterialTheme.typography.bodySmall,
        color = stateColor,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
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
