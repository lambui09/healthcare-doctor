package com.lambui.healthcare_doctor.ui.main.appointment.appointmentConfirm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.recycleview.BaseLoadMoreAdapter
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.utils.extension.listen
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.item_view_patient_horizental_appointment.view.*

class ConfirmAppointmentAdapter(context: Context) :
    BaseLoadMoreAdapter<AppointmentFullModel>(context) {
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_patient_horizental_appointment, parent, false)
        return ConfirmAppointmentVH(view).listen { position, type ->
            getItem(position)?.let { itemClickListener?.onItemViewClick(it, position) }
        }
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ConfirmAppointmentVH).bindData(getItem(position))
    }

    override fun getItemViewTypeLM(position: Int) = 0

}

class ConfirmAppointmentVH(view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(appointmentFullModel: AppointmentFullModel?) = with(itemView) {
        with(appointmentFullModel) {
            imgProfileDoctor.loadImageUrl(appointmentFullModel?.patientId?.avatar)
            tvNameDoctor.text = appointmentFullModel?.patientId?.fullName ?: "Bùi Đức Lâm"
            tvLocationOfDoctor.text = appointmentFullModel?.patientId?.address ?: "updating"
            tvStatus.text = resources.getString(R.string.text_status_confirmed)
            tvStatus.isSelected = true

        }
    }

}