package com.lambui.healthcare_doctor.ui.main.appointment.detail

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.dialog.DialogBookAppointmentSuccess
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.dialog.OnDialogBookAppointment
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.DateTimeUtils
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.goBackStepFragment
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.*
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.tvContentService
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.tvDateSelectAppoint
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.tvTimeSelectAppoint
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.*
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.imgProfilePatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvLocationOfPatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvNamePatient
import kotlinx.android.synthetic.main.item_view_doctor_complete_appointment.view.tvStatus
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CompleteBookAppointmentFragment : BaseFragment<AppointmentVM>() {
  override val layoutID: Int
    get() = R.layout.fragment_complete_book_appointment
  override val viewModelx: AppointmentVM by sharedViewModel()

  @SuppressLint("SetTextI18n")
  override fun initialize() {
    with(viewModelx) {
      appointmentItem?.let {
        tvDateSelectAppoint.text = DateTimeUtils.convertIOStoDefault(it.dataStartBook ?: "")
        tvTimeSelectAppoint.text = it.timeStartBook
        tvAddressDoctor.text = it.doctorId?.address ?: "not updating"
        tvContentService.text = it.listExamination?.get(0)?.serviceName ?: ""
        patientItemComplete.tvLocationOfPatient.text =
          it.patientId?.address ?: "not updating"
        patientItemComplete.imgProfilePatient.loadImageUrl(it.patientId?.avatar)
        patientItemComplete.tvNamePatient.text = it.patientId?.fullName
        patientItemComplete.tvStatus.text = it.status
        patientItemComplete.tvTimeAppointmentDoctor.text =
          it.timeStartBook + "-" + DateTimeUtils.convertIOStoDefault(
            it.dataStartBook ?: ""
          )
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
          lengthService.let { it1 ->
            for (i in 0 until lengthService) {
              builderService.run { append(it.listExamination?.get(i)?.serviceName + "\n") }
            }
          }
          tvContentService.text = builderService
        }else{
          tvContentService.text = "cập nhật"
        }
        btnCompleteAppointment.setButtonSelected(true)
      }
    }
  }

  override fun onSubscribeObserver() {
    with(viewModelx) {
      appointmentCompleteRequest.observe(this@CompleteBookAppointmentFragment, Observer {
        DialogBookAppointmentSuccess(requireContext(), object : OnDialogBookAppointment {
          override fun onConfirm() {
            requireActivity().goBackStepFragment(2)
          }
        }).show()
      })
      onError.observe(this@CompleteBookAppointmentFragment, Observer {
        handleApiError(it)
      })
    }
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