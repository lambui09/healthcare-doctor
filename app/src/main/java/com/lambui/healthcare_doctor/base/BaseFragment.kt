package com.lambui.healthcare_doctor.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.dialog.DialogAlert
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.DialogConfirmDelete
import com.lambui.healthcare_doctor.dialog.DialogManagerImpl
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VM : BaseViewModel> : Fragment(), BaseView {
  protected abstract val layoutID: Int
  val compositeDisposable = CompositeDisposable()

  protected abstract val viewModelx: VM

  lateinit var dialogManager: DialogManager

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layoutID, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    dialogManager = DialogManagerImpl(context)
    initialize()
    baseObserver()
    onSubscribeObserver()
    registerOnClick()
  }

  override fun onDestroyView() {
    compositeDisposable.clear()
    dialogManager.onRelease()
    super.onDestroyView()
  }

  override fun showLoading() {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showLoading()
  }

  override fun hideLoading() {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).hideLoading()
  }

  override fun handleApiError(apiError: Throwable) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).handleApiError(
      apiError
    )
  }

  override fun showToastSuccess(message: String) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showToastSuccess(
      message
    )
  }

  override fun showDialogType(toastType: String, message: String) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showDialogType(
      toastType, message
    )
  }

  override fun showAlertDialog(
    title: String,
    message: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showAlertDialog(
      title,
      message,
      listener
    )
  }

  override fun showAlertDialogWithTitleButton(
    title: String,
    message: String,
    titleButton: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  ) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showAlertDialogWithTitleButton(
      title,
      message,
      titleButton,
      listener
    )
  }

  override fun showConfirmDialog(
    title: String?, message: String?,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showConfirmDialog(
      title,
      message,
      listener
    )
  }

  override fun showConfirmDialogWithTitleButton(
    title: String?,
    message: String?,
    titleButtonPositive: String,
    titleButtonNegative: String,
    listener: DialogConfirm.OnButtonClickedListener?
  ) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showConfirmDialogWithTitleButton(
      title,
      message,
      titleButtonPositive,
      titleButtonNegative,
      listener
    )
  }



  override fun showConfirmDeleteDialog(
    title: String?, message: String?,
    listener: DialogConfirmDelete.OnButtonClickedListener?
  ) {
    if (activity is BaseActivity<*>)
      (activity as BaseActivity<*>).showConfirmDeleteDialog(
        title,
        message,
        listener
      )
  }

  override fun showErrorInternet(){
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).showErrorInternet()
  }

  override fun onError(@StringRes resId: Int) {
    if (activity is BaseActivity<*>) (activity as BaseActivity<*>).onError(resId)
  }

  override fun launchDisposable(job: () -> Disposable) {
    compositeDisposable.add(job())
  }

  protected abstract fun initialize()

  protected abstract fun onSubscribeObserver()

  private fun baseObserver() {
    viewModelx.isLoading.observe(this, Observer {
      if (it) showLoading() else hideLoading()
    })

    viewModelx.showError.observe(this, Observer {
      showAlertDialog(it)
    })
  }

  protected abstract fun registerOnClick()
}