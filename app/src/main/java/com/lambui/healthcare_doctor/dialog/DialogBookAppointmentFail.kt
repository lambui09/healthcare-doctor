package com.lambui.healthcare_doctor.dialog

import android.content.Context
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_book_appointment_fail.*

class DialogBookAppointmentFail (context: Context, listener: OnDialogBookAppointment) :
    BaseDialog(context, R.style.BaseDialog) {
    private val listenerConfirm: OnDialogBookAppointment = listener
    override fun getLayout(): Int = R.layout.dialog_book_appointment_fail

    override fun initData() {
        setCancelable(true)
    }

    override fun listener() {
        btnConfirm.setOnClickListener {
            dismiss()
            listenerConfirm.onConfirm()
        }
    }
}