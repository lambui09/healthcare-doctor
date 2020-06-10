package com.lambui.healthcare_doctor.ui.main.setting

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.DoctorModel
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider

class SettingVM(
    private var tokenRepository: TokenRepository,
    private var baseSchedulerProvider: BaseSchedulerProvider
) : BaseViewModel() {
    val getProfileData = MutableLiveData<DoctorModel>()
    fun logout() {
        tokenRepository.doLogout()
    }
}