package com.lambui.healthcare_doctor.utils

import android.content.Context
import android.net.ConnectivityManager
import com.lambui.healthcare_doctor.MainApplication

class InternetManager {
  companion object {
    fun isConnected(): Boolean {
      val cm = MainApplication.sInstance.getSystemService(
        Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
      return cm?.activeNetworkInfo != null
    }
  }
}