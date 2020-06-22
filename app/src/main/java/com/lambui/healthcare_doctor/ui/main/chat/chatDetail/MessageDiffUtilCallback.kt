package com.lambui.healthcare_doctor.ui.main.chat.chatDetail

import androidx.recyclerview.widget.DiffUtil
import com.lambui.healthcare_doctor.data.model.MessageModel

class MessageDiffUtilCallback(
  private var oldList: List<MessageModel>,
  private var newList: List<MessageModel>
) : DiffUtil.Callback() {

  override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    return oldList[oldItemPosition].id == newList[newItemPosition].id
  }

  override fun getOldListSize(): Int {
    return oldList.size
  }

  override fun getNewListSize(): Int {
    return newList.size
  }

  override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
    val old = oldList[oldItemPosition]
    val new = newList[newItemPosition]

    return old.compareTo(new)
  }
}