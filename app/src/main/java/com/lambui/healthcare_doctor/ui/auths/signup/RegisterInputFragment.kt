package com.lambui.healthcare_doctor.ui.auths.signup

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.utils.RxView
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
        validate()

    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {

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
}