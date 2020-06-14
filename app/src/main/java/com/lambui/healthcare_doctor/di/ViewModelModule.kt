package com.lambui.healthcare_doctor.di

import com.lambui.healthcare_doctor.ui.auths.login.LoginVM
import com.lambui.healthcare_doctor.ui.auths.signup.RegisterVM
import com.lambui.healthcare_doctor.ui.introduce.IntroduceVM
import com.lambui.healthcare_doctor.ui.main.MainVM
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.chat.ChatVM
import com.lambui.healthcare_doctor.ui.main.chat.chatDetail.ChatDetailVM
import com.lambui.healthcare_doctor.ui.main.home.HomeVM
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.ExaminationDoctorVM
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime.ScheduleDoctorVM
import com.lambui.healthcare_doctor.ui.main.notification.NotificationVM
import com.lambui.healthcare_doctor.ui.main.setting.SettingVM
import com.lambui.healthcare_doctor.ui.main.setting.account.AccountVM
import com.lambui.healthcare_doctor.ui.splash.SplashVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { RegisterVM(get(), get(), get(), get()) }
    viewModel { MainVM(get(), get()) }
    viewModel { LoginVM(get(), get(), get(), get()) }
    viewModel { IntroduceVM() }
    viewModel { HomeVM() }
    viewModel { AppointmentVM(get(), get(), get()) }
    viewModel { ChatVM(get()) }
    viewModel { NotificationVM(get(), get()) }
    viewModel { SettingVM(get(), get()) }
    viewModel { SplashVM() }
    viewModel { ChatDetailVM(get(), get()) }
    viewModel { AccountVM(get(), get()) }
    viewModel { ScheduleDoctorVM() }
    viewModel { ExaminationDoctorVM(get(), get(), get()) }
}