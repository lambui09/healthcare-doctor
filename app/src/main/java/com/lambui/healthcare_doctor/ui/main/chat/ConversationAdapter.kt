package com.lambui.healthcare_doctor.ui.main.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lambui.healthcare_app.data.model.ConversationModel
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.utils.DateTimeUtils
import kotlinx.android.synthetic.main.item_conversation.view.*

class ConversationAdapter(
    private val context: Context,
    private val yourId: String
) : RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {
    private val TAG = ConversationAdapter::class.java.simpleName

    var conversationList: MutableList<ConversationModel> = mutableListOf()
    var onConversationClick: OnConversationClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        return ConversationViewHolder(view)
    }

    override fun getItemCount(): Int {
        return conversationList.size
    }

    override fun onBindViewHolder(holder: ConversationViewHolder, position: Int) {
        val conversation = conversationList[position]
        val partnerId =
            if (conversation.members[0] == yourId) conversation.members[1] else conversation.members[0]
        with(holder.itemView) {
            tvName.text = conversation.memberNames[partnerId]
            Glide.with(context)
                .load(conversation.memberAvatars[partnerId])
                .into(imvAvatar)
            tvMessageContent.text = conversation.lastMessage

            val updateAt = conversation.updateAt.toDate()
            if (DateUtils.isToday(updateAt.time)) {
                val time =
                    DateTimeUtils.getDateTimeDisPlay(updateAt, DateTimeUtils.TIME_FORMAT_HH_MM)
                tvUpdateAt.text = time
            } else {
                val time =
                    DateTimeUtils.getDateTimeDisPlay(updateAt, DateTimeUtils.DATE_FORMAT_DD_MM)
                tvUpdateAt.text = time
            }

            unreadTextView(tvMessageContent, false)
            unreadTextView(tvUpdateAt, false)

            if (yourId == conversation.lastSender) {
                if (conversation.seen) {
                    imvMessageStatus.setImageDrawable(imvAvatar.drawable)
                    imvMessageStatus.visibility = View.VISIBLE
                } else
                    imvMessageStatus.visibility = View.INVISIBLE
            } else {
                if (!conversation.seen) {
                    //load notify icon to message status and visible it
                    imvMessageStatus.setImageResource(R.color.quantum_googblue)
                    imvMessageStatus.visibility = View.VISIBLE

                    unreadTextView(tvMessageContent, true)
                    unreadTextView(tvUpdateAt, true)
                } else {
                    imvMessageStatus.visibility = View.INVISIBLE
                }
            }

            setOnClickListener {
                val partner =
                    if (conversation.members[0] == yourId)
                        conversation.members[1]
                    else conversation.members[0]

                onConversationClick?.run {
                    onConversationClick(partner)
                }
            }
        }
    }

    private fun unreadTextView(textView: TextView, unread: Boolean) {
        with(textView) {
            setTypeface(typeface, if (unread) Typeface.BOLD else Typeface.ITALIC)
            setTextColor(
                if (unread) Color.BLACK
                else
                    ContextCompat.getColor(
                        context,
                        R.color.primary_text_color
                    )
            )
        }
    }

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnConversationClickListener {
        fun onConversationClick(userId: String)
    }
}