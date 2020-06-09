package com.lambui.healthcare_doctor.ui.main.appointment.detail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.*
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CompleteBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_complete_book_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()

    override fun initialize() {
        with(viewModelx) {
            appointmentItem?.let {
                tvDateSelectAppoint.text = it.dataStartBook
                tvTimeSelectAppoint.text = it.timeStartBook
                tvAddressDoctor.text = it.doctorId?.address ?: "not updating"
                tvContentService.text = it.listExamination?.get(0)?.serviceName ?: ""
                cardProfilePatient.tvLocationOfPatient.text =
                    it.patientId?.address ?: "not updating"
                cardProfilePatient.imgProfilePatient.loadImageUrl(it.patientId?.avatar)
                cardProfilePatient.tvNamePatient.text = it.patientId?.fullName
                cardProfilePatient.tvStatus.text = it.status
            }
        }
    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clickCheckNetwork(
                btnCompleteAppointment.getViewClick(),
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribeBy {
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