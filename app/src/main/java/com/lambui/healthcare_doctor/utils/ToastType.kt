package com.lambui.healthcare_doctor.utils

import androidx.annotation.StringDef

interface ToastType {
  @StringDef(
    SUCCESS,
    ERROR,
    INFO,
    WARNING
  )
  annotation class Toast

  companion object {
    const val SUCCESS = "SUCCESS"
    const val ERROR = "ERROR"
    const val INFO = "INFO"
    const val WARNING = "WARNING"
  }
}