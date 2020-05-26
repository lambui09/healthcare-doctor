package com.lambui.healthcare_doctor.data.source.remote.service

import com.lambui.healthcare_doctor.data.model.ListNotificationResponse
import com.lambui.healthcare_doctor.data.source.remote.api.response.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface NotificationApi {
    /**
     * get all notifcation
     * */
    @Headers("Content-Type: application/json")
    @GET("notifications")
    fun getAllNotification() : Single<ApiResponse<ListNotificationResponse>>
}