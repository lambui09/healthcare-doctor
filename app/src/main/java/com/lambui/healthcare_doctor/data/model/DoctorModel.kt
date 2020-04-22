package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorModel(
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("medical_department")
    val medicalDepartment: String,
    @Expose
    @SerializedName("name")
    val name: String? = "",
    @Expose
    @SerializedName("email")
    val email: String? = "",
    @Expose
    @SerializedName("phone")
    val phone: String? = "",
    @Expose
    @SerializedName("mobile_phone")
    val mobilePhone: String? = "",
    @Expose
    @SerializedName("fax")
    val fax: String? = "",
    @Expose
    @SerializedName("postal_code")
    val postalCode: String? = "",
    @Expose
    @SerializedName("prefecture_id")
    val prefectureId: Int,
    @Expose
    @SerializedName("city_id")
    val cityId: Int,
    @Expose
    @SerializedName("address")
    val address: String? = "",
    @Expose
    @SerializedName("building_name")
    val buildingName: String? = "",
    @Expose
    @SerializedName("introduction")
    val introduction: String? = "",
    @Expose
    @SerializedName("image")
    val image: String? = "",
    @Expose
    @SerializedName("image_url")
    val imageUrl: String? = "",
    @Expose
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = "",
    @Expose
    @SerializedName("created_at")
    val createdAt: String? = "",
    @Expose
    @SerializedName("updated_at")
    val updatedAt: String? = ""
): Parcelable{
    fun getImageDisplay(): String {
        if (avatarUrl.isNullOrEmpty()) return imageUrl.toString()
        return avatarUrl
    }
}