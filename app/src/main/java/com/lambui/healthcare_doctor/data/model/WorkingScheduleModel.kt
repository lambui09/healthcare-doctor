package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkingScheduleModel(
    @Expose
    @SerializedName("_id")
    val id: String? = null,
    @Expose
    @SerializedName("name")
    val specialistName: String? = null,
    @Expose
    @SerializedName("icon")
    val image: String? = null,
    @Expose
    @SerializedName("creator")
    val creator: String? = null
) : Parcelable