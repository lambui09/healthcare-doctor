package com.lambui.healthcare_doctor.dialog

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.lambui.healthcare_doctor.MainApplication
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.DialogManager
import com.lambui.healthcare_doctor.utils.StringUtils
import com.lambui.healthcare_doctor.utils.ToastType
import com.lambui.healthcare_doctor.utils.extension.notNull
import java.lang.ref.WeakReference
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class DialogManagerImpl(cxt: Context?) : DialogManager {


  companion object {
    const val TAG = "DialogManagerImpl"
  }

  private var context: WeakReference<Context?>? = null
  private var progressDialog: ProgressDialog? = null
  private var handler = Handler(Looper.myLooper())
  private var viewToast: View? = null
  private var toast: Toast? = null

  init {
    context = WeakReference(cxt).apply {
      progressDialog = ProgressDialog(this.get()!!)
    }
  }

  override fun showLoading() {
    if (progressDialog != null && progressDialog?.isShowing == false) {
      progressDialog?.show()
    }
  }

  override fun showProcessing() {
    if (progressDialog != null && progressDialog?.isShowing == false) {
      progressDialog?.show()
    }
  }

  override fun hideLoading() {
    if (progressDialog != null && progressDialog?.isShowing == true) {
      progressDialog?.dismiss()
    }
  }

  override fun onRelease() {
    if (progressDialog != null) {
      progressDialog = null
    }
  }

  override fun showDialogType(toastType: String, message: String) {
    if (StringUtils.isBlank(message)) {
      return
    }

    if (HTTP_UNAUTHORIZED.toString() == message) {
      context?.get().notNull {
        MainApplication.sInstance.onLogout()
      }

    }

    when (toastType) {
      ToastType.SUCCESS -> showToastSuccess(message)
      ToastType.INFO -> Toast.makeText(context?.get(), message, Toast.LENGTH_SHORT).show()
      ToastType.WARNING -> Toast.makeText(context?.get(), message, Toast.LENGTH_SHORT).show()
    }
  }

  override fun showToastSuccess(message: String) {
    if (viewToast == null) {
      val inflater = LayoutInflater.from(context?.get())
      viewToast = inflater.inflate(
        R.layout.view_toast_success,
        (context?.get() as Activity).findViewById(R.id.layout_toast_success)
      )

      toast = Toast(context?.get())
      toast?.setGravity(Gravity.CENTER, 0, 0)
      toast?.duration = Toast.LENGTH_SHORT
      toast?.view = viewToast
    }
    val textView = viewToast?.findViewById<AppCompatTextView>(R.id.titleToast)
    textView?.text = message
    toast?.show()
  }

  override fun showAlertDialog(
    title: String
    , message: String
    , listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    val dialog = DialogAlert.newInstance(title, message, "", listener)
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  override fun showAlertDialogWithTitleButton(
    title: String, message: String, titleButton: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    val dialog = DialogAlert.newInstance(title, message, titleButton, listener)
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  override fun showConfirmDialog(
    title: String?, message: String?,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    val dialog = DialogConfirm.newInstance(
      title, message, "", "", listener
    )
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  override fun showConfirmDialogWithTitleButton(
    title: String?,
    message: String?,
    titleButonAccept: String,
    titleButtonCancel: String,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    val dialog = DialogConfirm.newInstance(
      title, message, titleButonAccept, titleButtonCancel, listener
    )
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

  override fun showConfirmDeleteDialog(
    title: String?, message: String?,
    listener: DialogConfirmDelete.OnButtonClickedListener?
  ) {
    val dialog = DialogConfirmDelete.newInstance(
      title, message, "", "", listener
    )
    val fm = (context?.get() as AppCompatActivity).supportFragmentManager
    dialog.show(fm, DialogAlert::class.java.simpleName)
  }

}