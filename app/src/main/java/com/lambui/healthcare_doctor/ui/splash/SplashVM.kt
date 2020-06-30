package com.lambui.healthcare_doctor.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.source.repositories.TokenRepository

class SplashVM(private val tokenRepository: TokenRepository) : BaseViewModel() {
  var tokenExist = MutableLiveData<Boolean>()

  fun checkLogin(){
    Log.d("####", "${tokenRepository.getToken().toString()}")
    if (tokenRepository.getToken() != null) {
      //todo update because call api update deivce token
       tokenExist.postValue(true)
    }else{
      tokenExist.postValue(false)
    }
  }
}