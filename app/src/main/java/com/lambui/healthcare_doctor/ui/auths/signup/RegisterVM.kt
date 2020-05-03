package com.lambui.healthcare_doctor.ui.auths.signup

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.constant.Constants.Manager.KEY_CLINIC
import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepository
import com.lambui.healthcare_doctor.enums.RegisterNav
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.rxkotlin.subscribeBy

class RegisterVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val userAuthRepository: UserAuthRepository
) : BaseViewModel() {
    var navigationRegister = MutableLiveData<String>()
    var userModelDataResponse = MutableLiveData<UserModel>()
    val signUpSuccess = MutableLiveData<UserModel>()
    fun setNavigationRegister(navigation: RegisterNav) {
        navigationRegister.value = navigation.name
    }
    fun signUp(
        phoneNumber: String,
        password: String,
        password_confirmation: String,
        device_token: String
    ) {
        launchDisposable {
            userAuthRepository.signUp(phoneNumber, password, password_confirmation, device_token, KEY_CLINIC)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        signUpSuccess.value = it.data
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }
}