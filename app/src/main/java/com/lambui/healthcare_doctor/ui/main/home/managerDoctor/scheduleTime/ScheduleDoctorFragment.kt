package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScheduleDoctorFragment : BaseFragment<ScheduleDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_schedule_time_doctor
    override val viewModelx: ScheduleDoctorVM by sharedViewModel()


    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }

    override fun initialize() {

    }
}