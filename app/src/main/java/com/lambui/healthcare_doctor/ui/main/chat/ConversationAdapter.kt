package com.lambui.healthcare_doctor.ui.main.chat

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lambui.healthcare_app.data.model.ConversationModel
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.utils.DateTimeUtils
import kotlinx.android.synthetic.main.item_conversation.view.*

class ConversationAdapter(private val context: Context, private val yourId: String) :
    RecyclerView.Adapter<ConversationAdapter.ConversationViewHolder>() {
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
        holder.itemView.tvName.text = conversation.memberNames[partnerId]
        Glide.with(context)
            .load(conversation.memberAvatars[partnerId])
            .into(holder.itemView.imvAvatar)
        holder.itemView.tvMessageContent.text = conversation.lastMessage
        val updateAt = conversation.updateAt.toDate()
        if (DateUtils.isToday(updateAt.time)) {
            val time = DateTimeUtils.getDateTimeDisPlay(updateAt, DateTimeUtils.TIME_FORMAT_HH_MM)
            holder.itemView.tvUpdateAt.text = time
        } else {
            val time = DateTimeUtils.getDateTimeDisPlay(updateAt, DateTimeUtils.DATE_FORMAT_DD_MM)
            holder.itemView.tvUpdateAt.text = time
        }

        holder.itemView.tvMessageContent.run {
            this.setTypeface(this.typeface, Typeface.NORMAL)
            this.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_text_color
                )
            )
        }
        holder.itemView.tvName.run {
            this.setTypeface(this.typeface, Typeface.NORMAL)
        }
        holder.itemView.tvUpdateAt.run {
            this.setTypeface(this.typeface, Typeface.NORMAL)
            this.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.primary_text_color
                )
            )
        }

        if (yourId == conversation.lastSender) {
            if (conversation.seen) {
                holder.itemView.imvMessageStatus.setImageDrawable(holder.itemView.imvAvatar.drawable)
                holder.itemView.imvMessageStatus.visibility = View.VISIBLE
            } else
                holder.itemView.imvMessageStatus.visibility = View.INVISIBLE
        } else {
            if (!conversation.seen) {
                //load notify icon to message status and visible it
                holder.itemView.imvMessageStatus.setImageResource(R.color.quantum_googblue)
                holder.itemView.imvMessageStatus.visibility = View.VISIBLE

                holder.itemView.tvMessageContent.run {
                    this.setTypeface(this.typeface, Typeface.BOLD)
                    this.setTextColor(Color.BLACK)
                }
                holder.itemView.tvName.run {
                    this.setTypeface(this.typeface, Typeface.BOLD)
                }
                holder.itemView.tvUpdateAt.run {
                    this.setTypeface(this.typeface, Typeface.BOLD)
                    this.setTextColor(Color.BLACK)
                }
            } else {
                holder.itemView.imvMessageStatus.visibility = View.INVISIBLE
            }
        }

        holder.itemView.setOnClickListener {
            val partner =
                if (conversation.members[0].equals(yourId)) conversation.members[1] else conversation.members[0]

            onConversationClick?.run {
                onConversationClick(partner)
            }
        }
    }

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnConversationClickListener {
        fun onConversationClick(userId: String)
    }
}