package com.lambui.healthcare_doctor.base

import com.lambui.healthcare_doctor.dialog.DialogAlert
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.DialogConfirmDelete
import com.lambui.healthcare_doctor.utils.ToastType

interface DialogManager {
  fun showLoading()

  fun showProcessing()

  fun hideLoading()

  fun onRelease()

  fun showDialogType(@ToastType.Toast toastType: String, message: String)

  fun showToastSuccess(message: String)

  fun showAlertDialog(
    title: String, message: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  )

  fun showAlertDialogWithTitleButton(
    title: String, message: String, titleButton: String,
    listener: DialogAlert.Companion.OnButtonClickedListener?
  )

  fun showConfirmDialog(
    title: String?, message: String?,
    listener: DialogConfirm.OnButtonClickedListener?
  )

  fun showConfirmDialogWithTitleButton(
    title: String?, message: String?,
    titleButonAccept: String, titleButtonCancel: String,
    listener: DialogConfirm.OnButtonClickedListener?
  )

  fun showConfirmDeleteDialog(
    title: String?, message: String?,
    listener: DialogConfirmDelete.OnButtonClickedListener?
  )
}