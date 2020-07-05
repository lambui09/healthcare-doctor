package com.lambui.healthcare_doctor.ui.auths.login

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.firebase.iid.FirebaseInstanceId
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.LoginNav
import com.lambui.healthcare_doctor.ui.auths.signup.RegisterActivity
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.StringUtils.isBlank
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.utils.extension.enable
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.setTextWithSpan
import com.lambui.healthcare_doctor.widget.viewInApp.EditTextCustom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_input_login.*
import kotlinx.android.synthetic.main.fragment_input_login.tvRegister
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InputLoginFragment : BaseFragment<LoginVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_input_login
    override val viewModelx: LoginVM by sharedViewModel()

    override fun initialize() {
        edtPhoneNumber.getEditText().setText("04012312306")
        edtPassword.getEditText().setText("aa123123")
        handleRegister()
        validate()
        Log.d(
            "@@@@@",
            "Token on the 1st " + FirebaseInstanceId.getInstance().getToken() ?: "NULLLL"
        )
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            loginSuccess.observe(this@InputLoginFragment, Observer {
                val deviceToken = FirebaseInstanceId.getInstance().getToken() ?: ""
                val doctorId = getUserId() ?: ""
                if (!isBlank(deviceToken) && !isBlank(doctorId)) {
                    viewModelx.updateDeviceToken(deviceToken, doctorId)
                }
                viewModelx.setNavigationLogin(LoginNav.CONFIRM_CODE)
            })
            onError.observe(this@InputLoginFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {
        btnLoginSubmit.getViewClick().setOnClickListener {
            if (validateInput()) {
                val phoneNumber = edtPhoneNumber.getEditText().text.toString()
                val passWord = edtPassword.getEditText().text.toString()
                viewModelx.login(phoneNumber, passWord)
            }
        }
    }

    private fun handleRegister() {
        tvRegister.setTextWithSpan(
            ContextCompat.getColor(requireContext(), R.color.K_8A173833_gray),
            resources.getString(R.string.text_sign_up)
        ) {
            tvRegister.enable(false)
            this.goTo(RegisterActivity::class)
        }
    }

    private fun validate() {
        launchDisposable {
            RxView.search(edtPhoneNumber.getEditText())
                .map { return@map ValidateUtils.validatePhoneNumber(it, requireContext()) }
                .doOnNext {
                    Log.d("@@@@@", it.toString())
                }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtPhoneNumber.setTextError(it)
                            btnLoginSubmit.setButtonSelected(validateInput())
                        } else {
                            edtPhoneNumber.setVisibilityError(EditTextCustom.GONE)
                            btnLoginSubmit.setButtonSelected(validateInput())
                        }
                    }
                )

            RxView.search(edtPassword.getEditText())
                .map { return@map ValidateUtils.validatePassword(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtPassword.setTextError(it)
                            btnLoginSubmit.setButtonSelected(validateInput())

                        } else {
                            edtPassword.setVisibilityError(EditTextCustom.GONE)
                            btnLoginSubmit.setButtonSelected(validateInput())
                        }
                    }
                )
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        with(edtPhoneNumber.getText()) {
            if (ValidateUtils.validatePhoneNumber(this, requireContext()) != "") {
                isValid = false
            }
        }
        with(edtPassword.getText()) {
            if (ValidateUtils.validatePassword(this, requireContext()) != "") {
                isValid = false
            }
        }
        return isValid
    }
}