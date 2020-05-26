package com.lambui.healthcare_doctor.data.source.remote.service

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.DoctorModel
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleBody
import com.lambui.healthcare_doctor.data.model.WorkingScheduleModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface SettingAccountApi {
    /**
     *
     * */
    @POST("/doctors/{doctor_id}")
    @FormUrlEncoded
    fun updateDoctor(
        @Path("doctor_id") doctorId : String,
        @Body body: JsonObject
    ): Single<ApiResponse<DoctorModel>>

    /**
     * create service doctor
     *
     * */
    @Headers("Content-Type: application/json")
    @POST("examinations")
    fun createExamination(
        @Body body : JsonObject
    ) : Single<ApiResponse<ExaminationModel>>

    /**
     * create working schedule doctor
     *
     * */
    @Headers("Content-Type: application/json")
    @POST("working-schedule")
    fun createWorkingSchedule(
        @Body body: WorkingScheduleBody
    ): Single<ApiResponse<WorkingScheduleModel>>

}