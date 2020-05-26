package com.lambui.healthcare_doctor.ui.main.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.recycleview.BaseLoadMoreAdapter
import com.lambui.healthcare_doctor.data.model.NotificationModel
import kotlinx.android.synthetic.main.view_item_notification.view.*

class NotificationAdapter(context: Context) : BaseLoadMoreAdapter<NotificationModel>(context) {
    override fun onCreateViewHolderLM(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.view_item_notification, parent, false)
        return NotificationVH(view)
    }

    override fun onBindViewHolderLM(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NotificationVH).bindData(getItem(position))
    }

    override fun getItemViewTypeLM(position: Int) = 0
}

class NotificationVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(notificationModel: NotificationModel?) = with(itemView) {
        with(notificationModel) {
            tvTitle.text = notificationModel?.title
            tvContent.text = notificationModel?.body
        }
    }
}