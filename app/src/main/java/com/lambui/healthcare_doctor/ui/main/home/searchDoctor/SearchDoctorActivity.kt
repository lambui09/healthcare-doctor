package com.lambui.healthcare_doctor.ui.main.home.searchDoctor

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchDoctorActivity : BaseActivity<SearchDoctorVM>(){
    override val layoutID: Int
        get() = R.layout.activity_search_doctor
    override val viewModelx: SearchDoctorVM by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }

}