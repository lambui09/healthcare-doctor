package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.data.source.remote.api.middleware.BooleanAdapter
import com.lambui.healthcare_doctor.data.source.remote.api.middleware.DoubleAdapter
import com.lambui.healthcare_doctor.data.source.remote.api.middleware.IntegerAdapter
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import com.lambui.healthcare_doctor.utils.rxAndroid.SchedulerProvider
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.dsl.module

val appModule = module {
    single { provideBaseSchedulerProvider() }
    single { provideGson() }

}

fun provideBaseSchedulerProvider() : BaseSchedulerProvider{
    return SchedulerProvider()
}

fun provideGson() : Gson{
    val booleanAdapter = BooleanAdapter()
    val integerAdapter = IntegerAdapter()
    val doubleAdapter = DoubleAdapter()
    return GsonBuilder()
        .registerTypeAdapter(Boolean::class.java, booleanAdapter)
        .registerTypeAdapter(Int::class.java, integerAdapter)
        .registerTypeAdapter(Double::class.java, doubleAdapter)
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()
}


