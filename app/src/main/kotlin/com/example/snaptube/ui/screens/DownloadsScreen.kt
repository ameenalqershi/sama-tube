package com.example.snaptube.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FileDownloadDone
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.snaptube.R
import com.example.snaptube.download.DownloadTask
import com.example.snaptube.download.DownloadStatus
import com.example.snaptube.ui.viewmodels.DownloadsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadsScreen(
    viewModel: DownloadsViewModel = koinViewModel(),
    onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            LargeTopAppBar(
                title = {
        Text(
                        text = stringResource(R.string.downloads),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
        )
        
            // Download List
            if (uiState.downloads.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp), // For bottom nav bar
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = uiState.downloads,
                        key = { it.id }
                    ) { download ->
                        DownloadItem(
                            download = download,
                            onDeleteClick = { viewModel.deleteDownload(download.id) },
                            onItemClick = { 
                                if (download.status == DownloadStatus.COMPLETED) {
                                    viewModel.openFile(download.id)
                                }
                            }
                        )
                    }
                }
            } else {
                // Empty state
                EmptyDownloadsState()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DownloadItem(
    download: DownloadTask,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = onItemClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // We don't have thumbnailUrl in DownloadTask, so use generic icon instead
                Icon(
                    imageVector = if (download.audioOnly) Icons.Default.FileDownloadDone else Icons.Outlined.Download,
                    contentDescription = null,
                    modifier = Modifier
                        .size(width = 64.dp, height = 64.dp)
                        .padding(8.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    // Title
                    Text(
                        text = download.title,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    // Format & Size
                    Text(
                        text = "${download.downloadFormat.extension} • ${formatSize(download.totalSize)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Status Indicator
                    DownloadStatusIndicator(status = download.status, progress = download.progress)
                }
                
                // Delete Button
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
fun DownloadStatusIndicator(status: DownloadStatus, progress: Int = 0) {
    val (color, text) = when (status) {
        DownloadStatus.COMPLETED -> Pair(
            MaterialTheme.colorScheme.primary,
            stringResource(R.string.download_complete)
        )
        DownloadStatus.DOWNLOADING -> Pair(
            MaterialTheme.colorScheme.tertiary,
            "Downloading... $progress%"
        )
        DownloadStatus.FAILED -> Pair(
            MaterialTheme.colorScheme.error,
            stringResource(R.string.download_failed)
        )
        DownloadStatus.PENDING -> Pair(
            MaterialTheme.colorScheme.secondary,
            "Queued"
        )
        DownloadStatus.PAUSED -> Pair(
            MaterialTheme.colorScheme.outline,
            "Paused"
        )
        DownloadStatus.PROCESSING -> Pair(
            MaterialTheme.colorScheme.tertiary,
            "Processing..."
        )
        DownloadStatus.EXTRACTING -> Pair(
            MaterialTheme.colorScheme.tertiary,
            "Extracting..."
        )
        DownloadStatus.CANCELLED -> Pair(
            MaterialTheme.colorScheme.error,
            "Cancelled"
        )
                }
    
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (status == DownloadStatus.DOWNLOADING) {
            LinearProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier
                    .height(4.dp)
                    .width(100.dp),
                color = color,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$progress%",
                style = MaterialTheme.typography.labelMedium,
                color = color
            )
        } else {
            Icon(
                imageVector = if (status == DownloadStatus.COMPLETED) {
                    Icons.Default.FileDownloadDone
                } else {
                    Icons.Outlined.Download
                },
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = color
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = color
            )
        }
    }
}

@Composable
fun EmptyDownloadsState() {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                imageVector = Icons.Outlined.Download,
                            contentDescription = null,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
            
                        Spacer(modifier = Modifier.height(16.dp))
            
                        Text(
                text = stringResource(R.string.no_downloads),
                style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
// Helper function for formatting file size
private fun formatSize(bytes: Long): String {
    return when {
        bytes >= 1_073_741_824 -> "%.1f GB".format(bytes / 1_073_741_824.0)
        bytes >= 1_048_576 -> "%.1f MB".format(bytes / 1_048_576.0)
        bytes >= 1024 -> "%.1f KB".format(bytes / 1024.0)
        else -> "$bytes B"
    }
}
