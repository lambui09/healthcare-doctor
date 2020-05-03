package com.lambui.healthcare_doctor.data.source.remote.service

import com.google.gson.JsonObject
import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    /**
     * api register member
     * */
    @POST("auth/signup")
    fun register(
        @Body body: JsonObject
    ): Single<ApiResponse<UserModel>>

    /**
     * api login member
     * */
    @POST("auth/login")
    fun login(
        @Body body: JsonObject
    ): Single<ApiResponse<UserModel>>
}