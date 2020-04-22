package com.lambui.healthcare_doctor.ui.main.setting

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingFragment : BaseFragment<SettingVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_setting
    override val viewModelx: SettingVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}