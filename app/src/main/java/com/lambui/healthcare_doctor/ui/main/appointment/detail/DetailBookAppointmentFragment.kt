package com.lambui.healthcare_doctor.ui.main.appointment.detail

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.dialog.DialogBookAppointmentSuccess
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.OnDialogBookAppointment
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.DateTimeUtils.convertIOStoDefault
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.goBackStepFragment
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.*
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.*
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.cardProfilePatient
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.tvContentRemainder
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.tvContentService
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.tvContentSymptom
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.tvDateSelectAppoint
import kotlinx.android.synthetic.main.fragment_detail_book_appointment.tvTimeSelectAppoint
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.imgProfilePatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvLocationOfPatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvNamePatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvStatus
import kotlinx.android.synthetic.main.item_view_patient_horizental_appointment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class DetailBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_detail_book_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()

    @SuppressLint("SetTextI18n")
    override fun initialize() {
        with(viewModelx) {
            appointmentItem?.let {
                tvDateSelectAppoint.text = convertIOStoDefault(it.dataStartBook ?: "")
                tvTimeSelectAppoint.text = it.timeStartBook
                tvAddress.text = it.doctorId?.address ?: "cập nhật"
                cardProfilePatient.tvLocationOfPatient.text =
                    it.patientId?.address ?: "cập nhật"
                cardProfilePatient.imgProfilePatient.loadImageUrl(it.patientId?.avatar)
                cardProfilePatient.tvNamePatient.text = it.patientId?.fullName
                cardProfilePatient.tvStatus.text = it.status
                cardProfilePatient.tvTimeAppointment.text = it.timeStartBook + "-" +  convertIOStoDefault(it.dataStartBook ?: "")

                val length = it.symptomList?.size ?: 0
                if (length > 0) {
                    val builder = StringBuffer()
                    length?.let { it1 ->
                        for (i in 0 until length) {
                            builder.run { append(it.symptomList?.get(i)?.nameSymptom + "\n") }
                        }
                    }
                    tvContentSymptom.text = builder
                }
                //service
                val lengthService = it.listExamination?.size ?: 0
                if (lengthService > 0) {
                    val builderService = StringBuffer()
                    lengthService?.let { it1 ->
                        for (i in 0 until lengthService) {
                            builderService.run { append(it.listExamination?.get(i)?.serviceName + "\n") }
                        }
                    }
                    tvContentService.text = builderService
                }

                btnReject.isSelected = true
                btnConfirm.setButtonSelected(true)
            }
        }
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            appointmentItem.apply {
                tvDateSelectAppoint.text = this?.dataStartBook
                tvTimeSelectAppoint.text = this?.timeStartBook
                tvAddress.text = this?.doctorId?.specialistModel?.specialistName
                val length = this?.symptomList?.size
                val builder = StringBuffer()
                length?.let {
                    for (i in 0 until length) {
                        builder.append(this?.symptomList?.get(i)?.nameSymptom + "\n")
                    }
                }
                tvContentSymptom.text = builder
                tvContentRemainder.text = this?.timeRemainSendNotification.toString()
            }
            appointmentCancelRequest.observe(this@DetailBookAppointmentFragment, Observer {
                DialogBookAppointmentSuccess(requireContext(), object : OnDialogBookAppointment {
                    override fun onConfirm() {
                        requireActivity().goBackStepFragment(2)
                    }
                }).show()
            })
            appointmentConfirmRequest.observe(this@DetailBookAppointmentFragment, Observer {
                DialogBookAppointmentSuccess(requireContext(), object : OnDialogBookAppointment {
                    override fun onConfirm() {
                        requireActivity().goBackStepFragment(2)
                    }
                }).show()
            })
            onError.observe(this@DetailBookAppointmentFragment, Observer {
                handleApiError(it)
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
                showConfirmDialog(resources.getString(R.string.text_title_dialog_complete),
                    resources.getString(R.string.text_content_dialog_confirm),
                    object : DialogConfirm.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            showConfirmDialog(resources.getString(R.string.text_title_dialog_complete),
                                resources.getString(R.string.text_content_dialog_confirm),
                                object : DialogConfirm.OnButtonClickedListener {
                                    override fun onPositiveClicked() {
                                        viewModelx.confirmRequest()
                                    }

                                    override fun onNegativeClicked() {

                                    }
                                })
                        }

                        override fun onNegativeClicked() {

                        }
                    })
            }
            RxView.clickCheckNetwork(btnReject,
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribe {
                showConfirmDialog(resources.getString(R.string.text_title_dialog_complete),
                    resources.getString(R.string.text_content_dialog_reject),
                    object : DialogConfirm.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            viewModelx.cancelRequest()
                        }

                        override fun onNegativeClicked() {

                        }
                    })
            }
        }
    }
}