package com.lambui.healthcare_doctor.ui.main.chat

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.chat.chatDetail.ChatDetailActivity
import kotlinx.android.synthetic.main.fragment_chat.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChatFragment : BaseFragment<ChatVM>() {
  override val layoutID: Int
    get() = R.layout.fragment_chat
  override val viewModelx: ChatVM by sharedViewModel()

  private var yourID: String = "null"
  private lateinit var conversationAdapter: ConversationAdapter

  override fun initialize() {
    yourID = viewModelx.getCurrentUserId()!!
    viewModelx.getConversationList(yourID)
    initView()
  }

  private fun initView() {
    conversationAdapter = ConversationAdapter(activity!!, yourID)
    conversationAdapter.onConversationClick =
      object : ConversationAdapter.OnConversationClickListener {
        override fun onConversationClick(userId: String, userName: String) {
          val intent = Intent(context, ChatDetailActivity::class.java)
          with(intent) {
            putExtra(ChatDetailActivity.KEY_RECIEVER, userId)
            putExtra(ChatDetailActivity.KEY_RECEIVER_NAME, userName)
          }
          startActivity(intent)
        }

      }
    rvConversation.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
    rvConversation.adapter = conversationAdapter
  }

  override fun onSubscribeObserver() {
    with(viewModelx) {
      conversationList.observe(viewLifecycleOwner, Observer {
        conversationAdapter.conversationList = it
        conversationAdapter.notifyDataSetChanged()
      })
    }
  }

  override fun registerOnClick() {
  }
}