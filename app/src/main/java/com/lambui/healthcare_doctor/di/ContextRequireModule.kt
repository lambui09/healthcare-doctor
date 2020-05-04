package com.lambui.healthcare_doctor.di

import android.app.Application
import android.content.res.Resources
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsImpl
import okhttp3.Cache
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val contextRequireModule = module {
    single { provideSharedPrefsApi(get()) }
    single { provideResources(androidApplication()) }
    single { provideOkHttpCache(androidApplication()) }
}

fun provideSharedPrefsApi(app: Application): SharedPrefsApi {
    return SharedPrefsImpl(app)
}

fun provideResources(app: Application): Resources {
    return app.resources
}

fun provideOkHttpCache(app: Application): Cache {
    val cacheSize: Long = 10 * 1024 * 1024 // 10 MiB
    return Cache(app.cacheDir, cacheSize)
}