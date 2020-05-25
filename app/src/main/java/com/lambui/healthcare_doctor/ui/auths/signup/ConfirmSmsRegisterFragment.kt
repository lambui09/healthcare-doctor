package com.lambui.healthcare_doctor.ui.auths.signup

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.widget.viewInApp.EditTextCustom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_confirm_sms_register.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConfirmSmsRegisterFragment : BaseFragment<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_confirm_sms_register
    override val viewModelx: RegisterVM by sharedViewModel()

    override fun initialize() {
        listener()
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clicks(btnConfirmCodeSubmit.getViewClick(), false)
                .subscribe {
                    viewModelx.setNavigationRegister(RegisterNav.BIRTH_DAY)
                }
        }
    }

    private fun listener() {
        launchDisposable {
            RxView.search(edtCodeSms.getEditText())
                .map { return@map ValidateUtils.validateSmsCode(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtCodeSms.setTextError(it)
                            btnConfirmCodeSubmit.setButtonSelected(validateInput())
                        } else {
                            edtCodeSms.setVisibilityError(EditTextCustom.GONE)
                            btnConfirmCodeSubmit.setButtonSelected(validateInput())
                        }
                    }
                )
        }
    }

    private fun validateInput(): Boolean {
        var isValid = true
        with(edtCodeSms.getText()) {
            if (ValidateUtils.validateSmsCode(this, requireContext()) != "") {
                isValid = false
            }
        }
        return isValid
    }
}