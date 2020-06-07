package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetAppointmentBody(
    @Expose
    @SerializedName("status")
    var listStatus: List<String>? = null
) : Parcelable