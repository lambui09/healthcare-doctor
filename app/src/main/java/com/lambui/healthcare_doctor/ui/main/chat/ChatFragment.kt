package com.lambui.healthcare_doctor.ui.main.chat

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatFragment : BaseFragment<ChatVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_chat
    override val viewModelx: ChatVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}