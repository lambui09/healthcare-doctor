package com.lambui.healthcare_doctor.ui.splash

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.ui.auths.login.LoginActivity
import com.lambui.healthcare_doctor.ui.main.MainActivity
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.startActivityAtRoot
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<SplashVM>() {
  override val layoutID: Int
    get() = R.layout.activity_splash
  override val viewModelx: SplashVM by viewModel()

  override fun initialize() {
    launchDisposable {
      Observable.just(true)
        .delay(3, TimeUnit.SECONDS)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
          viewModelx.checkLogin()
        }
    }
  }

  override fun onSubscribeObserver() {
    with(viewModelx) {
      tokenExist.observe(this@SplashActivity, Observer {
        if (it == true) {
          this@SplashActivity.goTo(MainActivity::class)
          finish()
          return@Observer
        }
      })
      goToLogin()
    }
  }

  override fun registerOnClick() {

  }

  private fun goToLogin() {
    startActivityAtRoot(
      this@SplashActivity,
      LoginActivity::class.java
    )
  }
}