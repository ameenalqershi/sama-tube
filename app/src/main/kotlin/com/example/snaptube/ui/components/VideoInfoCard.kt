package com.example.snaptube.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.snaptube.R
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.models.VideoInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoInfoCard(
    videoInfo: VideoInfo,
    onDownloadClick: (DownloadFormat) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var showDownloadOptions by remember { mutableStateOf(false) }
    
    // Get video and audio formats
    val videoFormats = remember(videoInfo) { 
        videoInfo.formats.filter { it.hasVideo && !it.isAudioOnly() }
    }
    val audioFormats = remember(videoInfo) { 
        videoInfo.formats.filter { it.isAudioOnly() }
    }
    
    Card(
        modifier = modifier.animateContentSize(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Thumbnail
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(videoInfo.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title
            Text(
                text = videoInfo.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = if (expanded) Int.MAX_VALUE else 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Author and duration
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = videoInfo.uploader,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = videoInfo.getFormattedDuration(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Expand/Collapse Button
            if (videoInfo.description.isNotBlank()) {
                TextButton(
                    onClick = { expanded = !expanded },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (expanded) "Show Less" else "Show More")
                    Icon(
                        if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null
                    )
                }
                
                // Description (visible when expanded)
                if (expanded) {
                    Text(
                        text = videoInfo.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            
            // Download Buttons
            if (!showDownloadOptions) {
                // Main Download Button
                Button(
                    onClick = { showDownloadOptions = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.download))
                }
            } else {
                // Format Selection Section
                Text(
                    text = stringResource(R.string.download_options),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Video Format Option
                if (videoFormats.isNotEmpty()) {
                    val bestVideoFormat = videoFormats.first()
                    Card(
                        onClick = { onDownloadClick(bestVideoFormat) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.VideoFile,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(R.string.video),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "${bestVideoFormat.quality} • ${bestVideoFormat.getFormattedFileSize()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                                )
                            }
                            FilledIconButton(
                                onClick = { onDownloadClick(bestVideoFormat) },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(
                                    Icons.Default.Download,
                                    contentDescription = stringResource(R.string.download)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }
                
                // Audio Format Option
                if (audioFormats.isNotEmpty()) {
                    val bestAudioFormat = audioFormats.first()
                    Card(
                        onClick = { onDownloadClick(bestAudioFormat) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.AudioFile,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(R.string.audio),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                                Text(
                                    text = "${bestAudioFormat.quality} • ${bestAudioFormat.getFormattedFileSize()}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                                )
                            }
                            FilledIconButton(
                                onClick = { onDownloadClick(bestAudioFormat) },
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary
                                )
                            ) {
                                Icon(
                                    Icons.Default.Download,
                                    contentDescription = stringResource(R.string.download)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoInfoHeader(videoInfo: VideoInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // صورة مصغرة للفيديو
        AsyncImage(
            model = videoInfo.thumbnail,
            contentDescription = "صورة الفيديو",
            modifier = Modifier
                .size(80.dp)
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop
        )
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // عنوان الفيديو
            Text(
                text = videoInfo.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // اسم القناة
            Text(
                text = videoInfo.uploader,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // المنصة
            AssistChip(
                onClick = { },
                label = { Text(videoInfo.platform.replaceFirstChar { it.uppercaseChar() }) },
                leadingIcon = {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            )
        }
    }
}

@Composable
private fun VideoInfoStats(videoInfo: VideoInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem(
            label = "المدة",
            value = videoInfo.getFormattedDuration()
        )
        
        StatItem(
            label = "المشاهدات",
            value = formatViewCount(videoInfo.viewCount)
        )
        
        StatItem(
            label = "الحجم",
            value = formatFileSize(videoInfo.fileSize)
        )
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DownloadFormatItem(
    format: DownloadFormat,
    onDownloadClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${format.quality} - ${format.extension}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "الحجم: ${formatFileSize(format.fileSize ?: 0L)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Button(
                onClick = onDownloadClick,
                modifier = Modifier.height(36.dp)
            ) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("تحميل")
            }
        }
    }
}

// Helper functions
private fun formatViewCount(viewCount: Long): String {
    return when {
        viewCount > 1_000_000_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000_000_000)}B"
        viewCount > 1_000_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000_000)}M"
        viewCount > 1_000 -> "${"%.1f".format(viewCount.toDouble() / 1_000)}K"
        else -> viewCount.toString()
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
