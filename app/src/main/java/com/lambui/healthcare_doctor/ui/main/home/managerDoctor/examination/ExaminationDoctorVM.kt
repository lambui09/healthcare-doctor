package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.ExaminationModel
import com.lambui.healthcare_doctor.data.source.repositories.DoctorRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.enums.ExaminationNav
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class ExaminationDoctorVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val doctorRepository: DoctorRepository,
    private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {
    var listExamination = MutableLiveData<MutableList<ExaminationModel>>()
    var isDeleteExamination = MutableLiveData<Boolean>()
    var addNewExamination = MutableLiveData<ExaminationModel>()
    var navigationExamination = MutableLiveData<String>()

    fun navigationExamination(navigation: ExaminationNav) {
        navigationExamination.value = navigation.name
    }

    fun getListExaminationOfDoctor() {
        launchDisposable {
            doctorRepository.getAllExaminationOfDoctor(getIdOfDoctor())
                .withScheduler(baseSchedulerProvider)
                .subscribeBy(
                    onError = {
                        onError.value = it
                    },
                    onSuccess = {
                        listExamination.value = it?.data?.toMutableList()
                    }
                )
        }
    }

    fun deleteExamination(position: Int) {
        if (!listExamination.value.isNullOrEmpty()) {
            val itemRemove = listExamination.value?.get(position)
            launchDisposable {
                doctorRepository.deleteExamination(itemRemove?.id ?: "")
                    .withScheduler(baseSchedulerProvider)
                    .subscribeBy(
                        onSuccess = {
                            val newExaminationLists = listExamination.value
                            newExaminationLists?.remove(itemRemove)
                            listExamination.value = newExaminationLists
                            isDeleteExamination.value = true
                        },
                        onError = {
                            onError.value = it
                        }
                    )
            }
        }
    }

    fun createNewExamination(serviceName: String) {
        launchDisposable {
            doctorRepository.addNewExamination(serviceName)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        addNewExamination.value = it
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun getIdOfDoctor(): String {
        return userLocalRepository.getUserId() ?: ""
    }

}