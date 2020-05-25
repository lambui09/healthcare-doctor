package com.lambui.healthcare_doctor.data.source.remote.service

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.*
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface AuthApi {
    /**
     * api register member
     * */
    @POST("auth/signup")
    fun register(
        @Body body: JsonObject
    ): Single<ApiResponse<SignUpModelResponse>>

    /**
     * api login member
     * */
    @POST("auth/login")
    fun login(
        @Body body: JsonObject
    ): Single<ApiResponse<LoginModelResponse>>

    /**
     * update patient
     * */
    @PATCH("doctors/{doctor_id}")
    fun updateInformationPatient(
        @Path("doctor_id") doctorId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<DoctorModel>>

    /**
     * update patient
     * */
    @Headers("Content-Type: application/json")
    @PATCH("doctors/{doctor_id}")
    fun updateAddressPatient(
        @Path("doctor_id") doctorId: String,
        @Body body: LocationModel
    ): Single<ApiResponse<DoctorModel>>

    @PATCH("doctors/{doctor_id}")
    fun updateDeviceToken(
        @Path("doctor_id") doctorId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<DoctorModel>>
}