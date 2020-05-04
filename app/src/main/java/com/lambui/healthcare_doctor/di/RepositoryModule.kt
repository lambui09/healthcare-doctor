package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import com.lambui.healthcare_doctor.data.source.repositories.*
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import org.koin.dsl.module

val repositoryModule = module {
    single { provideTimeCountDownRepository(get()) }
    single { provideUserAuthRepository(get()) }
    single { provideTokenRepository(get()) }
}

fun provideTimeCountDownRepository(sharedPrefsApi: SharedPrefsApi): TimeCountDownRepository =
    TimeCountDownImpl(sharedPrefsApi)

fun provideUserAuthRepository(authApi: AuthApi): UserAuthRepository =
    UserAuthRepositoryImpl(authApi)

fun provideTokenRepository(sharedPrefsApi: SharedPrefsApi): TokenRepository =
    TokenRepositoryImpl(sharedPrefsApi)