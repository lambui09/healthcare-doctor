package com.lambui.healthcare_doctor.ui.main.chat.chatChannel

import com.lambui.healthcare_doctor.base.BaseViewModel
import com.lambui.healthcare_doctor.data.model.ChannelModel
import com.lambui.healthcare_doctor.data.model.MessageModel
import com.lambui.healthcare_doctor.utils.liveData.SingleLiveEvent
import com.lambui.healthcare_doctor.utils.rxAndroid.BaseSchedulerProvider

class ChannelVM(baseSchedulerProvider: BaseSchedulerProvider) : BaseViewModel() {
    var channelList = SingleLiveEvent<MutableList<ChannelModel>>()
    init {
        var  mulist = mutableListOf<ChannelModel>()
        val messgage = MessageModel(1,1,"",1,"aaaaavsdvs",0,"duc lam doctor", null,
            null)
        val channelModel = ChannelModel(1,1,"CLINIC",1,null, null,null,messgage)
        mulist.add(channelModel)
        mulist.add(channelModel)
        mulist.add(channelModel)
        channelList.value = mulist
    }
}