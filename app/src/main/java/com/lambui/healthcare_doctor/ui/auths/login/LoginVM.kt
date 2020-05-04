package com.lambui.healthcare_doctor.ui.auths.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.constant.Constants.INTIAL_DELAY
import com.lambui.healthcare_doctor.constant.Constants.PERIOD
import com.lambui.healthcare_doctor.constant.Constants.TIME_RESET_GET_OTP_SECOND
import com.lambui.healthcare_doctor.data.model.UserModel
import com.lambui.healthcare_doctor.data.source.repositories.TimeCountDownRepository
import com.lambui.healthcare_doctor.data.source.repositories.UserAuthRepository
import com.lambui.healthcare_doctor.enums.LoginNav
import com.lambui.healthcare_doctor.utils.extension.loading
import com.lambui.healthcare_doctor.utils.extension.withScheduler
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LoginVM(
    private val baseSchedulerProvider: BaseSchedulerProvider,
    private val timeCountDownRepository: TimeCountDownRepository,
    private val userAuthRepository: UserAuthRepository
) : BaseViewModel() {
    var navigationLogin = MutableLiveData<String>()
    var loginSuccess = MutableLiveData<UserModel>()
    private val remainD = MutableLiveData<Long>()
        .apply { value = TIME_RESET_GET_OTP_SECOND }
    private val startIntervalS = PublishSubject.create<Unit>()
    private val resendS = PublishSubject.create<Unit>()
    val remainLiveData: LiveData<Long> get() = remainD
    fun start() = startIntervalS.onNext(Unit)
    fun resend() = resendS.onNext(Unit)

    init {
        launchDisposable {
            Observable.merge(startIntervalS.take(1), resendS)
                .toFlowable(BackpressureStrategy.DROP)
                .flatMap(
                    {
                        Observable
                            .interval(INTIAL_DELAY, PERIOD, TimeUnit.SECONDS)
                            .withScheduler(baseSchedulerProvider)
                            .map { TIME_RESET_GET_OTP_SECOND - it }
                            .takeWhile { it >= 0 }
                            .doOnNext { Log.d("ahihi", "ob $it") }
                            .toFlowable(BackpressureStrategy.MISSING)
                    },
                    true,
                    1
                )
                .subscribe {
                    Log.d("@@@@", "Remain $it")
                    remainD.value = it
                }
        }
    }

    fun setNavigationLogin(navigationLoginNav: LoginNav) {
        navigationLogin.value = navigationLoginNav.name
        when (navigationLogin.value) {
            LoginNav.CONFIRM_CODE.name -> {

            }
        }
    }

    fun login(phoneNumber: String, password: String) {
        launchDisposable {
            userAuthRepository.login(phoneNumber, password)
                .withScheduler(baseSchedulerProvider)
                .loading(isLoading)
                .subscribeBy(
                    onSuccess = {
                        loginSuccess.value = it.data
                    },
                    onError = {
                        onError.value = it
                    }
                )
        }
    }
}