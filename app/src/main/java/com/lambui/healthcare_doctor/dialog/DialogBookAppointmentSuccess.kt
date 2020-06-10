package com.lambui.healthcare_doctor.dialog

import android.content.Context
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_book_appointment_success.*

class DialogBookAppointmentSuccess (context: Context, listener: OnDialogBookAppointment) :
    BaseDialog(context, R.style.BaseDialog) {
    private var onClickListener: OnDialogBookAppointment = listener
    override fun getLayout(): Int = R.layout.dialog_book_appointment_success


    override fun initData() {
        setCancelable(true)
    }

    override fun listener() {
        btnConfirm.setOnClickListener {
            dismiss()
            onClickListener.onConfirm()
        }
    }
}