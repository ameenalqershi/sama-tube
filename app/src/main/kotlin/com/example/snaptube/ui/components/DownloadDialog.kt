package com.example.snaptube.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Sheet States matching Seal's implementation
sealed interface SheetState {
    data object InputUrl : SheetState
    data class Configure(val urlList: List<String>) : SheetState
    data class Loading(val taskKey: String) : SheetState
    data class Error(val action: Action, val throwable: Throwable) : SheetState
}

// Actions matching Seal's implementation
sealed interface Action {
    data object HideSheet : Action
    data class ShowSheet(val urlList: List<String>? = null) : Action
    data class ProceedWithURLs(val urlList: List<String>) : Action
    data class FetchInfo(val url: String, val preferences: DialogPreferences) : Action
    data class Download(val urlList: List<String>, val preferences: DialogPreferences) : Action
    data class StartTask(val url: String, val preferences: DialogPreferences) : Action
    data object Reset : Action
}

// Action Button types matching Seal
enum class ActionButton {
    FetchInfo, Download, StartTask
}

// Dialog-specific preferences that map to real DownloadPreferences
data class DialogPreferences(
    val quality: VideoQuality = VideoQuality.BEST,
    val audioQuality: AudioQuality = AudioQuality.Q192K,
    val format: VideoFormat = VideoFormat.MP4,
    val audioOnly: Boolean = false,
    val embedThumbnail: Boolean = true,
    val embedMetadata: Boolean = true,
    val downloadSubtitles: Boolean = false,
    val customCommand: String = "",
    val useAria2: Boolean = false,
    val maxConnections: Int = 8,
    val splitSize: String = "10M"
) {
    // Convert to real DownloadPreferences for TaskDownloaderV2
    fun toDownloadPreferences(): com.example.snaptube.download.DownloadPreferences {
        return com.example.snaptube.download.DownloadPreferences(
            formatId = getFormatId(),
            audioOnly = audioOnly,
            extractAudio = audioOnly,
            audioFormat = if (audioOnly) format.extension else "mp3",
            videoFormat = if (!audioOnly) format.extension else "mp4",
            embedSubs = downloadSubtitles,
            embedThumbnail = embedThumbnail,
            embedMetadata = embedMetadata,
            aria2c = useAria2,
            concurrentConnections = if (useAria2) maxConnections else 1,
        )
    }
    
    private fun getFormatId(): String {
        return if (audioOnly) {
            when (audioQuality) {
                AudioQuality.Q64K -> "bestaudio[abr<=64]"
                AudioQuality.Q128K -> "bestaudio[abr<=128]"
                AudioQuality.Q192K -> "bestaudio[abr<=192]"
                AudioQuality.Q256K -> "bestaudio[abr<=256]"
                AudioQuality.Q320K -> "bestaudio[abr<=320]"
                AudioQuality.BEST -> "bestaudio"
            }
        } else {
            when (quality) {
                VideoQuality.Q144P -> "bestvideo[height<=144]+bestaudio/best[height<=144]"
                VideoQuality.Q240P -> "bestvideo[height<=240]+bestaudio/best[height<=240]"
                VideoQuality.Q360P -> "bestvideo[height<=360]+bestaudio/best[height<=360]"
                VideoQuality.Q480P -> "bestvideo[height<=480]+bestaudio/best[height<=480]"
                VideoQuality.Q720P -> "bestvideo[height<=720]+bestaudio/best[height<=720]"
                VideoQuality.Q1080P -> "bestvideo[height<=1080]+bestaudio/best[height<=1080]"
                VideoQuality.Q1440P -> "bestvideo[height<=1440]+bestaudio/best[height<=1440]"
                VideoQuality.Q2160P -> "bestvideo[height<=2160]+bestaudio/best[height<=2160]"
                VideoQuality.BEST -> "bestvideo+bestaudio/best"
                VideoQuality.HIGH -> "bestvideo[height<=1080]+bestaudio/best[height<=1080]"
                VideoQuality.MEDIUM -> "bestvideo[height<=720]+bestaudio/best[height<=720]"
                VideoQuality.LOW -> "bestvideo[height<=480]+bestaudio/best[height<=480]"
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onStartDownload: (String, DialogPreferences) -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isVisible) return

    var sheetState by remember { mutableStateOf<SheetState>(SheetState.InputUrl) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }

    LaunchedEffect(bottomSheetState.targetValue) {
        if (bottomSheetState.targetValue == SheetValue.Hidden) {
            onDismiss()
        }
    }

    // Handle Loading state transition for FetchInfo
    LaunchedEffect(sheetState) {
        val currentState = sheetState
        if (currentState is SheetState.Loading) {
            delay(1000) // Simulate fetch delay
            val url = currentState.taskKey
            sheetState = SheetState.Configure(listOf(url))
            println("🔍 DownloadDialog: State transitioned from Loading to Configure with URL: $url")
        }
    }

    val onActionPost = { action: Action ->
        when (action) {
            is Action.HideSheet -> {
                onDismiss()
            }
            is Action.ProceedWithURLs -> {
                sheetState = SheetState.Configure(action.urlList)
            }
            is Action.FetchInfo -> {
                println("🔍 DownloadDialog: FetchInfo action triggered for URL: ${action.url}")
                // Transition to Loading state first, then to Configure after a delay
                sheetState = SheetState.Loading(taskKey = action.url)
                println("🔍 DownloadDialog: State changed to Loading with URL: ${action.url}")
            }
            is Action.Download -> {
                // Start download for all URLs with the same preferences
                action.urlList.forEach { url ->
                    onStartDownload(url, action.preferences)
                }
                onDismiss()
            }
            is Action.StartTask -> {
                // Start single task download
                onStartDownload(action.url, action.preferences)
                onDismiss()
            }
            is Action.Reset -> {
                sheetState = SheetState.InputUrl
            }
            else -> {}
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState,
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        dragHandle = {
            Surface(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 32.dp, height = 4.dp)
                )
            }
        }
    ) {
        DownloadDialogContent(
            state = sheetState,
            onActionPost = onActionPost,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
private fun DownloadDialogContent(
    modifier: Modifier = Modifier,
    state: SheetState,
    onActionPost: (Action) -> Unit
) {
    AnimatedContent(
        modifier = modifier,
        targetState = state,
        label = "DownloadDialogContent",
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { it / 3 },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + fadeIn(animationSpec = tween(300)) togetherWith 
            slideOutHorizontally(
                targetOffsetX = { -it / 3 },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ) + fadeOut(animationSpec = tween(300))
        }
    ) { currentState ->
        when (currentState) {
            is SheetState.InputUrl -> {
                InputUrlPage(
                    onActionPost = onActionPost,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is SheetState.Configure -> {
                ConfigurePage(
                    urlList = currentState.urlList,
                    onActionPost = onActionPost,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is SheetState.Loading -> {
                LoadingPage(
                    taskKey = currentState.taskKey,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            is SheetState.Error -> {
                ErrorPage(
                    state = currentState,
                    onActionPost = onActionPost,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun InputUrlPage(
    modifier: Modifier = Modifier,
    onActionPost: (Action) -> Unit
) {
    var url by remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
        
        // Check clipboard for URLs
        clipboardManager.getText()?.let { clipText ->
            val text = clipText.toString()
            if (text.startsWith("http://") || text.startsWith("https://")) {
                // Don't auto-fill, just make it available as suggestion
            }
        }
    }

    Column(modifier = modifier) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "New Task",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // URL Input Field
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            label = { Text("Video URL") },
            placeholder = { Text("Paste your video URL here...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Link,
                    contentDescription = "URL",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = if (url.isNotEmpty()) {
                {
                    IconButton(onClick = { url = "" }) {
                        Icon(
                            imageVector = Icons.Outlined.Clear,
                            contentDescription = "Clear",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    if (url.isNotBlank()) {
                        onActionPost(Action.ProceedWithURLs(listOf(url)))
                    }
                }
            ),
            maxLines = 3,
            shape = RoundedCornerShape(12.dp)
        )

        // Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                SuggestionChip(
                    onClick = {
                        clipboardManager.getText()?.let { clipText ->
                            val text = clipText.toString()
                            if (text.startsWith("http://") || text.startsWith("https://")) {
                                url = text
                            }
                        }
                    },
                    label = { Text("Paste") },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.ContentPaste,
                            contentDescription = null,
                            modifier = Modifier.size(SuggestionChipDefaults.IconSize)
                        )
                    }
                )
            }
            
            // Quick URL suggestions
            item {
                SuggestionChip(
                    onClick = { 
                        url = "https://www.youtube.com/watch?v=" 
                    },
                    label = { Text("YouTube") },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.VideoLibrary,
                            contentDescription = null,
                            modifier = Modifier.size(SuggestionChipDefaults.IconSize)
                        )
                    }
                )
            }
            
            item {
                SuggestionChip(
                    onClick = { 
                        url = "https://www.instagram.com/p/" 
                    },
                    label = { Text("Instagram") },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Camera,
                            contentDescription = null,
                            modifier = Modifier.size(SuggestionChipDefaults.IconSize)
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = { onActionPost(Action.HideSheet) },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancel")
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Button(
                onClick = {
                    if (url.isNotBlank()) {
                        onActionPost(Action.ProceedWithURLs(listOf(url)))
                    }
                },
                enabled = url.isNotBlank(),
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Proceed")
            }
        }
    }
}

@Composable
private fun ConfigurePage(
    modifier: Modifier = Modifier,
    urlList: List<String>,
    onActionPost: (Action) -> Unit
) {
    var preferences by remember { mutableStateOf(DialogPreferences()) }
    var selectedDownloadType by remember { mutableStateOf(DownloadType.Auto) }
    var useFormatSelection by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.SettingsSuggest,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Configure Download",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // URL Display with video info preview
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Link,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Video URL",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = urlList.first(),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                // Detected platform info
                val detectedPlatform = remember(urlList.first()) {
                    when {
                        urlList.first().contains("youtube.com") || urlList.first().contains("youtu.be") -> "YouTube"
                        urlList.first().contains("instagram.com") -> "Instagram"
                        urlList.first().contains("tiktok.com") -> "TikTok"
                        urlList.first().contains("twitter.com") || urlList.first().contains("x.com") -> "Twitter/X"
                        else -> "Generic"
                    }
                }
                
                if (detectedPlatform != "Generic") {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Detected: $detectedPlatform",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Download Type Selection
        Text(
            text = "Download Type",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(DownloadType.values()) { type ->
                FilterChip(
                    selected = selectedDownloadType == type,
                    onClick = { selectedDownloadType = type },
                    label = { Text(type.displayName) }
                )
            }
        }

        // Format Selection Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Format Selection",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (useFormatSelection) "Custom" else "Auto",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(
                checked = useFormatSelection,
                onCheckedChange = { useFormatSelection = it }
            )
        }

        // Quality and Format Options
        if (useFormatSelection) {
            QualityFormatSelector(
                preferences = preferences,
                onPreferencesUpdate = { preferences = it },
                downloadType = selectedDownloadType
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons matching Seal's implementation
        ActionButtons(
            canProceed = urlList.isNotEmpty(),
            selectedType = selectedDownloadType,
            useFormatSelection = useFormatSelection,
            onCancel = { onActionPost(Action.HideSheet) },
            onFetchInfo = {
                onActionPost(Action.FetchInfo(urlList.first(), preferences))
            },
            onDownload = {
                onActionPost(Action.Download(urlList, preferences))
            },
            onStartTask = {
                onActionPost(Action.StartTask(urlList.first(), preferences))
            }
        )
    }
}

@Composable
private fun QualityFormatSelector(
    modifier: Modifier = Modifier,
    preferences: DialogPreferences,
    onPreferencesUpdate: (DialogPreferences) -> Unit,
    downloadType: DownloadType
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Audio Only Toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Audio Only")
            Switch(
                checked = preferences.audioOnly,
                onCheckedChange = { audioOnly ->
                    onPreferencesUpdate(preferences.copy(audioOnly = audioOnly))
                }
            )
        }

        // Quality Selection
        Text(
            text = if (preferences.audioOnly) "Audio Quality" else "Video Quality",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (preferences.audioOnly) {
                // Audio quality options based on bitrate
                val audioQualities = listOf(
                    AudioQuality.Q64K,
                    AudioQuality.Q128K,
                    AudioQuality.Q192K,
                    AudioQuality.Q256K,
                    AudioQuality.Q320K,
                    AudioQuality.BEST
                )
                
                items(audioQualities) { quality ->
                    FilterChip(
                        selected = preferences.audioQuality == quality,
                        onClick = { 
                            onPreferencesUpdate(preferences.copy(audioQuality = quality))
                        },
                        label = { Text(quality.displayName) }
                    )
                }
            } else {
                // Video quality options
                items(VideoQuality.values()) { quality ->
                    FilterChip(
                        selected = preferences.quality == quality,
                        onClick = { 
                            onPreferencesUpdate(preferences.copy(quality = quality))
                        },
                        label = { Text(quality.displayName) }
                    )
                }
            }
        }

        // Format Selection
        Text(
            text = if (preferences.audioOnly) "Audio Format" else "Video Format",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium
        )

        val formats = if (preferences.audioOnly) {
            // All audio formats from VideoUtils.AUDIO_FORMAT_PRIORITY
            listOf(
                VideoFormat.M4A,    // Priority 10
                VideoFormat.AAC,    // Priority 9  
                VideoFormat.MP3,    // Priority 8
                VideoFormat.OPUS,   // Priority 7
                VideoFormat.OGG,    // Priority 6
                VideoFormat.FLAC,   // Priority 5
                VideoFormat.WAV,    // Priority 4
                VideoFormat.WMA     // Priority 2
            )
        } else {
            // All video formats from VideoUtils.FORMAT_PRIORITY
            listOf(
                VideoFormat.MP4,        // Priority 10
                VideoFormat.MKV,        // Priority 8
                VideoFormat.WEBM,       // Priority 6
                VideoFormat.AVI,        // Priority 4
                VideoFormat.MOV,        // Priority 3
                VideoFormat.WMV,        // Priority 2
                VideoFormat.FLV,        // Priority 1
                VideoFormat.THREE_GP    // Priority 0
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(formats) { format ->
                FilterChip(
                    selected = preferences.format == format,
                    onClick = { 
                        onPreferencesUpdate(preferences.copy(format = format))
                    },
                    label = { Text(format.extension.uppercase()) }
                )
            }
        }

        // Additional Options
        AdditionalOptions(
            preferences = preferences,
            onPreferencesUpdate = onPreferencesUpdate
        )
    }
}

@Composable
private fun AdditionalOptions(
    preferences: DialogPreferences,
    onPreferencesUpdate: (DialogPreferences) -> Unit
) {
    var showAdvancedOptions by remember { mutableStateOf(false) }
    
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Expandable Header for Additional Options
        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showAdvancedOptions = !showAdvancedOptions }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Additional Options",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = if (showAdvancedOptions) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (showAdvancedOptions) "Collapse" else "Expand"
                )
            }
        }

        // Animated visibility for advanced options
        androidx.compose.animation.AnimatedVisibility(
            visible = showAdvancedOptions,
            enter = androidx.compose.animation.fadeIn() + androidx.compose.animation.expandVertically(),
            exit = androidx.compose.animation.fadeOut() + androidx.compose.animation.shrinkVertically()
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Basic Options Section
                    Text(
                        text = "Basic Settings",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Embed Thumbnail
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Embed Thumbnail")
                            Text(
                                text = "Include video thumbnail in file",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = preferences.embedThumbnail,
                            onCheckedChange = { embedThumbnail ->
                                onPreferencesUpdate(preferences.copy(embedThumbnail = embedThumbnail))
                            }
                        )
                    }

                    // Embed Metadata
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Embed Metadata")
                            Text(
                                text = "Include title, description, etc.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = preferences.embedMetadata,
                            onCheckedChange = { embedMetadata ->
                                onPreferencesUpdate(preferences.copy(embedMetadata = embedMetadata))
                            }
                        )
                    }

                    // Download Subtitles
                    if (!preferences.audioOnly) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Download Subtitles")
                                Text(
                                    text = "Download available subtitle files",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = preferences.downloadSubtitles,
                                onCheckedChange = { downloadSubtitles ->
                                    onPreferencesUpdate(preferences.copy(downloadSubtitles = downloadSubtitles))
                                }
                            )
                        }
                    }

                    HorizontalDivider()

                    // Advanced Settings Section
                    Text(
                        text = "Advanced Settings",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    // Use Aria2
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Use Aria2")
                            Text(
                                text = "Multi-connection download acceleration",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = preferences.useAria2,
                            onCheckedChange = { useAria2 ->
                                onPreferencesUpdate(preferences.copy(useAria2 = useAria2))
                            }
                        )
                    }

                    // Max Connections (if Aria2 is enabled)
                    if (preferences.useAria2) {
                        Column {
                            Text("Max Connections: ${preferences.maxConnections}")
                            Slider(
                                value = preferences.maxConnections.toFloat(),
                                onValueChange = { value ->
                                    onPreferencesUpdate(preferences.copy(maxConnections = value.toInt()))
                                },
                                valueRange = 1f..16f,
                                steps = 15
                            )
                            Text(
                                text = "Number of simultaneous connections (1-16)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Custom Command
                    var showCustomCommand by remember { mutableStateOf(preferences.customCommand.isNotEmpty()) }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Custom Command")
                            Text(
                                text = "Use custom yt-dlp parameters",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = showCustomCommand,
                            onCheckedChange = { enabled ->
                                showCustomCommand = enabled
                                if (!enabled) {
                                    onPreferencesUpdate(preferences.copy(customCommand = ""))
                                }
                            }
                        )
                    }

                    if (showCustomCommand) {
                        OutlinedTextField(
                            value = preferences.customCommand,
                            onValueChange = { command ->
                                onPreferencesUpdate(preferences.copy(customCommand = command))
                            },
                            label = { Text("Custom Command") },
                            placeholder = { Text("--extract-flat --write-info-json") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = false,
                            maxLines = 3
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    canProceed: Boolean,
    selectedType: DownloadType,
    useFormatSelection: Boolean,
    onCancel: () -> Unit,
    onFetchInfo: () -> Unit,
    onDownload: () -> Unit,
    onStartTask: () -> Unit
) {
    val actionButton = remember(selectedType, useFormatSelection) {
        when {
            selectedType == DownloadType.Command -> ActionButton.StartTask
            useFormatSelection && selectedType != DownloadType.Playlist -> ActionButton.FetchInfo
            else -> ActionButton.Download
        }
    }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        item {
            OutlinedButton(
                onClick = onCancel,
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cancel")
            }
        }

        item {
            Button(
                onClick = {
                    when (actionButton) {
                        ActionButton.FetchInfo -> onFetchInfo()
                        ActionButton.Download -> onDownload()
                        ActionButton.StartTask -> onStartTask()
                    }
                },
                enabled = canProceed,
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                AnimatedContent(
                    targetState = actionButton,
                    label = "ActionButton"
                ) { action ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = when (action) {
                                ActionButton.FetchInfo -> Icons.Outlined.FileDownload
                                ActionButton.Download -> Icons.Outlined.Download
                                ActionButton.StartTask -> Icons.Outlined.PlayArrow
                            },
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            when (action) {
                                ActionButton.FetchInfo -> "Fetch Info"
                                ActionButton.Download -> "Download"
                                ActionButton.StartTask -> "Start Task"
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingPage(
    modifier: Modifier = Modifier,
    taskKey: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
        
        Text(
            text = "Fetching information...",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = taskKey,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ErrorPage(
    modifier: Modifier = Modifier,
    state: SheetState.Error,
    onActionPost: (Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.error
        )
        
        Text(
            text = "Fetch Info Error",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = state.throwable.message ?: "Unknown error occurred",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onActionPost(Action.Reset) }
            ) {
                Text("Back")
            }
            
            Button(
                onClick = { 
                    // Retry the same action
                    onActionPost(state.action)
                }
            ) {
                Text("Retry")
            }
        }
    }
}

// Supporting Data Classes and Enums
enum class DownloadType(val displayName: String) {
    Auto("Auto"),
    Video("Video"),
    Audio("Audio"),
    Playlist("Playlist"),
    Command("Command")
}

enum class VideoQuality(val displayName: String) {
    Q144P("144p"),
    Q240P("240p"),
    Q360P("360p"),
    Q480P("480p"),
    Q720P("720p"),
    Q1080P("1080p"),
    Q1440P("1440p"),
    Q2160P("2160p"),
    BEST("Best"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low")
}

enum class VideoFormat(val extension: String, val priority: Int) {
    // Video formats
    MP4("mp4", 10),
    MKV("mkv", 8),
    WEBM("webm", 6),
    AVI("avi", 4),
    MOV("mov", 3),
    WMV("wmv", 2),
    FLV("flv", 1),
    THREE_GP("3gp", 0),
    
    // Audio formats
    M4A("m4a", 10),
    AAC("aac", 9),
    MP3("mp3", 8),
    OPUS("opus", 7),
    OGG("ogg", 6),
    FLAC("flac", 5),
    WAV("wav", 4),
    WMA("wma", 2)
}

enum class AudioQuality(val displayName: String, val bitrate: String) {
    Q64K("64 kbps", "64k"),
    Q128K("128 kbps", "128k"),
    Q192K("192 kbps", "192k"),
    Q256K("256 kbps", "256k"),
    Q320K("320 kbps", "320k"),
    BEST("Best", "best")
}
