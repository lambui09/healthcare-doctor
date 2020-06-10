package com.lambui.healthcare_doctor.data.source.repositories

import com.google.gson.Gson
import com.lambui.healthcare_doctor.data.model.CountNotificationModel
import com.lambui.healthcare_doctor.data.model.ListNotificationResponse
import com.lambui.healthcare_doctor.data.source.remote.service.NotificationApi
import io.reactivex.Single

interface NotificationRepository {
    fun getAllNotification(): Single<ListNotificationResponse>
    fun getCountNotifitionUnread(): Single<CountNotificationModel>
}

class NotificationRepositoryImpl(
    private val notificationApi: NotificationApi,
    private val gson: Gson
) : NotificationRepository {
    override fun getAllNotification(): Single<ListNotificationResponse> {
        return notificationApi.getAllNotification().map { it.data }
    }

    override fun getCountNotifitionUnread(): Single<CountNotificationModel> {
        return notificationApi.getUnSeenNotification().map { it.data }
    }
}