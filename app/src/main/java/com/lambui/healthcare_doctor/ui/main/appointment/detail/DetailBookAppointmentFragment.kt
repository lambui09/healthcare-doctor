package com.lambui.healthcare_doctor.ui.main.appointment.detail

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.RxView
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_detail_book_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()

    override fun initialize() {

    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            appointmentItem.apply {
                tvDateSelectAppoint.text = this?.dataStartBook
                tvTimeSelectAppoint.text = this?.timeStartBook
                tvContentSpecialistDoctor.text = this?.doctorId?.specialistModel?.specialistName
                val length = this?.symptomlist?.size
                val builder = StringBuffer()
                length?.let {
                    for (i in 0 until length) {
                        builder.append(this?.symptomlist?.get(i) + "\n")
                    }
                }
                tvContentFeeService.text = builder
                tvContentRemainder.text = this?.timeRemainSendNotification.toString()
            }
            appointmentCancelRequest.observe(this@DetailBookAppointmentFragment, Observer {
                requireActivity().finish()
            })
        }
    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clickCheckNetwork(btnConfirm.getViewClick(),
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribe {
                viewModelx.confirmRequest()
            }
            RxView.clickCheckNetwork(btnReject.getViewClick(),
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribe {
                viewModelx.cancelRequest()
                showConfirmDialog(resources.getString(R.string.text_title_dialog_complete),
                    resources.getString(R.string.text_content_dialog_complete),
                    object : DialogConfirm.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            viewModelx.completeRequest()
                        }

                        override fun onNegativeClicked() {

                        }
                    })
            }
        }
    }
}