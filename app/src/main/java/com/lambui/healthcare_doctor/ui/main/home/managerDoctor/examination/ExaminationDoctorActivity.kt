package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_examination_doctor.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExaminationDoctorActivity : BaseActivity<ExaminationDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.activity_examination_doctor
    override val viewModelx: ExaminationDoctorVM by viewModel()

    override fun initialize() {
        replaceFragmentInActivity(
            containerId = frameLayoutExamination.id,
            tag = ExaminationDoctorFragment::class.java.simpleName,
            fragment = ExaminationDoctorFragment(),
            addToBackStack = false,
            animateType = AnimateType.NO_ANIMATION
        )
    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
    }

}