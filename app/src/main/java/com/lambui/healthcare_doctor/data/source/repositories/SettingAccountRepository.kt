package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleBody
import com.lambui.healthcare_doctor.data.model.WorkingScheduleModel
import com.lambui.healthcare_doctor.data.source.remote.service.SettingAccountApi
import io.reactivex.Single

interface SettingAccountRepository {
    fun createExamination(serviceName: String): Single<ExaminationModel>
    fun createWorkingSchedule(workingScheduleBody: WorkingScheduleBody): Single<WorkingScheduleModel>
}

class SettingAccontRepositoryImpl(private var settingAccountApi: SettingAccountApi, gson: Gson) :
    SettingAccountRepository {
    override fun createExamination(serviceName: String): Single<ExaminationModel> {
        with(JsonObject()) {
            this.addProperty("service_name", serviceName)
            return settingAccountApi.createExamination(this).map { it.data }
        }
    }

    override fun createWorkingSchedule(workingScheduleBody: WorkingScheduleBody): Single<WorkingScheduleModel> {
        return settingAccountApi.createWorkingSchedule(workingScheduleBody).map { it.data }
    }
}