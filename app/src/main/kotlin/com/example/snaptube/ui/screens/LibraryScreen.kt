package com.example.snaptube.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AudioFile
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.snaptube.R
import com.example.snaptube.ui.viewmodels.LibraryItem
import com.example.snaptube.ui.viewmodels.LibraryViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LibraryScreen(
    viewModel: LibraryViewModel = koinViewModel(),
    onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // App Bar
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.library),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
            
            // Tabs
            val pagerState = rememberPagerState { 2 }
            val coroutineScope = rememberCoroutineScope()
            
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    icon = { Icon(Icons.Outlined.VideoLibrary, contentDescription = null) },
                    text = { Text("Videos") }
                )
                
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
                    icon = { Icon(Icons.Outlined.MusicNote, contentDescription = null) },
                    text = { Text("Audio") }
                )
            }
            
            // Tab Content
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> MediaGrid(
                        mediaItems = uiState.filteredItems.filter { !it.isAudioOnly },
                        onItemClick = viewModel::playFile,
                        emptyMessage = "No videos yet",
                        emptyIcon = Icons.Default.VideoFile,
                        loading = uiState.isLoading
                    )
                    1 -> MediaGrid(
                        mediaItems = uiState.filteredItems.filter { it.isAudioOnly },
                        onItemClick = viewModel::playFile,
                        emptyMessage = "No audio files yet",
                        emptyIcon = Icons.Default.AudioFile,
                        loading = uiState.isLoading
                )
            }
        }
        }
    }
}

@Composable
fun MediaGrid(
    mediaItems: List<LibraryItem>,
    onItemClick: (String) -> Unit,
    emptyMessage: String,
    emptyIcon: ImageVector,
    loading: Boolean = false
) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
        } else if (mediaItems.isEmpty()) {
            // Empty State
                    Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
                    ) {
                        Icon(
                    imageVector = emptyIcon,
                            contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                
                        Spacer(modifier = Modifier.height(16.dp))
                
                        Text(
                    text = emptyMessage,
                    style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(mediaItems) { media ->
                    MediaItemCard(
                        mediaItem = media,
                        onClick = { onItemClick(media.id) }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaItemCard(
    mediaItem: LibraryItem,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column {
            // Thumbnail or Icon if no thumbnail
            if (mediaItem.thumbnailUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(mediaItem.thumbnailUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                    ) {
                        Icon(
                        imageVector = if (mediaItem.isAudioOnly) Icons.Default.AudioFile else Icons.Default.VideoFile,
                            contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
            
            // Info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                        Text(
                    text = mediaItem.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                        )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                        Text(
                        text = mediaItem.duration,
                        style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    
                    Text(
                        text = mediaItem.getFormattedFileSize(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
