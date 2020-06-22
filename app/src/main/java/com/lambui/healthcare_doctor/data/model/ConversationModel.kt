package com.lambui.healthcare_app.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConversationModel(
    var id: String,
    var members: MutableList<String> = mutableListOf("sender", "receiver"),
    var memberAvatars: Map<String, String> = HashMap(),
    var memberNames: Map<String, String> = HashMap(),
    var lastSender: String = "null",
    var seen: Boolean = false,
    var lastMessage: String = "",
    var createAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now()
) : Parcelable {
    constructor() : this(id = "") {

    }

    fun compareTo(another: ConversationModel): Boolean {
        return members == another.members
                && memberAvatars == another.memberAvatars
                && memberNames == another.memberNames
                && lastSender == another.lastSender
                && seen == another.seen
                && lastMessage == another.lastMessage
                && createAt == another.createAt
                && updateAt == another.updateAt
    }
}