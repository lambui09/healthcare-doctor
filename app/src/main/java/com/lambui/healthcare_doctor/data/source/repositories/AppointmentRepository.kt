package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.data.model.ListAppointmentResponse
import com.lambui.healthcare_doctor.data.source.remote.service.InformationAppointmentApi
import io.reactivex.Single

interface AppointmentRepository {
    fun getAppointmentConfirmOfDoctor(
        doctorId: String,
        status: String
    ): Single<ListAppointmentResponse>

    fun getAppointmentOfDoctor(doctorId: String): Single<ListAppointmentResponse>

    fun cancelAppointment(appointmentId: String): Single<AppointmentFullModel>

    fun confirmAppointment(appointmentId: String): Single<AppointmentFullModel>

    fun completeAppointment(appointmentId: String): Single<AppointmentFullModel>
}

class AppointmentRepositoryImpl(
    private val informationAppointmentApi: InformationAppointmentApi,
    private val gson: Gson
) : AppointmentRepository {
    override fun getAppointmentConfirmOfDoctor(
        doctorId: String, status: String
    ): Single<ListAppointmentResponse> {
        return informationAppointmentApi.getAllAppointmentConfirmOfDoctor(doctorId, status)
            .map { it.data }
    }

    override fun getAppointmentOfDoctor(doctorId: String): Single<ListAppointmentResponse> {
        return informationAppointmentApi.getAllAppointmentOfDoctor(doctorId).map { it.data }
    }

    override fun cancelAppointment(appointmentId: String): Single<AppointmentFullModel> {
        with(JsonObject()) {
            this.addProperty("status", "CANCELED")
            return informationAppointmentApi.cancelRequestAppointment(appointmentId, this)
                .map { it.data }
        }
    }

    override fun confirmAppointment(appointmentId: String): Single<AppointmentFullModel> {
        with(JsonObject()) {
            this.addProperty("status", "CONFIRMED")
            return informationAppointmentApi.confirmRequestAppointment(appointmentId, this)
                .map { it.data }
        }
    }

    override fun completeAppointment(appointmentId: String): Single<AppointmentFullModel> {
        with(JsonObject()) {
            this.addProperty("status", "COMPLETED")
            return informationAppointmentApi.completeRequestAppointment(appointmentId, this)
                .map { it.data }
        }
    }
}