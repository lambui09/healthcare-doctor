package com.lambui.healthcare_doctor.ui.splash

import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository

class SplashVM(private val tokenRepository: TokenRepository) : BaseViewModel() {
  var tokenExist = MutableLiveData<Boolean>()

  init {
    tokenExist.postValue(false)
  }

  fun checkLogin(){
    if (tokenRepository.getToken() != null) {
       tokenExist.postValue(true)
    }
    tokenExist.postValue(false)
  }
}