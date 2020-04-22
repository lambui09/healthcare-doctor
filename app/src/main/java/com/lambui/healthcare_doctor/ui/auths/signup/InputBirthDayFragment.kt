package com.lambui.healthcare_doctor.ui.auths.signup

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InputBirthDayFragment : BaseFragment<RegisterVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_input_birthday
    override val viewModelx: RegisterVM by sharedViewModel()

    override fun initialize() {


    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {

    }
}