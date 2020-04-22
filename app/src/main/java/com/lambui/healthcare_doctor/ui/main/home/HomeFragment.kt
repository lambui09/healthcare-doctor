package com.lambui.healthcare_doctor.ui.main.home

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<HomeVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_home
    override val viewModelx: HomeVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}