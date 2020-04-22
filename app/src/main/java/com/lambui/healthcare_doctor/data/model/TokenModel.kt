package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenModel(
    @Expose
    @SerializedName("token_type")
    var tokenType: String? = "",
    @Expose
    @SerializedName("token")
    var accessToken: String? = "",
    @Expose
    @SerializedName("expires_in")
    var expires: Double? = 0.0,
    @Expose
    @SerializedName("email_verified")
    var emailVerified: Boolean = false,
    @Expose
    @SerializedName("phone_verified")
    var phoneVerified: Boolean = false
): Parcelable