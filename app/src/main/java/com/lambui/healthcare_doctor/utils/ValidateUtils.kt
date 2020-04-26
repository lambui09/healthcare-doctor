package com.lambui.healthcare_doctor.utils

import android.content.Context
import androidx.core.text.isDigitsOnly

object ValidateUtils {
    fun validatePhoneNumber(numberPhone: String, context: Context): String {
        if (numberPhone.isEmpty()) {
            return "Must input phone number"
        }
        if (numberPhone.length < 10) {
            return "Phone number is not valid"
        }
        if (!numberPhone.isDigitsOnly()) {
            return "Input digit"
        }
        return ""
    }

    fun validatePassword(password: String, context: Context): String {
        if (password.isBlank()) {
            return "Must input password"
        }

        if (password.length < 6) {
            return "Length is not valid"
        }
        return ""
    }

    fun validateConfirmPassword(
        password: String,
        confirmPassword: String,
        context: Context
    ): String {
        if (password != confirmPassword) {
            return "ConfirmPassword not same Password"
        }
        return ""
    }

    fun validateSmsCode(smsCode: String, context: Context) : String{
        if (smsCode.length == 5){
            return "Sms not valid"
        }
        return ""
    }
}