package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.*
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import io.reactivex.Single

interface UserAuthRepository {
    fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        role: String
    ): Single<ApiResponse<SignUpModelResponse>>

    fun login(
        phoneNumber: String,
        password: String
    ): Single<ApiResponse<LoginModelResponse>>

    fun updateInformationPatient(
        patientId: String,
        firstName: String, lastName: String, birthDay: String, gender: String
    ): Single<ApiResponse<DoctorModel>>

    fun updateAddressPatient(
        patientId: String,
        location: LocationModel
    ): Single<ApiResponse<DoctorModel>>

    fun updateDeviceToken(
        patientId: String,
        deviceToken: String
    ): Single<ApiResponse<DoctorModel>>
}

class UserAuthRepositoryImpl(
    private val authApi: AuthApi,
    private val gson: Gson
) : UserAuthRepository {
    override fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        role: String
    ): Single<ApiResponse<SignUpModelResponse>> {
        with(JsonObject()) {
            this.addProperty("phone_number", phoneNumber)
            this.addProperty("password", password)
            this.addProperty("confirm_password", password_confirmation)
            this.addProperty("role", role)
            return authApi.register(this)
        }
    }

    override fun login(
        phoneNumber: String,
        password: String
    ): Single<ApiResponse<LoginModelResponse>> {
        with(JsonObject()) {
            this.addProperty("phone_number", phoneNumber)
            this.addProperty("password", password)
            return authApi.login(this)
        }
    }

    override fun updateInformationPatient(
        doctorId: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        gender: String
    ): Single<ApiResponse<DoctorModel>> {
        with(JsonObject()) {
            this.addProperty("first_name", firstName)
            this.addProperty("last_name", lastName)
            this.addProperty("birth_day", birthDay)
            this.addProperty("gender", gender)
            return authApi.updateInformationPatient(doctorId, this)
        }
    }

    override fun updateDeviceToken(
        doctorId: String,
        deviceToken: String
    ): Single<ApiResponse<DoctorModel>> {
        with(JsonObject()) {
            this.addProperty("device_token", deviceToken)
            return authApi.updateDeviceToken(doctorId, this)
        }
    }

    override fun updateAddressPatient(
        doctorId: String,
        location: LocationModel
    ): Single<ApiResponse<DoctorModel>> {
        return authApi.updateAddressPatient(doctorId, location)
    }
}