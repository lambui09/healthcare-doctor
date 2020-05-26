package com.lambui.healthcare_doctor.ui.main.chat.chatChannel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.recycleview.BaseLoadMoreAdapter
import com.lambui.healthcare_doctor.constant.Constants
import com.lambui.healthcare_doctor.data.model.ChannelModel
import com.lambui.healthcare_doctor.utils.extension.listen
import com.lambui.healthcare_doctor.utils.extension.loadImageUrlInCircle
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.item_channel_vertical.view.*

class ChannelsChatAdapter(sender: String, context: Context) :
    BaseLoadMoreAdapter<ChannelModel>(context) {
    private var senderBy = sender

    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.item_channel_vertical, parent, false)
        return ItemViewHolder(itemView).listen { position, type ->
            getItem(position)?.let { itemClickListener?.onItemViewClick(it, position) }
        }
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bindData(getItem(position), senderBy)
    }

    override fun getItemViewTypeLM(position: Int): Int {
        return 0
    }
}

class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bindData(channel: ChannelModel?, nameSender: String) {
        channel?.let {
            with(itemView) {
                it.clinics?.let { clinics ->
                    if (clinics.isNotEmpty())
                        tvLastMessage.text = it.latestMessage?.getLastMessage(
                            nameSender,
                            Constants.Manager.KEY_CLINIC
                        )
                }
                tvLabelTime.text = it.latestMessage?.getUpdatedAtDisplay()
                val totalUnread =
                    if (it.unreadMsgCount < MAX_UNREAD)
                        it.unreadMsgCount.toString()
                    else it.unreadMsgCount.toString() + "+"
                tvUnreadMessage.text = totalUnread
                tvUnreadMessage.show(it.isShowUnreadMessage())
                tvRoomName.text = it.getClinicOrManager()?.get(FIRST_INDEX)?.fullName
            }
        }
    }


    companion object {
        private const val MAX_UNREAD = 100
        private const val FIRST_INDEX = 0 // Currently, just have 1 doctor
    }

}
