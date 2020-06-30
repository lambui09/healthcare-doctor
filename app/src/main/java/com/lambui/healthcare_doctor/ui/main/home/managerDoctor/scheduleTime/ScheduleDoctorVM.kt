package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.WorkingScheduleBody
import com.lambui.healthcare_doctor.data.model.WorkingScheduleFullModel
import com.lambui.healthcare_doctor.data.source.repositories.DoctorRepository
import com.lambui.healthcare_doctor.data.source.repositories.SettingAccountRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class ScheduleDoctorVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val doctorRepository: DoctorRepository,
    private val userLocalRepository: UserLocalRepository,
    private val settingAccountRepository: SettingAccountRepository
) : BaseViewModel() {
    var workingSchedule = MutableLiveData<WorkingScheduleFullModel>()
    var updateWorkingSchedule = MutableLiveData<Boolean>()

    fun getWorkingScheduleOfDoctor() {
        launchDisposable {
            doctorRepository.getWorkingScheduleOfDoctor(getDoctorId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        workingSchedule.value = it
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun createWorkingScheduleDoctor(workingScheduleBody: WorkingScheduleBody) {
        launchDisposable {
            settingAccountRepository.createWorkingSchedule(workingScheduleBody)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        workingSchedule.value = it
                        updateWorkingSchedule.value = true
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }


    private fun getDoctorId(): String {
        return userLocalRepository.getUserId() ?: ""
    }
}