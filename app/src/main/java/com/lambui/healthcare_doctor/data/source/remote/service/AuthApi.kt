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
    @Headers("Content-Type: application/json")
    @POST("auth/signup")
    fun register(
        @Body body: JsonObject
    ): Single<ApiResponse<SignUpModelResponse>>

    /**
     * api login member
     * */
    @Headers("Content-Type: application/json")
    @POST("auth/login")
    fun login(
        @Body body: JsonObject
    ): Single<ApiResponse<LoginModelResponse>>

    /**
     * update patient
     * */
    @Headers("Content-Type: application/json")
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

    @Headers("Content-Type: application/json")
    @PATCH("doctors/{doctor_id}")
    fun updateDeviceToken(
        @Path("doctor_id") doctorId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<DoctorModel>>

    @Headers("Content-Type: application/json")
    @GET("patients/{patient_id}")
    fun getPatientInfo(
        @Path("patient_id") patientId: String
    ): Single<ApiResponse<PatientModel>>

    @Headers("Content-Type: application/json")
    @GET("doctors/{doctor_id}")
    fun getDoctorInfo(
        @Path("doctor_id") doctor_id: String
    ): Single<ApiResponse<DoctorModel>>
}