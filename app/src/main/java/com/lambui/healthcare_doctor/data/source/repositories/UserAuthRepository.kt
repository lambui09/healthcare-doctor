package com.lambui.healthcare_doctor.data.source.repositories

import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import com.lambui.healthcare_doctor.data.source.remote.service.AuthApi
import io.reactivex.Single

interface UserAuthRepository {
    fun signUp (
        phoneNumber : String,
        password : String,
        password_confirmation : String,
        device_token : String
    ) : Single<ApiResponse<UserModel>>
}
class UserAuthRepositoryImpl(
    private val authApi: AuthApi
) : UserAuthRepository{
    override fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        device_token: String
    ): Single<ApiResponse<UserModel>> {
        return authApi.register(phoneNumber,password, password_confirmation, device_token)
    }
}