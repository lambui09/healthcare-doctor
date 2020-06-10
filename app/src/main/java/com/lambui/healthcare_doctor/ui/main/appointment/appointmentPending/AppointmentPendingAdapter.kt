package com.lambui.healthcare_doctor.ui.main.appointment.appointmentPending

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
import kotlinx.android.synthetic.main.item_view_patient_horizental_appointment.view.*

class AppointmentPendingAdapter(context: Context) : BaseLoadMoreAdapter<AppointmentFullModel>(context) {
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_view_patient_horizental_appointment, parent, false)
        return UpcomingVH(view).listen { position, type ->
            getItem(position)?.let {
                itemClickListener?.onItemViewClick(it, position)
            }
        }
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UpcomingVH).bindData(getItem(position))
    }

    override fun getItemViewTypeLM(position: Int) = 0
}

class UpcomingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(appointmentModel: AppointmentFullModel?) = with(itemView) {
        with(appointmentModel) {
            imgProfilePatient.loadImageUrl(appointmentModel?.patientId?.avatar)
            tvNamePatient.text = appointmentModel?.patientId?.fullName ?: "Bùi Đức Lâm"
            tvLocationOfPatient.text = appointmentModel?.patientId?.address ?: "updating"
            tvStatus.text = resources.getText(R.string.text_status_pending)
            tvStatus.isSelected = false
        }
    }
}