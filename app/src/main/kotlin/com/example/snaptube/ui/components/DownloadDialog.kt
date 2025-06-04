package com.example.snaptube.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onStartDownload: (String, DownloadConfig) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    var urlText by remember { mutableStateOf("") }
    var showAdvancedOptions by remember { mutableStateOf(false) }
    var isValidating by remember { mutableStateOf(false) }
    var validationError by remember { mutableStateOf<String?>(null) }
    
    // Download configuration state
    var downloadConfig by remember { mutableStateOf(DownloadConfig()) }
    
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(100)
            focusRequester.requestFocus()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Download Video",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                // URL Input Field
                OutlinedTextField(
                    value = urlText,
                    onValueChange = { 
                        urlText = it
                        validationError = null
                    },
                    label = { Text("Video URL") },
                    placeholder = { Text("Paste your video URL here...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = "URL"
                        )
                    },
                    trailingIcon = if (urlText.isNotEmpty()) {
                        {
                            IconButton(onClick = { urlText = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    } else null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                            if (urlText.isNotBlank()) {
                                // Validate and proceed
                                handleDownloadStart(
                                    url = urlText,
                                    config = downloadConfig,
                                    onStartDownload = onStartDownload,
                                    setValidating = { isValidating = it },
                                    setError = { validationError = it }
                                )
                            }
                        }
                    ),
                    isError = validationError != null,
                    supportingText = validationError?.let { { Text(it) } }
                )

                // Advanced Options Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Advanced Options",
                        style = MaterialTheme.typography.titleMedium
                    )
                    
                    Switch(
                        checked = showAdvancedOptions,
                        onCheckedChange = { showAdvancedOptions = it }
                    )
                }

                // Advanced Options Panel
                if (showAdvancedOptions) {
                    AdvancedOptionsPanel(
                        config = downloadConfig,
                        onConfigChange = { downloadConfig = it }
                    )
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    Button(
                        onClick = {
                            if (urlText.isNotBlank()) {
                                handleDownloadStart(
                                    url = urlText,
                                    config = downloadConfig,
                                    onStartDownload = onStartDownload,
                                    setValidating = { isValidating = it },
                                    setError = { validationError = it }
                                )
                            } else {
                                validationError = "Please enter a valid URL"
                            }
                        },
                        enabled = urlText.isNotBlank() && !isValidating
                    ) {
                        if (isValidating) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isValidating) "Validating..." else "Download")
                    }
                }
            }
        }
    }
}

@Composable
private fun AdvancedOptionsPanel(
    config: DownloadConfig,
    onConfigChange: (DownloadConfig) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Quality Selection
            Text(
                text = "Quality",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            
            QualitySelector(
                selectedQuality = config.quality,
                onQualitySelected = { quality ->
                    onConfigChange(config.copy(quality = quality))
                }
            )

            // Format Selection
            Text(
                text = "Format",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium
            )
            
            FormatSelector(
                selectedFormat = config.format,
                audioOnly = config.audioOnly,
                onFormatSelected = { format ->
                    onConfigChange(config.copy(format = format))
                },
                onAudioOnlyChanged = { audioOnly ->
                    onConfigChange(config.copy(audioOnly = audioOnly))
                }
            )

            // Additional Options
            AdvancedToggleOptions(
                config = config,
                onConfigChange = onConfigChange
            )
        }
    }
}

@Composable
private fun QualitySelector(
    selectedQuality: VideoQuality,
    onQualitySelected: (VideoQuality) -> Unit
) {
    val qualities = listOf(
        VideoQuality.BEST,
        VideoQuality.HIGH,
        VideoQuality.MEDIUM,
        VideoQuality.LOW
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        qualities.forEach { quality ->
            FilterChip(
                selected = selectedQuality == quality,
                onClick = { onQualitySelected(quality) },
                label = { Text(quality.displayName) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun FormatSelector(
    selectedFormat: VideoFormat,
    audioOnly: Boolean,
    onFormatSelected: (VideoFormat) -> Unit,
    onAudioOnlyChanged: (Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Audio Only Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Audio Only")
            Switch(
                checked = audioOnly,
                onCheckedChange = onAudioOnlyChanged
            )
        }

        // Format Selection
        if (!audioOnly) {
            val videoFormats = listOf(
                VideoFormat.MP4,
                VideoFormat.WEBM,
                VideoFormat.MKV
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                videoFormats.forEach { format ->
                    FilterChip(
                        selected = selectedFormat == format,
                        onClick = { onFormatSelected(format) },
                        label = { Text(format.extension.uppercase()) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            val audioFormats = listOf(
                VideoFormat.MP3,
                VideoFormat.M4A,
                VideoFormat.OGG
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                audioFormats.forEach { format ->
                    FilterChip(
                        selected = selectedFormat == format,
                        onClick = { onFormatSelected(format) },
                        label = { Text(format.extension.uppercase()) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AdvancedToggleOptions(
    config: DownloadConfig,
    onConfigChange: (DownloadConfig) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Embed Thumbnail
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Embed Thumbnail")
            Switch(
                checked = config.embedThumbnail,
                onCheckedChange = { embedThumbnail ->
                    onConfigChange(config.copy(embedThumbnail = embedThumbnail))
                }
            )
        }

        // Embed Metadata
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Embed Metadata")
            Switch(
                checked = config.embedMetadata,
                onCheckedChange = { embedMetadata ->
                    onConfigChange(config.copy(embedMetadata = embedMetadata))
                }
            )
        }

        // Download Subtitles
        if (!config.audioOnly) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Download Subtitles")
                Switch(
                    checked = config.downloadSubtitles,
                    onCheckedChange = { downloadSubtitles ->
                        onConfigChange(config.copy(downloadSubtitles = downloadSubtitles))
                    }
                )
            }
        }
    }
}

private fun handleDownloadStart(
    url: String,
    config: DownloadConfig,
    onStartDownload: (String, DownloadConfig) -> Unit,
    setValidating: (Boolean) -> Unit,
    setError: (String?) -> Unit
) {
    // Basic URL validation
    if (!isValidUrl(url)) {
        setError("Please enter a valid URL")
        return
    }

    setValidating(true)
    setError(null)

    // Start download
    onStartDownload(url, config)
    setValidating(false)
}

private fun isValidUrl(url: String): Boolean {
    return url.isNotBlank() && 
           (url.startsWith("http://") || url.startsWith("https://") || 
            url.contains("youtube.com") || url.contains("youtu.be") ||
            url.contains("instagram.com") || url.contains("tiktok.com") ||
            url.contains("facebook.com") || url.contains("twitter.com"))
}

// Data classes for download configuration
data class DownloadConfig(
    val quality: VideoQuality = VideoQuality.BEST,
    val format: VideoFormat = VideoFormat.MP4,
    val audioOnly: Boolean = false,
    val embedThumbnail: Boolean = true,
    val embedMetadata: Boolean = true,
    val downloadSubtitles: Boolean = false
)

enum class VideoQuality(val displayName: String) {
    BEST("Best"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}

enum class VideoFormat(val extension: String) {
    MP4("mp4"),
    WEBM("webm"),
    MKV("mkv"),
    MP3("mp3"),
    M4A("m4a"),
    OGG("ogg")
}
