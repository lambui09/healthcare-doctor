package com.lambui.healthcare_doctor.ui.main.appointment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.data.source.repositories.AppointmentRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.enums.DetailAppointmentNav
import com.lambui.healthcare_doctor.utils.StringUtils.isBlank
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class AppointmentVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val appointmentRepository: AppointmentRepository,
    private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {
    var listAppointmentPending = MutableLiveData<MutableList<AppointmentFullModel>>()
    var listAppointmentOfDoctor = MutableLiveData<MutableList<AppointmentFullModel>>()
    var appointmentCancelRequest = MutableLiveData<AppointmentFullModel>()
    var appointmentConfirmRequest = MutableLiveData<AppointmentFullModel>()
    var appointmentCompleteRequest = MutableLiveData<AppointmentFullModel>()
    var isRefresh = MutableLiveData<Boolean>()
    var appointmentItem: AppointmentFullModel? = null
    var navigation = MutableLiveData<String>()

    fun setNavigationDetailAppointment(navigationNav: DetailAppointmentNav) {
        navigation.value = navigationNav.name
    }

    fun getAppointmentPending() {
        launchDisposable {
            appointmentRepository.getAppointmentConfirmOfDoctor(getDoctorId(), "PENDING")
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listAppointmentPending.value = it.listAppointment?.toMutableList()
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getAppointmentOfDoctor() {
        launchDisposable {
            appointmentRepository.getAppointmentOfDoctor(getDoctorId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listAppointmentOfDoctor.value = it.listAppointment?.toMutableList()
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getDoctorId(): String {
        var id = userLocalRepository.getUserLocal()?.id
        if (isBlank(id)) {
            id = userLocalRepository.getUserId()
        }
        Log.d("@@@@", "USER_ID" + "${id}")
        return id ?: ""
    }

    fun setIsRefresh(isRefreshData: Boolean) {
        isRefresh.value = isRefreshData
    }

    fun cancelRequest() {
        launchDisposable {
            appointmentRepository.cancelAppointment(getAppointmentId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        appointmentCancelRequest.value = it
                    },
                    onError = { onError.value = it }
                )
        }
    }

    fun confirmRequest() {
        launchDisposable {
            appointmentRepository.confirmAppointment(getAppointmentId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        appointmentConfirmRequest.value = it
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun completeRequest() {
        launchDisposable {
            appointmentRepository.completeAppointment(getAppointmentId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        appointmentCompleteRequest.value = it
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getAppointmentId(): String {
        return ""
    }
}