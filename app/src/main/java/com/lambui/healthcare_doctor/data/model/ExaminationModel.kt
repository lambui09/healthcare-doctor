package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ExaminationModel(
    @Expose
    @SerializedName("_id")
    val id: String? = null,
    @Expose
    @SerializedName("service_name")
    val serviceName: String? = null,
    @Expose
    @SerializedName("image")
    val image: String? = null,
    @Expose
    @SerializedName("price")
    val price: Long?,
    @Expose
    @SerializedName("creator")
    val doctorId: String? = null,
    @Expose
    @SerializedName("createdAt")
    val createAt: String? = null,
    @Expose
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    var isSelect: Boolean = false
) : Parcelable