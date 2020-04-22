package com.lambui.healthcare_doctor.data.model

import android.os.Parcelable
import android.text.format.DateUtils.isToday
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.lambui.healthcare_doctor.constant.Constants
import com.lambui.healthcare_doctor.constant.Constants.BLANK
import com.lambui.healthcare_doctor.constant.Constants.DEFAULT_ID
import com.lambui.healthcare_doctor.constant.Constants.INT_DEFAULT
import com.lambui.healthcare_doctor.enums.MessageType
import com.lambui.healthcare_doctor.enums.SendType
import com.lambui.healthcare_doctor.utils.DateTimeUtils
import com.lambui.healthcare_doctor.utils.DateTimeUtils.DATE_TIME_FORMAT_MM_DD_HH_MM_JP
import com.lambui.healthcare_doctor.utils.DateTimeUtils.DATE_TIME_WITH_TIMEZONE
import com.lambui.healthcare_doctor.utils.DateTimeUtils.TIME_FORMAT_HH_MM
import com.lambui.healthcare_doctor.utils.DateTimeUtils.isToday
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MessageModel(
    @Expose
    @SerializedName("id")
    var id: Int? = DEFAULT_ID,
    @Expose
    @SerializedName("channel_id")
    val channelId: Int? = INT_DEFAULT,
    @Expose
    @SerializedName("created_at")
    val createdAt: String = "",
    @Expose
    @SerializedName("type_message")
    val typeMessage: Int = SendType.TEXT.value,
    @Expose
    @SerializedName("message_content")
    val message_content: String? = null,
    @Expose
    @SerializedName("sender_id")
    val senderId: Int? = INT_DEFAULT,
    @Expose
    @SerializedName("sender_by")
    val senderBy: String? = null,
    @Expose
    @SerializedName("image_url")
    val imageUrl: String? = null,
    var messageType: Int? = MessageType.HEADER_DATE.value
) : Parcelable {

    fun getLastMessage(sender: String, key: String): String {
        val nameSender = when (senderBy) {
            KEY_USER -> sender
            KEY_MANAGER -> when (key
                ) {
                Constants.Manager.KEY_CLINIC -> DOCTOR
                else -> ADMIN
            }
            else -> Constants.STR_DEFAULT
        }
        if (typeMessage == SendType.IMAGE.value) {
            return "$nameSender Sent photos"
        }
        return "$nameSender: $message_content"

    }
    //Todo update timezone
    fun getUpdatedAtDisplay() : String{
        val hour = DateTimeUtils.convertUTCTimeToLocalTimeDisplay(createdAt,
            DATE_TIME_WITH_TIMEZONE,
            TIME_FORMAT_HH_MM, Locale.ENGLISH) ?: BLANK

        val dateTime = DateTimeUtils.convertUTCTimeToLocalTimeDisplay(createdAt,
            DATE_TIME_WITH_TIMEZONE,
            DATE_TIME_FORMAT_MM_DD_HH_MM_JP, Locale.ENGLISH) ?: BLANK
        return if (isToday(createdAt)) hour else dateTime
    }

    companion object {
        const val KEY_USER = "user"
        const val KEY_MANAGER = "manager"
        const val DOCTOR = "Clinic"
        const val ADMIN = "Admin"
    }
}