package com.lambui.healthcare_doctor.ui.main.home.searchDoctor

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchDoctorFragment : BaseFragment<SearchDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_search_doctor
    override val viewModelx: SearchDoctorVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}