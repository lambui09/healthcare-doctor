package com.lambui.healthcare_doctor.ui.main.appointment.appointmentPrevious

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.recycleview.BaseLoadMoreAdapter
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.item_view_doctor_horizental_appointment.view.*

class AppointmentHistoryAdapter(context: Context) :
    BaseLoadMoreAdapter<AppointmentFullModel>(context) {
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view_doctor_history_appointment, parent, false)
        return AppointmentHistoryVH(view)
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AppointmentHistoryVH).bindData(getItem(position))
    }

    override fun getItemViewTypeLM(position: Int) = 0
}

class AppointmentHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(appointmentFullModel: AppointmentFullModel?) = with(itemView) {
        with(appointmentFullModel) {
            imgProfileDoctor.loadImageUrl(appointmentFullModel?.patientId?.avatar)
            tvNameDoctor.text = appointmentFullModel?.patientId?.fullName ?: "Bùi Đức Lâm"
            tvLocationOfDoctor.text = appointmentFullModel?.patientId?.address ?: "updating"
            when (this?.status) {
                StatusAppointmentType.COMPLETED.name -> {
                    tvStatus.text = resources.getString(R.string.text_status_completed)
                    tvStatus.isSelected = true
                }
                StatusAppointmentType.CANCELED.name -> {
                    tvStatus.text = resources.getString(R.string.text_status_cancel)
                    tvStatus.isSelected = false
                }
            }
        }
    }
}