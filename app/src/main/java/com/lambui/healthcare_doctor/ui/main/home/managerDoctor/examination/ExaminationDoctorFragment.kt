package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ExaminationDoctorFragment : BaseFragment<ExaminationDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_examination_doctor
    override val viewModelx: ExaminationDoctorVM by sharedViewModel()

    override fun initialize() {

    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}