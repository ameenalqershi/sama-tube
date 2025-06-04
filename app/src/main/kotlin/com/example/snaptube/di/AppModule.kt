package com.example.snaptube.di

import android.content.Context
import com.example.snaptube.download.DownloadManager
import com.example.snaptube.download.VideoInfoExtractor
import com.example.snaptube.services.NotificationService
import com.example.snaptube.services.MediaScannerService
import com.example.snaptube.utils.FileUtils
import com.example.snaptube.utils.NetworkUtils
import com.example.snaptube.utils.VideoUtils
import com.example.snaptube.utils.PreferenceUtils
import com.example.snaptube.utils.PermissionUtils
import com.example.snaptube.utils.LogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * وحدة حقن التبعيات الرئيسية للتطبيق
 * تحتوي على جميع المكونات والخدمات الأساسية
 */
val appModule = module {

    // Application Scope للعمليات الطويلة
    single<CoroutineScope> {
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    // Utilities - أدوات مساعدة
    single { FileUtils(androidContext()) }
    single { NetworkUtils(androidContext()) }
    single { VideoUtils(androidContext()) }
    single { PreferenceUtils(androidContext()) }
    single { PermissionUtils(androidContext()) }
    single { LogUtils.getInstance(androidContext()) }

    // Video Info Extractor - مستخرج معلومات الفيديو
    single { 
        VideoInfoExtractor(
            context = androidContext()
        ) 
    }

    // Download Manager - مدير التحميل
    single { 
        DownloadManager(
            context = androidContext(),
            videoInfoExtractor = get(),
            fileUtils = get(),
            networkUtils = get()
        ) 
    }

    // Notification Service - خدمة الإشعارات
    single { 
        NotificationService(
            context = androidContext(),
            settingsRepository = get(),
            fileUtils = get(),
            logUtils = get()
        ) 
    }

    // Media Scanner Service - خدمة فحص الوسائط
    single { 
        MediaScannerService(
            context = androidContext(),
            fileUtils = get(),
            videoUtils = get(),
            logUtils = get()
        ) 
    }
}

/**
 * قائمة جميع وحدات حقن التبعيات
 */
val allModules = listOf(
    appModule,
    databaseModule,
    downloadModule,
    repositoryModule
)
