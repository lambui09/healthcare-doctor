package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PatientModel(
    @Expose
    @SerializedName("_id")
    var id: String? = null,
    @Expose
    @SerializedName("first_name")
    var firstName: String? = null,
    @Expose
    @SerializedName("last_name")
    var lastName: String? = null,
    @Expose
    @SerializedName("birth_day")
    var birthDay: String? = null,
    @Expose
    @SerializedName("gender")
    var gender: String? = null,
    @Expose
    @SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @SerializedName("is_active")
    val is_active: Boolean?,
    @Expose
    @SerializedName("is_complete")
    val is_complete: Boolean?,
    @Expose
    @SerializedName("push_notification_enabled")
    var push_notification_enabled: Boolean?,
    @Expose
    @SerializedName("unseen_notification_count")
    val countNotRead: Int?,
    @Expose
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @Expose
    @SerializedName("user_id")
    var userId: String? = null,
    @Expose
    @SerializedName("location")
    var location: LocationModel? = null,
    @Expose
    @SerializedName("device_token")
    var deviceToken: String? = null

) : Parcelable
