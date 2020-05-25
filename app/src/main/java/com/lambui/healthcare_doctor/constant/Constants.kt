package com.lambui.healthcare_doctor.constant

import android.net.Uri

object Constants {
    const val API_PREFIX = "api/"
    const val BASE_URL = "api/"
    const val QUERY = "=?"
    const val BLANK = " "
    const val MINIMUM_MINUTE_DURATION = 20
    const val INDEX_INSERT_CHARACTER_FIRST = 3
    const val INDEX_INSERT_CHARACTER_SECOND = 8
    const val DIVIDER_CHARACTER = "-"
    const val EMPTY = ""
    const val SHARDED_PRE_FLOAT_DEFAULT = 0f
    const val SHARDED_PRE_INT_DEFAULT = 0
    const val SHARDED_PRE_BOOLEAN_DEFAULT = false
    const val SHARDED_PRE_LONG_DEFAULT = 0L
    const val EXTRA_TITLE = "extra-title"
    const val EXTRA_ARGS = "EXTRA_ARGS"
    const val ZERO_LONG = 0L
    const val NUMBER_ONE = 1
    const val PERIOD = 1L
    const val INTIAL_DELAY = 0L
    const val TIME_RESET_GET_OTP_SECOND: Long = 20
    const val PAGE_DEFAULT = 1
    const val POSITION_DEFAULT = -1
    const val DEFAULT_ID = -1
    const val INT_DEFAULT = -1
    const val STR_DEFAULT = ""
    var TEMPORARY_URI_FILE: Uri? = null
    const val PICK_IMAGE_RESULT = "pickImageResult.jpeg"
    var TEMPORARY_PATH_FILE: String? = null
    const val FIRST_INDEX = 0
    const val EXT_GIF = ".gif"
    const val MB = 1024
    const val LENGTH = 1000
    const val QUALITY = 75
    const val STRING_DEFAULT = ""
    const val LENGTH_INPUT_BIRTHDAY = 10
    const val POSITION_SEPARATOR_FIRST = 4
    const val POSITION_SEPARATOR_DASH = 3
    const val POSITION_SEPARATOR_SECOND = 7
    const val TIME_OUT_EVENT_CLICK = 1000L
    const val LATITUDE_EXAMPLE = 16.073078
    const val LONGITUDE_EXAMPLE = 108.213784
    const val NEAR_BY_VALUE = "NEAR_BY_VALUE"
    const val TIME_SAVE_REMAIN_TEMP = ""
    const val LIST_TEMPTOM = ""

    object Manager {
        const val KEY_CLINIC = "DOCTOR"
        const val KEY_ADMIN = "KEY_ADMIN"
        const val KEY_DOCTOR = "DOCTOR"
    }
}