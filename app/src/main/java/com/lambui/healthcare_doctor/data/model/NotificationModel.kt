package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotificationModel(
    @Expose
    @SerializedName("_id")
    val id: String? = null,
    @Expose
    @SerializedName("title")
    var title: String? = null,
    @Expose
    @SerializedName("body")
    var body: String? = null,
    @Expose
    @SerializedName("has_read")
    var hasRead: Boolean?,
    @Expose
    @SerializedName("patient")
    var patient: PatientModel?,
    @Expose
    @SerializedName("doctor")
    var doctor: DoctorModelConvert?,
    @Expose
    @SerializedName("isSent")
    var isSent: Boolean?,
    @Expose
    @SerializedName("createdAt")
    var createdAt: String? = null,
    @Expose
    @SerializedName("updatedAt")
    var updatedAt: String? = null
) : Parcelable