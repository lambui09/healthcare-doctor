package com.lambui.healthcare_doctor.ui.main

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.source.repositories.NotificationRepository
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class MainVM(
    private val notificationRepository: NotificationRepository,
    private val baseSchedulerProvider: BaseSchedulerProvider
) : BaseViewModel() {
    var countNotification = MutableLiveData<Int>()
    fun getNumNotificationUnseen() {
        launchDisposable {
            notificationRepository.getCountNotifitionUnread().withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        countNotification.value = it?.count
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

}