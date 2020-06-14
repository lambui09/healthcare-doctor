package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleFullModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleModel
import com.lambui.healthcare_doctor.data.source.remote.service.DoctorApi
import io.reactivex.Single

interface DoctorRepository {
    fun addNewExamination(service: String): Single<ExaminationModel>
    fun deleteExamination(examinationId: String): Single<Any>
    fun getAllExaminationOfDoctor(doctorId: String): Single<List<ExaminationModel>>
    fun getWorkingScheduleOfDoctor(doctorId: String): Single<WorkingScheduleFullModel>
}

class DoctorRepositoryImpl(private val doctorApi: DoctorApi, private val gson: Gson) :
    DoctorRepository {
    override fun addNewExamination(service: String): Single<ExaminationModel> {
        with(JsonObject()) {
            this.addProperty("service_name", service)
            return doctorApi.addNewExamination(this).map { it.data }
        }
    }

    override fun deleteExamination(examinationId: String): Single<Any> {
        return doctorApi.deleteExamination(examinationId).map { it.data }
    }

    override fun getAllExaminationOfDoctor(doctorId: String): Single<List<ExaminationModel>> {
        return doctorApi.getAllExaminationOfDoctor(doctorId).map { it.data }
    }

    override fun getWorkingScheduleOfDoctor(doctorId: String): Single<WorkingScheduleFullModel> {
        return doctorApi.getWorkingScheduleOfDoctor(doctorId).map { it.data }
    }
}