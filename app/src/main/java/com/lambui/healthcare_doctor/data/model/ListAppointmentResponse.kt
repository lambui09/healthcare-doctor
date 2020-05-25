package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListAppointmentResponse(
    @Expose
    @SerializedName("total_size")
    val totalSize: String? = null,
    @Expose
    @SerializedName("data")
    val listAppointment: List<AppointmentFullModel>? = null
): Parcelable