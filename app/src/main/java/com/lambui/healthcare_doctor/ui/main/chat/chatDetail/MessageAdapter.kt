package com.lambui.healthcare_doctor.ui.main.chat.chatDetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.data.model.MessageModel
import kotlinx.android.synthetic.main.item_message_receiver.view.*
import kotlinx.android.synthetic.main.item_message_sender.view.*
import kotlinx.android.synthetic.main.item_message_sender.view.chat_message

class MessageAdapter(var context: Context, var sender: String) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var messages: MutableList<MessageModel> = ArrayList()
    var pendingWriteMessageIds: MutableList<String> = mutableListOf()
    var seen: Boolean = false
    var partnerAvatar: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_account)

    private val VIEW_TYPE_SENDER = 0
    private val VIEW_TYPE_RECEIVER = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENDER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_sender, parent, false)
            SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message_receiver, parent, false)
            ReceiverViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]

        if (holder is SenderViewHolder) {
            with(holder.itemView) {
                chat_message.text = message.content
                tvSending.visibility =
                    if (pendingWriteMessageIds.indexOf(message.id) == -1)
                        View.GONE
                    else View.VISIBLE
                if (position == messages.size - 1) {
                    tvSeen.visibility =
                        if (seen) View.VISIBLE
                        else View.GONE
                } else
                    tvSeen.visibility = View.GONE
            }
        } else {
            with(holder.itemView) {
                imvAvatar.setImageDrawable(partnerAvatar)
                chat_message.text = message.content
            }
        }
    }

    fun updateLastItem() {
        notifyItemChanged(messages.size - 1)
    }

    override fun getItemViewType(position: Int): Int {
        if (messages[position].sender == this.sender)
            return VIEW_TYPE_SENDER
        return VIEW_TYPE_RECEIVER
    }

    fun updatePendingWriteMessageIds(ids: MutableList<String>) {
        pendingWriteMessageIds = ids
        notifyDataSetChanged()
    }

    inner class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    inner class ReceiverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}