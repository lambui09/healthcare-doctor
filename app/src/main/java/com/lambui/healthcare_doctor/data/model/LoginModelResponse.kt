package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModelResponse(
    @Expose
    @SerializedName("token")
    var token: String? = null,
    @Expose
    @SerializedName("user_id")
    var userId: String? = null
) : Parcelable
