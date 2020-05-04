package com.lambui.healthcare_doctor

import android.app.Activity
import android.app.Application
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.cloudinary.android.MediaManager
import com.lambui.healthcare_doctor.di.*
import com.lambui.healthcare_doctor.utils.Foreground
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application(), Foreground.Listener {


  private var currentClass: Class<*>? = null

  var reLoginListener: ReLoginListener? = null

  override fun onCreate() {
    super.onCreate()

    sInstance = this
    initFastNetwork()
    initCloudinary()

    startKoin {
      androidLogger(Level.DEBUG)
      androidContext(this@MainApplication)
      androidFileProperties()
      modules(listOf(appModule,contextRequireModule, networkModule, viewModelModule, repositoryModule))
    }
  }

  override fun onLowMemory() {
    super.onLowMemory()
  }

  override fun onTrimMemory(level: Int) {
    super.onTrimMemory(level)
  }

  override fun onBecameBackground() {
    Log.d(TAG, "onBecameForeground")
  }

  override fun onBecameForeground() {
    Log.d(TAG, "onBecameBackground")
  }


  fun setCurrentClass(clazz: Class<out Activity>) {
    currentClass = clazz
    Log.d("CurrentClass: ", clazz.javaClass.simpleName)
  }

  fun getCurrentClass(): Class<*>? {
    return currentClass
  }

  fun registerReLoginListener(listener: ReLoginListener?) {
    reLoginListener = listener
  }

  fun unRegisterReLoginListener() {
    reLoginListener = null
  }

  fun onLogout() {
  }

  companion object {
    private const val TAG = "MainApplication"
    lateinit var sInstance: MainApplication
  }

  fun initCloudinary(){
    MediaManager.init(this)
  }

  private fun initFastNetwork() {
    val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
      .build()
    AndroidNetworking.initialize(applicationContext, okHttpClient)
  }

  interface ReLoginListener {
    fun onReLogin()
  }
}