package com.lambui.healthcare_doctor.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

abstract class BaseDialog(context: Context, style: Int) : Dialog(context, style) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    requestWindowFeature(Window.FEATURE_NO_TITLE)
    val window = window!!
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    setContentView(getLayout())
    this.initData()
    listener()
  }

  abstract fun getLayout(): Int
  abstract fun initData()
  abstract fun listener()
}