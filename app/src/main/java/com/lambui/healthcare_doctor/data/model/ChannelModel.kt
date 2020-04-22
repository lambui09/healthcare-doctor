package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambui.healthcare_doctor.constant.Constants
import com.lambui.healthcare_doctor.constant.Constants.DEFAULT_ID
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChannelModel(
    @Expose
    @SerializedName("id")
    var id: Int = DEFAULT_ID,
    @Expose
    @SerializedName("ownerable_id")
    val ownerableId: Int,
    @Expose
    @SerializedName("ownerable_type")
    val ownerable_type: String,
    @Expose
    @SerializedName("type")
    val type: Int,
    @Expose
    @SerializedName("users")
    val users: List<UserModel>? = null,
    @Expose
    @SerializedName("clinics")
    val clinics: List<DoctorModel>? = null,
    @Expose
    @SerializedName("managers")
    val managers: List<DoctorModel>? = null,
    @Expose
    @SerializedName("latest_message")
    var latestMessage: MessageModel? = null,
    @Expose
    @SerializedName("message_unread_count")
    var unreadMsgCount: Int = 0
): Parcelable{

    fun isShowUnreadMessage(): Boolean {
        return unreadMsgCount != 0
    }

    fun getClinicOrManager() : List<DoctorModel>? {
        if (!clinics.isNullOrEmpty())
            return clinics
        if (!managers.isNullOrEmpty())
            return managers
        return null
    }
    fun getRoomImage(): String {
        if (!managers.isNullOrEmpty())
            return managers[0].getImageDisplay()
        return Constants.STR_DEFAULT
    }

}