package com.example.snaptube.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.snaptube.R
import com.example.snaptube.ui.viewmodels.SettingsViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel(),
    onBottomBarVisibilityChange: (Boolean) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val downloadDirectory by viewModel.downloadDirectory.collectAsState()
    val useSystemTheme by viewModel.useSystemTheme.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    val appVersion by viewModel.appVersion.collectAsState()
    
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
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
        )
        
            // Settings List
            LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                // General Section
                item {
                    SectionHeader(title = stringResource(R.string.general))
                }
                
                    item {
                    PreferenceCard(
                        title = stringResource(R.string.downloads_directory),
                        description = downloadDirectory,
                        icon = Icons.Outlined.Folder,
                        onClick = viewModel::selectDownloadDirectory
                    )
                }
                
                // Appearance Section
                item {
                    SectionHeader(title = stringResource(R.string.appearance))
                }
                
                item {
                    SwitchPreferenceCard(
                        title = stringResource(R.string.use_system_theme),
                        description = "Follow system theme settings",
                        icon = Icons.Outlined.Palette,
                        isChecked = useSystemTheme,
                        onCheckedChange = viewModel::setUseSystemTheme
                                )
                            }
                
                    item {
                    SwitchPreferenceCard(
                        title = stringResource(R.string.dark_theme),
                        description = "Enable dark theme",
                        icon = Icons.Outlined.DarkMode,
                        isChecked = isDarkTheme,
                        enabled = !useSystemTheme,
                        onCheckedChange = viewModel::setDarkTheme
                                )
                            }
                            
                // About Section
                item {
                    SectionHeader(title = stringResource(R.string.about))
                }
                
                item {
                    PreferenceCard(
                        title = stringResource(R.string.version),
                        description = appVersion,
                        icon = Icons.Outlined.Info
                                )
                            }
                            
                item {
                    PreferenceCard(
                        title = stringResource(R.string.licenses),
                        description = "View open source licenses",
                        icon = Icons.Outlined.Article,
                        onClick = viewModel::openLicensesScreen
                                )
                            }
                
                    item {
                    PreferenceCard(
                        title = stringResource(R.string.privacy_policy),
                        description = "View privacy policy",
                        icon = Icons.Outlined.Lock,
                        onClick = viewModel::openPrivacyPolicy
                    )
                }
                
                // Add some space at the bottom
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                                )
                            }
                            
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwitchPreferenceCard(
    title: String,
    description: String,
    icon: ImageVector,
    isChecked: Boolean,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled) {
                onCheckedChange(!isChecked)
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = if (enabled) {
                    MaterialTheme.colorScheme.secondaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f)
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (enabled) {
                            MaterialTheme.colorScheme.onSecondaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.6f)
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    }
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (enabled) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    }
                )
            }
            
            Switch(
                checked = isChecked,
                onCheckedChange = { onCheckedChange(it) },
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    }
}
