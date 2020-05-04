package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import io.reactivex.Single

interface UserAuthRepository {
    fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        device_token: String,
        role: String
    ): Single<ApiResponse<UserModel>>

    fun login(
        phoneNumber: String,
        password: String
    ): Single<ApiResponse<UserModel>>
}

class UserAuthRepositoryImpl(
    private val authApi: AuthApi
) : UserAuthRepository {
    override fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        device_token: String,
        role: String
    ): Single<ApiResponse<UserModel>> {
        with(JsonObject()) {
            this.addProperty("phone_number", phoneNumber)
            this.addProperty("password", password)
            this.addProperty("confirm_password", password_confirmation)
            this.addProperty("role", role)
            return authApi.register(this)
        }
    }

    override fun login(phoneNumber: String, password: String): Single<ApiResponse<UserModel>> {
        with(JsonObject()) {
            this.addProperty("phone_number", phoneNumber)
            this.addProperty("password", password)
            return authApi.login(this)
        }
    }
}