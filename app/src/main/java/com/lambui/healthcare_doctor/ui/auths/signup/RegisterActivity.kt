package com.lambui.healthcare_doctor.ui.auths.signup

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.ui.main.MainActivity
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import com.lambui.healthcare_doctor.utils.extension.startActivityAtRoot
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : BaseActivity<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.activity_register
    override val viewModelx: RegisterVM by viewModel()

    override fun initialize() {
        initInputRegisterInfo()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            navigationRegister.observe(this@RegisterActivity, Observer {
                when (it) {
                    RegisterNav.CONFIRM_SMS.name -> {
                        replaceFragmentInActivity(
                            containerId = R.id.frameLayoutRegister,
                            addToBackStack = false,
                            fragment = ConfirmSmsRegisterFragment(),
                            animateType = AnimateType.SLIDE_TO_LEFT,
                            tag = ConfirmSmsRegisterFragment::class.java.simpleName
                        )
                    }
                    RegisterNav.MAIN.name -> {
                        startActivityAtRoot(
                            this@RegisterActivity,
                            MainActivity::class.java
                        )
                    }
                    RegisterNav.SURVEY_PATIENT.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutRegister.id,
                            addToBackStack = false,
                            fragment = SurveyPatientFragment(),
                            animateType = AnimateType.NO_ANIMATION,
                            tag = SurveyPatientFragment::class.java.simpleName
                        )
                    }
                    RegisterNav.INPUT_INFO.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutRegister.id,
                            addToBackStack = false,
                            fragment = RegisterInputFragment(),
                            animateType = AnimateType.NO_ANIMATION,
                            tag = RegisterInputFragment::class.java.simpleName
                        )
                    }
                }
            })
        }
    }

    override fun registerOnClick() {


    }

    private fun initInputRegisterInfo() {
        replaceFragmentInActivity(
            frameLayoutRegister.id
            , RegisterInputFragment()
            , false
            , RegisterInputFragment::class.java.simpleName, AnimateType.NO_ANIMATION
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}