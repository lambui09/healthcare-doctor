package com.lambui.healthcare_doctor.data.source.remote.service

import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {
    /**
     * api register member
     * */
    @POST("/auth/signup")
    @FormUrlEncoded
    fun register(
        @Field("phone_number") phoneNumber: String,
        @Field("password") password: String,
        @Field("password_confirmation") confirmPassWord: String,
        @Field("device_token") deviceToken: String
    ): Single<ApiResponse<UserModel>>
}