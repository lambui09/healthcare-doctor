package com.lambui.healthcare_doctor.ui.main.notification

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.NotificationModel
import com.lambui.healthcare_doctor.data.source.repositories.NotificationRepository
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class NotificationVM(private val notificationRepository: NotificationRepository,
                     private val baseSchedulerProvider: BaseSchedulerProvider
) : BaseViewModel() {
    var listNotification = MutableLiveData<MutableList<NotificationModel>>()

    fun getNotification() {
        launchDisposable {
            notificationRepository.getAllNotification()
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listNotification.postValue(it.listNotification?.toMutableList())
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

}