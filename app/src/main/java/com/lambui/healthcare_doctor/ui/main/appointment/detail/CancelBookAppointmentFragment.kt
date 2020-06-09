package com.lambui.healthcare_doctor.ui.main.appointment.detail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.fragment_cancel_book_appointment.*
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CancelBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_cancel_book_appointment
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
                btnCompleteAppointment.setButtonSelected(true)
            }
        }
    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
    }

}