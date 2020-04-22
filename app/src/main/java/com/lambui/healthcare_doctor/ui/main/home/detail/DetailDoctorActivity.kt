package com.lambui.healthcare_doctor.ui.main.home.detail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailDoctorActivity : BaseActivity<DetailDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.activity_detail_doctor
    override val viewModelx: DetailDoctorVM by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}