package com.lambui.healthcare_doctor.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.lambui.healthcare_doctor.MainApplication
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.data.source.remote.api.error.RetrofitException
import com.lambui.healthcare_doctor.data.source.remote.api.error.Type
import com.lambui.healthcare_doctor.dialog.DialogAlert
import com.lambui.healthcare_doctor.dialog.DialogConfirmDelete
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.DialogManagerImpl

@SuppressLint("Registered")
abstract class BaseActivity<VM : BaseViewModel> :AppCompatActivity(), MainApplication.ReLoginListener, BaseView {

  protected abstract val layoutID: Int
  protected abstract val viewModelx: VM

  lateinit var dialogManager: DialogManager
  private val compositeDisposable = CompositeDisposable()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    setContentView(layoutID)
    dialogManager = DialogManagerImpl(this)
    initialize()
    initBroadcastReceiver()
    baseObserver()
    onSubscribeObserver()
    initStatusBarGradient()
    registerOnClick()
  }

  override fun onResume() {
    super.onResume()
  }

  override fun onPause() {
    super.onPause()
  }

  private fun initBroadcastReceiver() {
  }

  private fun baseObserver() {
    viewModelx.isLoading.observe(this, Observer {
      if (it) showLoading() else hideLoading()
    })

    viewModelx.showError.observe(this, Observer {
      showAlertDialog(it)
    })
  }

  override fun onStart() {
    super.onStart()
    MainApplication.sInstance.setCurrentClass(javaClass)
    MainApplication.sInstance.registerReLoginListener(this)
  }

  override fun onDestroy() {
    compositeDisposable.clear()
    dialogManager.onRelease()
    super.onDestroy()

  }

  override fun showLoading() {
    dialogManager.showLoading()
  }

  override fun hideLoading() {
    dialogManager.hideLoading()
  }

  override fun showToastSuccess(message: String) {
    dialogManager.showToastSuccess(message)
  }

  override fun showDialogType(toastType: String, message: String) {
    dialogManager.showDialogType(toastType, message)
  }

  override fun showAlertDialog(
    title: String, message: String, listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    dialogManager.showAlertDialog(title, message, listener)
  }

  override fun showAlertDialogWithTitleButton(
    title: String,
    message: String,
    titleButton: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    dialogManager.showAlertDialogWithTitleButton(title, message, titleButton, listener)
  }

  override fun showConfirmDialog(
    title: String?, message: String?,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    dialogManager.showConfirmDialog(title, message, listener)
  }

  override fun showConfirmDialogWithTitleButton(
    title: String?,
    message: String?,
    titleButtonPositive: String,
    titleButtonNegative: String,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    dialogManager.showConfirmDialogWithTitleButton(
      title,
      message,
      titleButtonPositive,
      titleButtonNegative,
      listener
    )
  }

  override fun showConfirmDeleteDialog(title: String?, message: String?,
    listener: DialogConfirmDelete.OnButtonClickedListener?) {

  }

  override fun handleApiError(apiError: Throwable) {
    if (apiError is RetrofitException) {
      if (apiError.getErrorType() == Type.NETWORK) {
        showAlertDialog(message = resources.getString(R.string.text_internet_error),
          listener = object : DialogAlert.Companion.OnButtonClickedListener {
            override fun onPositiveClicked() {
              reLogin()
            }
          })
        return
      }

      val msgError = apiError.getMsgError()
      if (msgError != null) {
        showAlertDialog(message = msgError)
        return
      }

      val allMsgError = apiError.getAllMsgError()
      if (allMsgError != null) {
        showAlertDialog(message = allMsgError)
        return
      }
    }
  }

  override fun onError(@StringRes resId: Int) {
  }

  override fun launchDisposable(job: () -> Disposable) {
    compositeDisposable.add(job())
  }

  override fun onReLogin() {
  }

  override fun showErrorInternet() {
    showAlertDialog(title = resources.getString(R.string.title_error),
      message = resources.getString(R.string.messenger_error_connect_network),
      listener = object : DialogAlert.Companion.OnButtonClickedListener {
        override fun onPositiveClicked() {

        }
      })
  }

  private fun reLogin() {
    MainApplication.sInstance.onLogout()
  }

  protected abstract fun initialize()

  protected abstract fun onSubscribeObserver()

  protected abstract fun registerOnClick()

  fun transactionSlideOutTopToBottom() {
    overridePendingTransition(R.anim.no_animation, R.anim.slide_down)
  }

  fun transactionSlideInBottomToTop() {
    overridePendingTransition(R.anim.slide_up, R.anim.no_animation)
  }

  fun transactionSlideOutRightToLeft() {
    overridePendingTransition(R.anim.no_animation, R.anim.slide_out_right)
  }

  private fun initStatusBarGradient() {
    val background = getDrawable(R.drawable.bg_status_bar_gradient)
    with(window) {
      addFlags(android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
      statusBarColor = ContextCompat.getColor(context, R.color.transparent)
      navigationBarColor = ContextCompat.getColor(context, R.color.transparent)
      setBackgroundDrawable(background)
    }
  }

  private fun logout() {
  }
}