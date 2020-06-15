package com.lambui.healthcare_doctor.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MessageModel(
    var id: String,
    @Expose(serialize = false, deserialize = false)
    var conversationId: String,
    var content: String,
    var createAt: Timestamp = Timestamp.now(),
    var sender: String
): Parcelable {
    constructor(): this(id = "", conversationId = "", content = "", sender = "sender")
}