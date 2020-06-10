package com.lambui.healthcare_doctor.ui.main.setting.account

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.DoctorModel
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository
import com.lambui.healthcare_doctor.enums.SettingAccountNav
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider

class AccountVM(private var baseSchedulerProvider: BaseSchedulerProvider,private var tokenRepository : TokenRepository) : BaseViewModel() {
    val navigationSettingAccount = MutableLiveData<String>()
    fun setNavigationSettingAccount(navigationLoginNav: SettingAccountNav) {
        navigationSettingAccount.value = navigationLoginNav.name
    }
    val getProfileData = MutableLiveData<DoctorModel>()
    fun logout(){
        tokenRepository.doLogout()
    }
}