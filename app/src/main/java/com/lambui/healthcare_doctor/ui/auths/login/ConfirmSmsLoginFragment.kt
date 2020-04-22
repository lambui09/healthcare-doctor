package com.lambui.healthcare_doctor.ui.auths.login

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.LoginNav
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.widget.viewInApp.EditTextCustom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_confirm_sms_register.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ConfirmSmsLoginFragment : BaseFragment<LoginVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_confirm_sms_register
    override val viewModelx: LoginVM by sharedViewModel()


    override fun initialize() {
        btnConfirmCodeSubmit.setButtonSelected(true)
        viewModelx.start()
        listener()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            remainLiveData.observe(viewLifecycleOwner, Observer {
                tvResetOtpTime.text =
                    resources.getString(R.string.text_description_code_sms_resend, it.toString())
                btnCodeSmsResend.isSelected = it == 0L
            })
        }
    }

    override fun registerOnClick() {
        btnConfirmCodeSubmit.getViewClick().setOnClickListener {
            if (validateInput()) {
                viewModelx.setNavigationLogin(LoginNav.MAIN)
            }
        }
        btnCodeSmsResend.setOnClickListener {
            viewModelx.resend()
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
                        }else{
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