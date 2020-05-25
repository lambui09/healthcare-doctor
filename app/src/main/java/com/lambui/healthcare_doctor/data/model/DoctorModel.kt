package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorModel(
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
    @SerializedName("full_name")
    val fullName: String? = "",
    @Expose
    @SerializedName("location")
    val location : LocationModel? = null,
    @Expose
    @SerializedName("device_token")
    val deviceToken : String? = null,
    @Expose
    @SerializedName("phone_number")
    val phone: String? = "",
    @Expose
    @SerializedName("awards")
    val awards: String? = "",
    @Expose
    @SerializedName("about")
    val about: String? = "",
    @Expose
    @SerializedName("year_experience")
    val year_experience: Int? = 0,
    @Expose
    @SerializedName("specialist")
    var specialistModel: SpecialistModel? = null,
    @Expose
    @SerializedName("examination_list")
    var examinationList: MutableList<String>? = null,
    @Expose
    @SerializedName("avatar")
    var avatarUrl: String? = "",
    @Expose
    @SerializedName("is_active")
    var is_active: Boolean? = null,
    @Expose
    @SerializedName("is_complete")
    var is_complete: Boolean? = null,
    @Expose
    @SerializedName("user_id")
    var userId : String? = null,
    @Expose
    @SerializedName("created_at")
    var createdAt: String? = "",
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @Expose
    @SerializedName("address")
    val address: String? = null

) : Parcelable