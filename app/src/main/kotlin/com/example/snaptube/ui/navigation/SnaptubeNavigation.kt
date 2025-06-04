package com.example.snaptube.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.snaptube.R
import com.example.snaptube.ui.screens.*
import com.example.snaptube.ui.screens.DownloadPageV2

sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Home : BottomNavItem("home", R.string.home, Icons.Filled.Home, Icons.Outlined.Home)
    object Downloads : BottomNavItem("downloads", R.string.downloads, Icons.Filled.Download, Icons.Outlined.Download)
    object Library : BottomNavItem("library", R.string.library, Icons.Filled.VideoLibrary, Icons.Outlined.VideoLibrary)
    object Settings : BottomNavItem("settings", R.string.settings, Icons.Filled.Settings, Icons.Outlined.Settings)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnaptubeNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Downloads,
        BottomNavItem.Library,
        BottomNavItem.Settings
    )
    
    var bottomBarVisible by remember { mutableStateOf(true) }
    
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        contentColor = MaterialTheme.colorScheme.onBackground,
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisible,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
            ) {
                NavigationBar(
                    tonalElevation = 0.dp,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            icon = { 
                                Icon(
                                    if (selected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = stringResource(item.titleResId)
                                )
                            },
                            label = { 
                                Text(stringResource(item.titleResId))
                            },
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onBottomBarVisibilityChange = { visible ->
                        bottomBarVisible = visible
                    }
                )
            }
            composable(BottomNavItem.Downloads.route) {
                DownloadPageV2(
                    onBottomBarVisibilityChange = { visible ->
                        bottomBarVisible = visible
                    }
                )
            }
            composable(BottomNavItem.Library.route) {
                LibraryScreen(
                    onBottomBarVisibilityChange = { visible ->
                        bottomBarVisible = visible
                    }
                )
            }
            composable(BottomNavItem.Settings.route) {
                SettingsScreen(
                    onBottomBarVisibilityChange = { visible ->
                        bottomBarVisible = visible
                    }
                )
            }
        }
    }
}
