package com.lambui.healthcare_doctor.ui.auths.signup

import androidx.lifecycle.Observer
import com.google.firebase.iid.FirebaseInstanceId
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.StringUtils.isBlank
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.widget.viewInApp.EditTextCustom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_input_register.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterInputFragment : BaseFragment<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_input_register
    override val viewModelx: RegisterVM by sharedViewModel()
    override fun initialize() {
        tvStep1.isSelected = true
        validate()
        listener()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            signUpSuccess.observe(this@RegisterInputFragment, Observer {
                val deviceToken = FirebaseInstanceId.getInstance().getToken() ?: ""
                val doctorId = getDoctorId() ?: ""
                if (isBlank(deviceToken) && isBlank(doctorId)) {
                    viewModelx.updateDeviceToken(deviceToken, doctorId)
                }
                viewModelx.setNavigationRegister(RegisterNav.CONFIRM_SMS)
            })
            onError.observe(this@RegisterInputFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clicks(btnRegisterSubmit.getViewClick(), false)
                .subscribe {
                    if (checkValidate()) {

                        val phoneNumber = edtPhoneNumber.getEditText().text.toString()
                        val password = edtPassword.getEditText().text.toString()
                        val passwordConfirm = edtConfirmPassword.getEditText().text.toString()
                        viewModelx.signUp(phoneNumber, password, passwordConfirm)
                    }
                }
        }

    }

    private fun validate() {
        launchDisposable {
            RxView.search(edtPhoneNumber.getEditText())
                .map { return@map ValidateUtils.validatePhoneNumber(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtPhoneNumber.setTextError(it)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        } else {
                            edtPhoneNumber.setVisibilityError(EditTextCustom.GONE)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        }
                    }
                )
            RxView.search(edtPassword.getEditText())
                .map { return@map ValidateUtils.validatePassword(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtPassword.setTextError(it)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        } else {
                            edtPassword.setVisibilityError(EditTextCustom.GONE)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        }
                    }
                )
            RxView.search(edtConfirmPassword.getEditText())
                .map {
                    return@map ValidateUtils.validateConfirmPassword(
                        edtPassword.getEditText().text.toString(), it, requireContext()
                    )
                }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtConfirmPassword.setTextError(it)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        } else {
                            edtConfirmPassword.setVisibilityError(EditTextCustom.GONE)
                            btnRegisterSubmit.setButtonSelected(checkValidate())
                        }
                    }
                )
        }
    }

    private fun checkValidate(): Boolean {
        var isValid = true
        with(edtPhoneNumber.getText()) {
            if (ValidateUtils.validatePhoneNumber(this, requireContext()) != "") {
                isValid = false
            }
        }
        with((edtPassword.getText())) {
            if (ValidateUtils.validatePassword(this, requireContext()) != "") {
                isValid = false
            }
        }
        with(edtConfirmPassword.getText()) {
            if (ValidateUtils.validateConfirmPassword(
                    edtPassword.getText(),
                    this,
                    requireContext()
                ) != ""
            ) {
                isValid = false
            }
        }
        return isValid
    }

    private fun listener() {

    }
}