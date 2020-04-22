package com.lambui.healthcare_doctor.enums

enum class MessageType(val value : Int) {
    HEADER_DATE(0),
    OWNER_MESSAGE_TEXT(1),
    OWNER_MESSAGE_IMAGE(2),
    GUEST_MESSAGE_TEXT(3),
    GUEST_MESSAGE_IMAGE(4),
}