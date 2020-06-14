package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkingScheduleFullModel(
    @Expose
    @SerializedName("_id")
    var id: String? = null,
    @Expose
    @SerializedName("list_time")
    var listTime: List<String>?,
    @Expose
    @SerializedName("from_date")
    var fromDate: String? = null,
    @Expose
    @SerializedName("end_date")
    var endDate: String? = null,
    @Expose
    @SerializedName("start_time")
    var startTime: String? = null,
    @Expose
    @SerializedName("end_time")
    var endTime: String? = null,
    @Expose
    @SerializedName("duration_default_appointment")
    var duration_default_appointment: Int? = null,
    @Expose
    @SerializedName("doctor_id")
    var doctorId: String? = null,
    @Expose
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @Expose
    @SerializedName("updatedAt")
    var updatedAt: String? = null

) : Parcelable