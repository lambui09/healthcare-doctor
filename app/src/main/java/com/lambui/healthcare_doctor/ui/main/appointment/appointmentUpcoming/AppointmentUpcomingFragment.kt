package com.lambui.healthcare_doctor.ui.main.appointment.appointmentUpcoming

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentUpcomingFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_upcoming
    override val viewModelx: AppointmentVM by sharedViewModel()
    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}