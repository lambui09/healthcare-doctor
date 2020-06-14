package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.ValidateUtils
import com.lambui.healthcare_doctor.utils.extension.goBackStepFragment
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_add_new_examination_doctor.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AddNewExaminationDoctorFragment : BaseFragment<ExaminationDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_add_new_examination_doctor
    override val viewModelx: ExaminationDoctorVM by sharedViewModel()

    override fun initialize() {
        launchDisposable {
            RxView.search(edtInputExamination.getEditText())
                .map { return@map ValidateUtils.validateName(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it == "") {
                            btnDone.setButtonSelected(validateContent())
                        } else {
                            btnDone.setButtonSelected(validateContent())
                        }
                    }
                )
        }
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            addNewExamination.observe(this@AddNewExaminationDoctorFragment, Observer {
                requireActivity().goBackStepFragment(1)
            })
        }
    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clickCheckNetwork(btnDone.getViewClick(), object : RxView.IListenerCheckNetWork {
                override fun showError(isCheckNetwork: Boolean) {
                    showErrorInternet()
                }
            }).subscribe {
                if (validateContent()) {
                    val newExamination = edtInputExamination.getEditText().text.trim().toString()
                    viewModelx.createNewExamination(newExamination)
                }
            }
        }
    }


    private fun validateContent(): Boolean {
        with(edtInputExamination.getEditText().text.trim().toString()) {
            if (this.isEmpty()) {
                return false
            }
        }
        return true
    }
}