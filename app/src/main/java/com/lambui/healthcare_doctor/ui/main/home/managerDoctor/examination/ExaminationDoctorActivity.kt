package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.enums.ExaminationNav
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import com.lambui.healthcare_doctor.widget.toolbar.MainToolbar
import kotlinx.android.synthetic.main.activity_examination_doctor.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExaminationDoctorActivity : BaseActivity<ExaminationDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.activity_examination_doctor
    override val viewModelx: ExaminationDoctorVM by viewModel()

    override fun initialize() {
        viewModelx.navigationExamination(ExaminationNav.LIST_EXAMINATION_OF_DOCTOR)
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            navigationExamination.observe(this@ExaminationDoctorActivity, Observer {
                when (it) {
                    ExaminationNav.LIST_EXAMINATION_OF_DOCTOR.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutExamination.id,
                            tag = ExaminationDoctorFragment::class.java.simpleName,
                            fragment = ExaminationDoctorFragment(),
                            addToBackStack = false,
                            animateType = AnimateType.NO_ANIMATION
                        )
                    }
                    ExaminationNav.ADD_NEW_EXAMINATION.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutExamination.id,
                            tag = AddNewExaminationDoctorFragment::class.java.simpleName,
                            fragment = AddNewExaminationDoctorFragment(),
                            addToBackStack = false,
                            animateType = AnimateType.NO_ANIMATION
                        )
                    }
                }
            })
        }
    }

    override fun registerOnClick() {
        tooBarExamination.setToolBarOnClick(object : MainToolbar.OnToolBarListener {
            override fun onClickLeft() {
                onBackPressed()
            }

            override fun onClickRight() {
                onBackPressed()
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}