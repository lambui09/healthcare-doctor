package com.lambui.healthcare_doctor.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.DoctorModel
import com.lambui.healthcare_doctor.data.model.DoctorModelConvert
import com.lambui.healthcare_doctor.data.source.repositories.DoctorRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class HomeVM(
    private val doctorRepository: DoctorRepository,
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {

    var getDetailDoctorSuccess = MutableLiveData<DoctorModel>()
    fun getDetailDoctor() {
        launchDisposable {
            doctorRepository.getDetailDoctor(getDoctorId())
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        getDetailDoctorSuccess.value = it
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getDoctorId(): String {
        return userLocalRepository.getUserId() ?: ""
    }
}