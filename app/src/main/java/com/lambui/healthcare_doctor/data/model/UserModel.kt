package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    @Expose
    @field:SerializedName("id")
    var id: Int = -1,
    @Expose
    @field:SerializedName("name")
    val name: String? = null,
    @Expose
    @field:SerializedName("name_kana")
    val nameKana: String? = null,
    @Expose
    @field:SerializedName("recipient_name")
    val recipientName: String? = null,
    @Expose
    @field:SerializedName("phone")
    val phone: String? = null,
    @Expose
    @field:SerializedName("email")
    val email: String? = null,
    @Expose
    @field:SerializedName("date_of_birth")
    val dateOfBirth: String? = null,
    @Expose
    @field:SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @field:SerializedName("gender")
    var gender: Boolean? = null,
    @Expose
    @field:SerializedName("height")
    val height: String? = null,
    @Expose
    @field:SerializedName("postal_number")
    val postalNumber: String? = null,
    @Expose
    @field:SerializedName("prefecture_id")
    val prefectureId: String? = null,
    @Expose
    @field:SerializedName("city_id")
    val cityId: String? = null,
    @Expose
    @field:SerializedName("address")
    val address: String? = null,
    @Expose
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @Expose
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @Expose
    @field:SerializedName("is_verified")
    var isVerified: Int? = null,
    @Expose
    @field:SerializedName("plan_expired_date")
    val planExpiredDate: String? = null,
    @Expose
    @field:SerializedName("unread_count")
    val unreadCount: Int? = null
) : Parcelable