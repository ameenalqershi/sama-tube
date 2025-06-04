package com.example.snaptube.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.example.snaptube.ui.viewmodels.HomeViewModel
import com.example.snaptube.ui.viewmodels.DownloadsViewModel
import com.example.snaptube.ui.viewmodels.DownloadsViewModelV2
import com.example.snaptube.ui.viewmodels.LibraryViewModel
import com.example.snaptube.ui.viewmodels.SettingsViewModel

/**
 * وحدة Koin للـ ViewModels والـ UI components
 */
val uiModule = module {
    
    // ViewModels
    viewModel { HomeViewModel() }
    viewModel { DownloadsViewModel() }
    viewModel { DownloadsViewModelV2(get()) }
    viewModel { LibraryViewModel() }
    viewModel { SettingsViewModel() }
}
