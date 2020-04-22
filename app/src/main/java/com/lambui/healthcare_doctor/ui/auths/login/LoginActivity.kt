package com.lambui.healthcare_doctor.ui.auths.login

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.enums.LoginNav
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.ui.main.MainActivity
import com.lambui.healthcare_doctor.utils.extension.startActivityAtRoot
import com.lambui.healthcare_doctor.widget.toolbar.MainToolbar

class LoginActivity : BaseActivity<LoginVM>() {
    override val layoutID: Int
        get() = R.layout.activity_register
    override val viewModelx: LoginVM by viewModel()

    override fun initialize() {
        initInputLoginInfo()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            navigationLogin.observe(this@LoginActivity, Observer {
                when (it) {
                    LoginNav.CONFIRM_CODE.name -> {
                        handleConfirmCodeFragment()
                    }
                    LoginNav.MAIN.name ->{
                        startActivityAtRoot(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    }
                }
            })
        }
    }

    override fun registerOnClick() {
        handelToolbar()
    }

    private fun initInputLoginInfo() {
        replaceFragmentInActivity(
            frameLayoutRegister.id
            , InputLoginFragment()
            , false
            , InputLoginFragment::class.java.simpleName, AnimateType.NO_ANIMATION
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun handleConfirmCodeFragment() {
        replaceFragmentInActivity(
            frameLayoutRegister.id
            , ConfirmSmsLoginFragment()
            , true
            , ConfirmSmsLoginFragment::class.java.simpleName, AnimateType.SLIDE_TO_LEFT
        )
        toolbar.setTypeToolBar(MainToolbar.TYPE_SHOW_LEFT)
    }

    private fun handelToolbar(){
        toolbar.setToolBarOnClick(object : MainToolbar.OnToolBarListener{
            override fun onClickLeft() {
                onBackPressed()
            }

            override fun onClickRight() {

            }
        })
    }

}