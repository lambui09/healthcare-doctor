package com.lambui.healthcare_doctor.ui.auths.signup

import android.view.View
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.GenderType
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.utils.EditTextInputValidate
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.widget.viewInApp.EditTextCustom
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_input_birthday.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InputBirthDayFragment : BaseFragment<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_input_birthday
    override val viewModelx: RegisterVM by sharedViewModel()
    private var mGender = GenderType.MALE

    override fun initialize() {
        EditTextInputValidate(edtInputBirthDay.getEditText()).listen()
        tvDone.isSelected = false
        listener()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            userModelDataResponse.observe(this@InputBirthDayFragment, Observer {
                viewModelx.setNavigationRegister(RegisterNav.MAP)
            })
            onError.observe(this@InputBirthDayFragment, Observer {
                handleApiError(it)
            })
        }

    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clicks(tvMale, false)
                .subscribe {
                    handleGenderSelected(it)
                }

            RxView.clicks(tvFemale, false)
                .subscribe {
                    handleGenderSelected(it)
                }

            RxView.clicks(tvUnknown, false)
                .subscribe {
                    handleGenderSelected(it)
                }
        }

        launchDisposable {
            RxView.clicks(tvDone, false)
                .subscribe {
                    if (checkValidate()) {
                        val patientId = viewModelx.getDoctorId() ?: ""
                        val firstName = edtFirstNamePatient.getEditText().text.toString()
                        val lastName = edtLastNamePatient.getEditText().text.toString()
                        val birthDay = edtInputBirthDay.getEditText().text.toString()
                        viewModelx.updateInformationPatient(
                            patientId,
                            firstName,
                            lastName,
                            birthDay,
                            mGender.name
                        )
                    }
                }
        }
    }

    private fun handleGenderSelected(view: View) {
        handleGenderDefault()
        when (view) {
            tvMale -> {
                with(tvMale) {
                    isSelected = true
                    mGender = GenderType.MALE
                    setTextColor(resources.getColor(R.color.colorWhite))
                }
            }
            tvFemale -> {
                with(tvFemale) {
                    isSelected = true
                    mGender = GenderType.FEMALE
                    setTextColor(resources.getColor(R.color.colorWhite))
                }
            }

            tvUnknown -> {
                with(tvUnknown) {
                    isSelected = true
                    mGender = GenderType.OTHERS
                    setTextColor(resources.getColor(R.color.colorWhite))
                }
            }
        }
    }

    private fun handleGenderDefault() {
        with(tvMale) {
            isSelected = false
            setTextColor(resources.getColor(R.color.K_C3CBCA_gray))
        }
        with(tvFemale) {
            isSelected = false
            setTextColor(resources.getColor(R.color.K_C3CBCA_gray))
        }
        with(tvUnknown) {
            isSelected = false
            setTextColor(resources.getColor(R.color.K_C3CBCA_gray))
        }
    }

    private fun checkValidate(): Boolean {
        var isValid = true
        with(edtFirstNamePatient.getText()) {
            if (ValidateUtils.validateName(this, requireContext()) != "") {
                isValid = false
            }
        }
        with((edtLastNamePatient.getText())) {
            if (ValidateUtils.validateName(this, requireContext()) != "") {
                isValid = false
            }
        }

        with(edtInputBirthDay.getText()) {
            if (ValidateUtils.validateBirthDay(edtInputBirthDay.getText(), requireContext()) != ""
            ) {
                isValid = false
            }
        }
        return isValid
    }

    private fun listener() {
        launchDisposable {
            RxView.search(edtFirstNamePatient.getEditText())
                .map { return@map ValidateUtils.validateName(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtFirstNamePatient.setTextError(it)
                            tvDone.isSelected = checkValidate()
                        } else {
                            edtFirstNamePatient.setVisibilityError(EditTextCustom.GONE)
                            tvDone.isSelected = checkValidate()
                        }
                    }
                )
            RxView.search(edtLastNamePatient.getEditText())
                .map { return@map ValidateUtils.validateName(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtLastNamePatient.setTextError(it)
                            tvDone.isSelected = checkValidate()
                        } else {
                            edtLastNamePatient.setVisibilityError(EditTextCustom.GONE)
                            tvDone.isSelected = checkValidate()
                        }
                    }
                )
            RxView.search(edtInputBirthDay.getEditText())
                .map {
                    return@map ValidateUtils.validateBirthDay(it, requireContext())
                }
                .subscribeBy(
                    onNext = {
                        if (it != "") {
                            edtInputBirthDay.setTextError(it)
                            tvDone.isSelected = checkValidate()
                        } else {
                            edtInputBirthDay.setVisibilityError(EditTextCustom.GONE)
                            tvDone.isSelected = checkValidate()
                        }
                    }
                )
        }
    }
}