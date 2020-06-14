package com.lambui.healthcare_doctor.di

import com.google.gson.Gson
import com.lambui.healthcare_doctor.data.source.remote.service.*
import com.lambui.healthcare_doctor.data.source.repositories.*
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsApi
import org.koin.dsl.module
import kotlin.math.sin

val repositoryModule = module {
    single { provideTimeCountDownRepository(get()) }
    single { provideUserAuthRepository(get(), get()) }
    single { provideTokenRepository(get()) }
    single { provideUserLocalRepository(get()) }
    single { provideInformationAppointmentRepository(get(), get()) }
    single { provideNotificationRepository(get(), get()) }
    single { provideDoctorRepository(get(), get()) }
    single { provideSettingAccountRepository(get(), get()) }
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

fun provideNotificationRepository(
    notificationApi: NotificationApi,
    gson: Gson
): NotificationRepository = NotificationRepositoryImpl(notificationApi, gson)

fun provideDoctorRepository(
    doctorApi: DoctorApi,
    gson: Gson
): DoctorRepository = DoctorRepositoryImpl(doctorApi, gson)

fun provideSettingAccountRepository(
    settingAccountApi: SettingAccountApi,
    gson: Gson
): SettingAccountRepository = SettingAccontRepositoryImpl(settingAccountApi, gson)