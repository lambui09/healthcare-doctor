package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.ui.auths.login.LoginVM
import com.lambui.healthcare_doctor.ui.auths.signup.RegisterVM
import com.lambui.healthcare_doctor.ui.introduce.IntroduceVM
import com.lambui.healthcare_doctor.ui.main.MainVM
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.chat.ChatVM
import com.lambui.healthcare_doctor.ui.main.chat.chatChannel.ChannelVM
import com.lambui.healthcare_doctor.ui.main.chat.chatDetail.ChatDetailVM
import com.lambui.healthcare_doctor.ui.main.home.HomeVM
import com.lambui.healthcare_doctor.ui.main.notification.NotificationVM
import com.lambui.healthcare_doctor.ui.main.setting.SettingVM
import com.lambui.healthcare_doctor.ui.splash.SplashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterVM(get(), get(), get(), get()) }
    viewModel { MainVM() }
    viewModel { LoginVM(get(), get(), get(), get()) }
    viewModel { IntroduceVM() }
    viewModel { HomeVM() }
    viewModel { AppointmentVM() }
    viewModel { ChatVM() }
    viewModel { NotificationVM() }
    viewModel { SettingVM() }
    viewModel { SplashVM() }
    viewModel { ChatDetailVM() }
    viewModel { ChannelVM(get()) }
}