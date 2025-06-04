package com.example.snaptube.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.get
import com.example.snaptube.R
import com.example.snaptube.download.DownloadState
import com.example.snaptube.download.Task
import com.example.snaptube.ui.components.ActionSheet
import com.example.snaptube.ui.components.DownloadDialog
import com.example.snaptube.ui.components.VideoCardV2
import com.example.snaptube.ui.viewmodels.DownloadsViewModelV2
import com.example.snaptube.utils.DownloadPreferencesManager

enum class Filter(val displayName: String) {
    All("All"),
    Downloading("Downloading"),
    Completed("Completed"),
    Canceled("Canceled"),
    Error("Error")
}

enum class ViewMode {
    List, Grid
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadPageV2(
    modifier: Modifier = Modifier,
    onBottomBarVisibilityChange: (Boolean) -> Unit = {},
    viewModel: DownloadsViewModelV2 = koinViewModel()
) {
    var selectedFilter by remember { mutableStateOf(Filter.All) }
    var viewMode by remember { mutableStateOf(ViewMode.List) }
    var showActionSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }
    var showDownloadDialog by remember { mutableStateOf(false) }
    
    val preferencesManager: DownloadPreferencesManager = get()

    val tasks by viewModel.tasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Filter tasks based on selected filter
    val filteredTasks = remember(tasks, selectedFilter) {
        when (selectedFilter) {
            Filter.All -> tasks
            Filter.Downloading -> tasks.filter { 
                it.downloadState is Task.DownloadState.FetchingInfo || it.downloadState is Task.DownloadState.Running
            }
            Filter.Completed -> tasks.filter { it.downloadState is Task.DownloadState.Completed }
            Filter.Canceled -> tasks.filter { it.downloadState is Task.DownloadState.Canceled }
            Filter.Error -> tasks.filter { it.downloadState is Task.DownloadState.Error }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Top bar with filters and view toggle
        TopAppBar(
            title = {
                Text(
                    text = "Downloads",
                    fontWeight = FontWeight.Bold
                )
            },
            actions = {
                // View mode toggle
                IconButton(
                    onClick = { 
                        viewMode = if (viewMode == ViewMode.List) ViewMode.Grid else ViewMode.List 
                    }
                ) {
                    Icon(
                        imageVector = if (viewMode == ViewMode.List) Icons.Default.GridView else Icons.Default.ViewList,
                        contentDescription = "Toggle view mode"
                    )
                }
                
                // More options
                IconButton(onClick = { /* TODO: Implement settings */ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options"
                    )
                }
            }
        )

        // Filter chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(Filter.values()) { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { 
                        Text(
                            text = buildString {
                                append(filter.displayName)
                                if (filter != Filter.All) {
                                    val count = when (filter) {
                                        Filter.Downloading -> tasks.count { 
                                            it.downloadState is Task.DownloadState.FetchingInfo || it.downloadState is Task.DownloadState.Running
                                        }
                                        Filter.Completed -> tasks.count { it.downloadState is Task.DownloadState.Completed }
                                        Filter.Canceled -> tasks.count { it.downloadState is Task.DownloadState.Canceled }
                                        Filter.Error -> tasks.count { it.downloadState is Task.DownloadState.Error }
                                        else -> 0
                                    }
                                    if (count > 0) append(" ($count)")
                                } else {
                                    if (tasks.isNotEmpty()) append(" (${tasks.size})")
                                }
                            },
                            style = MaterialTheme.typography.bodySmall
                        ) 
                    },
                    modifier = Modifier.wrapContentWidth()
                )
            }
        }

        // Content area with FAB
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { showDownloadDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download video"
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when {
                    isLoading -> {
                        // Loading state
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    
                    filteredTasks.isEmpty() -> {
                        // Empty state
                        EmptyDownloadsPlaceholder(
                            filter = selectedFilter,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    
                    else -> {
                        // Content based on view mode
                        when (viewMode) {
                            ViewMode.List -> {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(
                                        items = filteredTasks,
                                        key = { it.id }
                                    ) { task ->
                                        VideoCardV2(
                                            viewState = task.viewState,
                                            onButtonClick = {
                                                selectedTask = task
                                                showActionSheet = true
                                            }
                                        )
                                    }
                                }
                            }
                            
                            ViewMode.Grid -> {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(
                                        items = filteredTasks,
                                        key = { it.id }
                                    ) { task ->
                                        VideoCardV2(
                                            viewState = task.viewState,
                                            onButtonClick = {
                                                selectedTask = task
                                                showActionSheet = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Action sheet
    if (showActionSheet && selectedTask != null) {
        ActionSheet(
            task = selectedTask!!,
            viewState = selectedTask!!.viewState,
            downloadState = selectedTask!!.downloadState,
            onDismissRequest = {
                showActionSheet = false
                selectedTask = null
            },
            onActionPost = { task, action ->
                viewModel.handleTaskAction(task, action)
                showActionSheet = false
                selectedTask = null
            }
        )
    }

    // Download dialog
    DownloadDialog(
        isVisible = showDownloadDialog,
        onDismiss = { showDownloadDialog = false },
        onStartDownload = { url, dialogPreferences ->
            // Convert DialogPreferences to real DownloadPreferences using the built-in method
            val preferences = dialogPreferences.toDownloadPreferences()
            
            // Start the download using the real preferences
            viewModel.startDownload(url, preferences)
        }
    )
}

@Composable
private fun EmptyDownloadsPlaceholder(
    filter: Filter,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = when (filter) {
                Filter.All -> Icons.Default.Download
                Filter.Downloading -> Icons.Default.CloudDownload
                Filter.Completed -> Icons.Default.CheckCircle
                Filter.Canceled -> Icons.Default.Cancel
                Filter.Error -> Icons.Default.Error
            },
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = when (filter) {
                Filter.All -> "No downloads yet"
                Filter.Downloading -> "No active downloads"
                Filter.Completed -> "No completed downloads"
                Filter.Canceled -> "No canceled downloads"
                Filter.Error -> "No failed downloads"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = when (filter) {
                Filter.All -> "Start downloading videos to see them here"
                Filter.Downloading -> "Downloads in progress will appear here"
                Filter.Completed -> "Successfully downloaded videos will appear here"
                Filter.Canceled -> "Canceled downloads will appear here"
                Filter.Error -> "Failed downloads will appear here"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}
