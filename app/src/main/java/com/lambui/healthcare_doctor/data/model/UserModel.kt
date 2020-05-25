package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    @Expose
    @field:SerializedName("id")
    var id: String? = null,
    @Expose
    @field:SerializedName("name")
    val name: String? = null,
    @Expose
    @field:SerializedName("phone")
    val phone: String? = null,
    @Expose
    @field:SerializedName("email")
    val email: String? = null,
    @Expose
    @field:SerializedName("birth_day")
    val dateOfBirth: String? = null,
    @Expose
    @field:SerializedName("avatar")
    val avatar: String? = null,
    @Expose
    @field:SerializedName("gender")
    var gender: Boolean? = null,
    @Expose
    @field:SerializedName("address")
    val address: String? = null,
    @Expose
    @field:SerializedName("created_at")
    val createdAt: String? = null,
    @Expose
    @field:SerializedName("is_verified")
    var isVerified: Int? = null,
    @Expose
    @field:SerializedName("unread_count")
    val unreadCount: Int? = null
) : Parcelable