package com.lambui.healthcare_doctor.ui.main.appointment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.data.model.GetAppointmentBody
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
    var listAppointmentCompleteOfDoctor = MutableLiveData<MutableList<AppointmentFullModel>>()
    var listAppointmentCancelOfDoctor = MutableLiveData<MutableList<AppointmentFullModel>>()
    var listAppointmentConfirmOfDoctor = MutableLiveData<MutableList<AppointmentFullModel>>()
    var appointmentCancelRequest = MutableLiveData<Any>()
    var appointmentConfirmRequest = MutableLiveData<Any>()
    var appointmentCompleteRequest = MutableLiveData<Any>()
    var isRefresh = MutableLiveData<Boolean>()
    var appointmentItem: AppointmentFullModel? = null
    var navigation = MutableLiveData<String>()

    fun setNavigationDetailAppointment(navigationNav: DetailAppointmentNav) {
        navigation.value = navigationNav.name
    }

    fun getAllAppointmentPending() {
        val statusForUpComing: List<String> = listOf("PENDING")
        launchDisposable {
            appointmentRepository.getAppointmentConfirmOfDoctor(GetAppointmentBody(statusForUpComing))
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

    fun getAllAppointmentComplete() {
        val statusForHistory: List<String> = listOf("COMPLETED")
        launchDisposable {
            appointmentRepository.getAppointmentOfDoctor(GetAppointmentBody(statusForHistory))
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listAppointmentCompleteOfDoctor.value = it.listAppointment?.toMutableList()
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getAllAppointmentCancel() {
        val statusForHistory: List<String> = listOf("CANCELED")
        launchDisposable {
            appointmentRepository.getAppointmentOfDoctor(GetAppointmentBody(statusForHistory))
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listAppointmentCancelOfDoctor.value = it.listAppointment?.toMutableList()
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getAllAppointmentConfirm() {
        val statusForHistory: List<String> = listOf("CONFIRMED")
        launchDisposable {
            appointmentRepository.getAppointmentOfDoctor(GetAppointmentBody(statusForHistory))
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        listAppointmentConfirmOfDoctor.value = it.listAppointment?.toMutableList()
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

    //update appointmentID
    fun getAppointmentId(): String {
        val id = appointmentItem?.id
        if (id != null) {
            return id
        }
        return ""
    }
}