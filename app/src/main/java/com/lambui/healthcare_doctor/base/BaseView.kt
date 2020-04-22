package com.lambui.healthcare_doctor.base

import androidx.annotation.StringRes
import com.lambui.healthcare_doctor.dialog.DialogAlert
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.DialogConfirmDelete
import com.lambui.healthcare_doctor.utils.ToastType
import io.reactivex.disposables.Disposable

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun onError(@StringRes resId: Int)
    fun handleApiError(apiError: Throwable)
    fun launchDisposable(job: () -> Disposable)

    // Showing dialog popup customize
    fun showDialogType(@ToastType.Toast toastType: String, message: String)

    fun showToastSuccess(message: String)

    fun showAlertDialog(
        title: String = "", message: String = "",
        listener: DialogAlert.Companion.OnButtonClickedListener? = null
    )

    fun showAlertDialogWithTitleButton(
        title: String = "", message: String = "", titleButton: String = "",
        listener: DialogAlert.Companion.OnButtonClickedListener? = null
    )

    fun showConfirmDialog(
        title: String? = "", message: String? = "",
        listener: DialogConfirm.OnButtonClickedListener? = null
    )

    fun showConfirmDialogWithTitleButton(
        title: String? = "", message: String? = "",
        titleButtonPositive: String = "", titleButtonNegative: String = "",
        listener: DialogConfirm.OnButtonClickedListener? = null
    )

    fun showConfirmDeleteDialog(
        title: String? = "", message: String? = "",
        listener: DialogConfirmDelete.OnButtonClickedListener? = null
    )

    fun showErrorInternet()
}