package com.lambui.healthcare_doctor.ui.main.notification

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NotificationFragment : BaseFragment<NotificationVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_notification
    override val viewModelx: NotificationVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}