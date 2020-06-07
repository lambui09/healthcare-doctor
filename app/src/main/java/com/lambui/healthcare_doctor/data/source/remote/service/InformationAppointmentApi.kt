package com.lambui.healthcare_doctor.data.source.remote.service

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.data.model.GetAppointmentBody
import com.lambui.healthcare_doctor.data.model.ListAppointmentResponse
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface InformationAppointmentApi {
    /**
     * get appointment pending
     * */
    @Headers("Content-Type: application/json")
    @POST("appointments/list")
    fun getAllAppointmentConfirmOfDoctor(
        @Body body: GetAppointmentBody
    ): Single<ApiResponse<ListAppointmentResponse>>

    /**
     * get appointment of patient
     * */
    @Headers("Content-Type: application/json")
    @POST("appointments/list")
    fun getAllAppointmentOfDoctor(
        @Body body: GetAppointmentBody
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