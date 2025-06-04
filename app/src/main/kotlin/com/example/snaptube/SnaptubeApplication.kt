package com.example.snaptube

import android.app.Application
import com.example.snaptube.BuildConfig
import com.example.snaptube.di.allModules
import com.example.snaptube.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import com.yausername.youtubedl_android.YoutubeDL

/**
 * كلاس Application الرئيسي لتطبيق Snaptube
 * مسؤول عن:
 * 1. تهيئة Timber للسجلات عند بدء التطبيق
 * 2. تهيئة Koin لحقن التبعيات
 * 3. تهيئة مكتبة YoutubeDL Android
 */
class SnaptubeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // تهيئة Timber للـ debug logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        
        // تهيئة YoutubeDL Android
        try {
            YoutubeDL.getInstance().init(this)
            Timber.d("YoutubeDL initialized successfully")
        } catch (e: Exception) {
            Timber.e(e, "Failed to initialize YoutubeDL")
        }
        
        // بدء Koin مع جميع الوحدات المعرفة
        startKoin {
            androidContext(this@SnaptubeApplication)
            modules(allModules + uiModule)
        }

        Timber.d("SnaptubeApplication initialized with Koin")
    }
}