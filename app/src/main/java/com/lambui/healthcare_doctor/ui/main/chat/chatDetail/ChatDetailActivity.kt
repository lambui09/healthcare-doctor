package com.lambui.healthcare_doctor.ui.main.chat.chatDetail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatDetailActivity : BaseActivity<ChatDetailVM>() {
    override val layoutID: Int
        get() = R.layout.activity_detail_chat
    override val viewModelx: ChatDetailVM by viewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}