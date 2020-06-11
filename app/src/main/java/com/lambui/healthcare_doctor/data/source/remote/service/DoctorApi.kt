package com.lambui.healthcare_doctor.data.source.remote.service

import com.cloudinary.Api
import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.*

interface DoctorApi {
    /**
     * create service examination
     * */
    @Headers("Content-Type: application/json")
    @POST("examinations")
    fun addNewExamination(
        @Body body: JsonObject
    ): Single<ApiResponse<ExaminationModel>>

    @Headers("Content-Type: application/json")
    @DELETE("examinations/{examination_id}")
    fun deleteExamination(
        @Path("examination_id") examinationId: String
    ): Single<ApiResponse<Any>>

    @Headers("Content-Type: application/json")
    @GET("examinations/{}")
    fun getAllExaminationOfDoctor(
        @Path("examination_id") examinationId: String
    ): Single<ApiResponse<List<ExaminationModel>>>
}