package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.constant.NetworkConstants
import com.lambui.healthcare_doctor.data.source.remote.api.middleware.InterceptorImpl
import com.lambui.healthcare_doctor.data.source.remote.api.middleware.RxErrorHandlingCallAdapterFactory
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {provideInterceptor(get()) }
    single { provideOkHttpClient(get(), get()) }
}

fun provideInterceptor(
    tokenRepository: TokenRepository
): Interceptor {
    return InterceptorImpl(tokenRepository)
}

fun provideOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()

    //Handle one request. After request finish -> run new request.
    val dispatcher = Dispatcher()
    dispatcher.maxRequests = 1
    dispatcher.maxRequestsPerHost = 1
    httpClientBuilder.dispatcher(dispatcher)

    httpClientBuilder.cache(cache)
    httpClientBuilder.addInterceptor(interceptor)

    httpClientBuilder.readTimeout(
        NetworkConstants.READ_TIMEOUT, TimeUnit.SECONDS
    )
    httpClientBuilder.writeTimeout(
        NetworkConstants.WRITE_TIMEOUT, TimeUnit.SECONDS
    )
    httpClientBuilder.connectTimeout(
        NetworkConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS
    )

    val logging = HttpLoggingInterceptor()
    httpClientBuilder.addInterceptor(logging)
    logging.level = HttpLoggingInterceptor.Level.BODY

    return httpClientBuilder.build()
}

fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("xxxxx")
        .addCallAdapterFactory(
            RxErrorHandlingCallAdapterFactory.create()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}


