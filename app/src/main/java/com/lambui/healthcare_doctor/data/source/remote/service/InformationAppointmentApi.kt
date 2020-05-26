package com.lambui.healthcare_doctor.data.source.remote.service

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.data.model.ListAppointmentResponse
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface InformationAppointmentApi {
    /**
     * get appointment pending
     * */
    @Headers("Content-Type: application/json")
    @GET("appointments")
    fun getAllAppointmentConfirmOfDoctor(
        @Query("doctor_id") doctorId: String,
        @Query("status") status: String
    ): Single<ApiResponse<ListAppointmentResponse>>

    /**
     * get appointment of patient
     * */
    @Headers("Content-Type: application/json")
    @GET("appointments")
    fun getAllAppointmentOfDoctor(
        @Query("doctor_id") doctorId: String
    ): Single<ApiResponse<ListAppointmentResponse>>

    /**
     * cancel appointment of patient
     * */
    @Headers("Content-Type: application/json")
    @PATCH("appointments/{appointment_id}")
    fun cancelRequestAppointment(
        @Path("appointment_id") appointmentId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<AppointmentFullModel>>
    /**
     * confirm appointment of patient
     * */
    @Headers("Content-Type: application/json")
    @PATCH("appointments/{appointment_id}")
    fun confirmRequestAppointment(
        @Path("appointment_id") appointmentId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<AppointmentFullModel>>

    /**
     * complete appointment of patient
     * */
    @Headers("Content-Type: application/json")
    @PATCH("appointments/{appointment_id}")
    fun completeRequestAppointment(
        @Path("appointment_id") appointmentId: String,
        @Body body: JsonObject
    ): Single<ApiResponse<AppointmentFullModel>>

}