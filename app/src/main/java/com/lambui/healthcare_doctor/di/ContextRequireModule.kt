package com.lambui.healthcare_doctor.di

import android.app.Application
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsImpl
import org.koin.dsl.module

val contextRequireModule = module {
    single { provideSharedPrefsApi(get()) }
}
fun provideSharedPrefsApi(app: Application): SharedPrefsApi {
    return SharedPrefsImpl(app)
}