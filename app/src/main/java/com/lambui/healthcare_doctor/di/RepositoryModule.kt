package com.lambui.healthcare_doctor.di

import com.google.gson.Gson
import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import com.lambui.healthcare_doctor.data.source.remote.service.InformationAppointmentApi
import com.lambui.healthcare_doctor.data.source.repositories.*
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import org.koin.dsl.module

val repositoryModule = module {
    single { provideTimeCountDownRepository(get()) }
    single { provideUserAuthRepository(get(), get()) }
    single { provideTokenRepository(get()) }
    single { provideUserLocalRepository(get()) }
    single { provideInformationAppointmentRepository(get(), get()) }
}

fun provideTimeCountDownRepository(sharedPrefsApi: SharedPrefsApi): TimeCountDownRepository =
    TimeCountDownImpl(sharedPrefsApi)

fun provideUserAuthRepository(authApi: AuthApi, gson: Gson): UserAuthRepository =
    UserAuthRepositoryImpl(authApi, gson)

fun provideTokenRepository(sharedPrefsApi: SharedPrefsApi): TokenRepository =
    TokenRepositoryImpl(sharedPrefsApi)

fun provideUserLocalRepository(sharedPrefsApi: SharedPrefsApi): UserLocalRepository =
    UserLocalRepositoryImpl(sharedPrefsApi)

fun provideInformationAppointmentRepository(
    informationAppointmentApi: InformationAppointmentApi,
    gson: Gson
): AppointmentRepository =
    AppointmentRepositoryImpl(informationAppointmentApi, gson)