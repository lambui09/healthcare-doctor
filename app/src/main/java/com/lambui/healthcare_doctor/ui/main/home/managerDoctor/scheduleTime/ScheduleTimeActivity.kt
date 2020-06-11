package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_schedule_time_doctor.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScheduleTimeActivity : BaseActivity<ScheduleDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.activity_schedule_time_doctor
    override val viewModelx: ScheduleDoctorVM by viewModel()

    override fun initialize() {
        replaceFragmentInActivity(
            containerId = frameLayoutScheduleTime.id,
            tag = ScheduleDoctorFragment::class.java.simpleName,
            fragment = ScheduleDoctorFragment(),
            addToBackStack = false,
            animateType = AnimateType.NO_ANIMATION
        )
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}