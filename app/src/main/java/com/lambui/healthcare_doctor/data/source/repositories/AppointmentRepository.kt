package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import io.reactivex.Single

interface AppointmentRepository {
    fun getAppointmentPendingOfPatient(
        patientId: String,
        status: String
    ): Single<ListAppointmentResponse>

    fun getAppointmentOfPatient(patientId: String): Single<ListAppointmentResponse>

    fun cancelAppointment(appointmentId: String): Single<AppointmentFullModel>
}

class AppointmentRepositoryImpl(
    private val informationAppointmentApi: InformationAppointmentApi,
    private val gson: Gson
) : AppointmentRepository {
    override fun getAppointmentPendingOfPatient(
        patientId: String, status: String
    ): Single<ListAppointmentResponse> {
        return informationAppointmentApi.getAllAppointmentPendingOfPatient(patientId, status)
            .map { it.data }
    }

    override fun getAppointmentOfPatient(patientId: String): Single<ListAppointmentResponse> {
        return informationAppointmentApi.getAllAppointmentOfPatient(patientId).map { it.data }
    }

    override fun cancelAppointment(appointmentId: String): Single<AppointmentFullModel> {
        return informationAppointmentApi.cancelRequestAppointment(appointmentId).map { it.data }
    }

    override fun addCommentToDoctor(
        doctorId: String,
        content: String,
        rateStar: Double
    ): Single<CommentResponse> {
        with(JsonObject()) {
            this.addProperty("content", content)
            this.addProperty("rate_star", rateStar)
            return informationAppointmentApi.addCommentToDoctor(doctorId, this).map { it.data }
        }

    }
}