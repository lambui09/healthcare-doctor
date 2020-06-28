package com.lambui.healthcare_doctor.ui.main.appointment.appointmentComplete

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.recycleview.BaseLoadMoreAdapter
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.utils.extension.listen
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.item_view_patient_history_appointment.view.*

class AppointmentHistoryAdapter(context: Context) :
  BaseLoadMoreAdapter<AppointmentFullModel>(context) {
  override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val view = LayoutInflater.from(context)
      .inflate(R.layout.item_view_patient_history_appointment, parent, false)
    return AppointmentHistoryVH(view).listen { position, type ->
      getItem(position)?.let {
        itemClickListener?.onItemViewClick(it, position)
      }
    }
  }

  override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as AppointmentHistoryVH).bindData(getItem(position))
  }

  override fun getItemViewTypeLM(position: Int) = 0
}

class AppointmentHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
  fun bindData(appointmentFullModel: AppointmentFullModel?) = with(itemView) {
    with(appointmentFullModel) {
      imgProfilePatient.loadImageUrl(appointmentFullModel?.patientId?.avatar)
      tvNamePatient.text = appointmentFullModel?.patientId?.fullName ?: "Bùi Đức Lâm"
      tvLocationOfPatient.text = appointmentFullModel?.patientId?.address ?: "updating"
      tvTimeAppointment.text =
        appointmentFullModel?.timeStartBook + "-" + appointmentFullModel?.dataStartBook
      tvStatus.text = resources.getString(R.string.text_status_completed)
      tvStatus.isSelected = true
      containerAppointment.isEnabled = false
    }
  }
}