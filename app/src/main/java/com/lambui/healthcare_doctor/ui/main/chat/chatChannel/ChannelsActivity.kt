package com.lambui.healthcare_doctor.ui.main.chat.chatChannel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.base.recycleview.OnItemClickListener
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_DETAIL_MESSAGE
import com.lambui.healthcare_doctor.data.model.ChannelModel
import com.lambui.healthcare_doctor.ui.main.chat.chatChannel.adapter.ChannelsChatAdapter
import com.lambui.healthcare_doctor.ui.main.chat.chatDetail.ChatDetailActivity
import com.lambui.healthcare_doctor.utils.extension.notNull
import com.lambui.healthcare_doctor.widget.recycleView.SuperRecyclerView
import kotlinx.android.synthetic.main.activity_channel.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChannelsActivity : BaseActivity<ChannelVM>(), OnItemClickListener<ChannelModel>,
    SuperRecyclerView.LoadDataListener {
    override val layoutID: Int
        get() = R.layout.activity_channel
    private lateinit var adapter: ChannelsChatAdapter
    private lateinit var senderBy: String
    override val viewModelx: ChannelVM by viewModel()

    override fun initialize() {
        setupAdapter()
    }

    override fun onSubscribeObserver() {
        with(viewModelx){
            channelList.observe(this@ChannelsActivity, Observer {

            })
        }
    }

    override fun registerOnClick() {
    }

    private fun setupAdapter() {
        adapter = ChannelsChatAdapter(senderBy, this@ChannelsActivity).apply {
            registerItemClickListener(this@ChannelsActivity)
        }

        rvChannels.apply {
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(this@ChannelsActivity))
            setLoadDataListener(this@ChannelsActivity)
            setHasFixedSize(true)
            disableAnimateRecyclerView()
        }
    }

    override fun onItemViewClick(item: ChannelModel, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable(EXTRA_DETAIL_MESSAGE, item)
        startActivity(getInstance(this@ChannelsActivity, item))
    }

    override fun onLoadMore(page: Int) {
    }

    override fun onRefreshData() {

    }

    companion object {
        fun getInstance(context: Context, channel: ChannelModel?): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            channel.notNull {
                val bundle = Bundle()
                bundle.putInt(EXTRA_DETAIL_MESSAGE, it.id)
                intent.putExtras(bundle)
            }

            return intent
        }
    }
}