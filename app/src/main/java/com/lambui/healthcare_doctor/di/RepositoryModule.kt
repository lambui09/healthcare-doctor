package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import com.lambui.healthcare_doctor.data.source.repositories.TimeCountDownImpl
import com.lambui.healthcare_doctor.data.source.repositories.TimeCountDownRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepositoryImpl
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import org.koin.dsl.module

val repositoryModule = module {
    single { provideTimeCountDownRepository(get()) }
    single { provideUserAuthRepository(get()) }
}

fun provideTimeCountDownRepository(sharedPrefsApi: SharedPrefsApi): TimeCountDownRepository =
    TimeCountDownImpl(sharedPrefsApi)

fun provideUserAuthRepository(authApi: AuthApi): UserAuthRepository =
    UserAuthRepositoryImpl(authApi)