package com.lambui.healthcare_doctor.ui.auths.signup

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.constant.Constants.Manager.KEY_CLINIC
import com.lambui.healthcare_doctor.constant.Constants.Manager.KEY_DOCTOR
import com.lambui.healthcare_doctor.data.model.DoctorModel
import com.lambui.healthcare_doctor.data.model.LocationModel
import com.lambui.healthcare_doctor.data.model.SignUpModelResponse
import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserLocalRepository
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.liveData.SingleLiveEvent
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class RegisterVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val userAuthRepository: UserAuthRepository,
    private val tokenRepository: TokenRepository,
    private val userLocalRepository: UserLocalRepository
) : BaseViewModel() {
    var navigationRegister = MutableLiveData<String>()
    var userModelDataResponse = SingleLiveEvent<DoctorModel>()
    var userModelUpdateAddressResponse = SingleLiveEvent<DoctorModel>()
    val signUpSuccess = MutableLiveData<SignUpModelResponse>()
    var address: LocationModel? = null
    fun setNavigationRegister(navigation: RegisterNav) {
        navigationRegister.value = navigation.name
    }
    fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String
    ) {
        launchDisposable {
            userAuthRepository.signUp(
                    phoneNumber,
                    password,
                    password_confirmation,
                    KEY_DOCTOR
                )
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        signUpSuccess.value = it.data
                        it.data?.token?.let { it1 ->
                            tokenRepository.saveToken(it1)
                        }
                        it.data?.user?.let {
                            userLocalRepository.saveUserLocal(it)
                        }
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun updateInformationPatient(
        doctorId: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        gender: String
    ) {
        launchDisposable {
            userAuthRepository.updateInformationPatient(
                    doctorId, firstName, lastName, birthDay, gender
                ).withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        userModelDataResponse.value = it.data
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun updateDeviceToken(deviceToken: String, doctorId: String) {
        launchDisposable {
            userAuthRepository.updateDeviceToken(doctorId, deviceToken)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        userModelDataResponse.value = it.data
                        userLocalRepository.saveUserLocal(it.data)
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }

    fun updateAddressPatient(patientId: String, location: LocationModel) {
        launchDisposable {
            userAuthRepository.updateAddressPatient(patientId, location)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        userModelUpdateAddressResponse.value = it.data
                    },
                    onError = {
                        onError.value = it
                    }
                )

        }
    }

    fun getDoctorId(): String? {
        return userLocalRepository.getUserLocal()?.id
    }

    fun setLocation(location: LocationModel) {
        address = location
    }
}