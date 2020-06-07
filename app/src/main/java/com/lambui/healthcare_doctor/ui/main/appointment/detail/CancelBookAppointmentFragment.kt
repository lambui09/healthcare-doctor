package com.lambui.healthcare_doctor.ui.main.appointment.detail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CancelBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_cancel_book_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }

}