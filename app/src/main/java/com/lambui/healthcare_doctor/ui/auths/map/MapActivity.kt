package com.lambui.healthcare_doctor.ui.auths.map

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapActivity: BaseActivity<MapVM>() {
    override val layoutID: Int
        get() = R.layout.activity_map
    override val viewModelx: MapVM by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}