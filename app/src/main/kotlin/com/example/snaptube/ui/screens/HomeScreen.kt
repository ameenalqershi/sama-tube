package com.example.snaptube.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.snaptube.R
import com.example.snaptube.models.DownloadFormat
import com.example.snaptube.ui.components.VideoInfoCard
import org.koin.androidx.compose.koinViewModel
import com.example.snaptube.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val urlInput by viewModel.urlInput.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    var textFieldFocused by remember { mutableStateOf(false) }
    
    LaunchedEffect(textFieldFocused) {
        // Hide the bottom bar when keyboard is likely to be shown
        onBottomBarVisibilityChange(!textFieldFocused)
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // App Logo/Title
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 24.dp, bottom = 32.dp)
            )
            
            // URL Input Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // URL Input Field
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = viewModel::updateUrl,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { focusState ->
                                textFieldFocused = focusState.isFocused
                            },
                        label = { Text(stringResource(R.string.enter_video_url)) },
                        leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                        trailingIcon = {
                            if (urlInput.isNotEmpty()) {
                                IconButton(onClick = { viewModel.updateUrl("") }) {
                                    Icon(Icons.Default.Clear, contentDescription = stringResource(R.string.clear))
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                if (urlInput.isNotEmpty()) {
                                    viewModel.extractVideoInfo()
                                    keyboardController?.hide()
                                    textFieldFocused = false
                                }
                            }
                        ),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Extract Button
                    Button(
                        onClick = {
                            viewModel.extractVideoInfo()
                            keyboardController?.hide()
                            textFieldFocused = false
                        },
                        enabled = urlInput.isNotEmpty() && !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (uiState.isLoading) 
                                stringResource(R.string.extracting) 
                            else 
                                stringResource(R.string.extract_video_info)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Video Info Display Area
            val videoInfo = uiState.videoInfo
            AnimatedVisibility(
                visible = videoInfo != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                videoInfo?.let { info ->
                    VideoInfoCard(
                        videoInfo = info,
                        onDownloadClick = { format: DownloadFormat ->
                            viewModel.startDownload(format)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Error Display
            val error = uiState.error
            AnimatedVisibility(
                visible = error != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                error?.let { errorMessage ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(R.string.error),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = errorMessage,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            TextButton(
                                onClick = viewModel::clearError,
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                                )
                            ) {
                                Text(stringResource(R.string.ok))
                            }
                        }
                    }
                }
            }
            
            // Empty State
            if (urlInput.isEmpty() && uiState.videoInfo == null && uiState.error == null) {
                Spacer(modifier = Modifier.height(32.dp))
                Icon(
                    Icons.Outlined.Download,
                    contentDescription = null,
                    modifier = Modifier.size(72.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.enter_url_hint),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(48.dp))
            }
            
            Spacer(modifier = Modifier.height(80.dp)) // Bottom padding for navigation bar
        }
    }
}
