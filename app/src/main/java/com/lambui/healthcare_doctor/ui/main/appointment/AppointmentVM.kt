package com.lambui.healthcare_doctor.ui.main.appointment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.source.repositories.AppointmentRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider

class AppointmentVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val appointmentRepository: AppointmentRepository,
    private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {
    var listAppointmentPending = MutableLiveData<MutableList<AppointmentFullModel>>()
    var listAppointmentOfDoctor = MutableLiveData<MutableList<AppointmentFullModel>>()
    var appointmentCancelRequest = MutableLiveData<AppointmentFullModel>()
    var commentSucces = MutableLiveData<CommentModel>()
    var isRefresh = MutableLiveData<Boolean>()
    var appointmentItem: AppointmentFullModel? = null
    var navigation = MutableLiveData<String>()

    fun setNavigationDetailAppointment(navigationNav: DetailAppointmentNav) {
        navigation.value = navigationNav.name
    }

    fun getAppointmentPending() {
        launchDisposable {
            appointmentRepository.getAppointmentPendingOfPatient(getPatientId(), "PENDING")
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
            appointmentRepository.getAppointmentOfPatient(getPatientId())
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

    fun getPatientId(): String {
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

    fun cancelRequst(appointmentId: String) {
        launchDisposable {
            appointmentRepository.cancelAppointment(appointmentId)
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

    fun reviewAppointment(doctorId: String, content: String, rate_star: Double) {
        launchDisposable {
            appointmentRepository.addCommentToDoctor(doctorId, content, rate_star)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        commentSucces.value = it.commentCreated
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }
}