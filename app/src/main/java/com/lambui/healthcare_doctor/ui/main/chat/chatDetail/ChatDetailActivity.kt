package com.lambui.healthcare_doctor.ui.main.chat.chatDetail

import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.data.model.MessageModel
import kotlinx.android.synthetic.main.activity_detail_chat.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatDetailActivity : BaseActivity<ChatDetailVM>() {
    private var receiverId: String = "sender"
    private var senderId: String = "receiver"
    override val layoutID: Int
        get() = R.layout.activity_detail_chat
    override val viewModelx: ChatDetailVM by viewModel()

    var conversationId: String = ""
    lateinit var mMessageAdapter: MessageAdapter

    override fun initialize() {
        senderId = viewModelx.getCurrentUserId()
        receiverId = intent.getStringExtra(KEY_RECIEVER)
        viewModelx.initUser(senderId, receiverId)
        viewModelx.loadConversation(senderId, receiverId)
        initView()
    }

    private fun initView() {
        rvMessages.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mMessageAdapter = MessageAdapter(this, sender = senderId)
        rvMessages.adapter = mMessageAdapter
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            conversation.observe(this@ChatDetailActivity,
                Observer { t ->
                    conversationId = t.id
                    loadMessage()
                })
            conversationUpdate.observe(this@ChatDetailActivity,
                Observer {
                    mMessageAdapter.seen = it.seen
                    mMessageAdapter.updateLastItem()
                    rvMessages.smoothScrollToPosition(
                        mMessageAdapter.messages.size
                    )

                })

            partnerName.observe(this@ChatDetailActivity, Observer {
                toolBar.setTitleTooBar(it ?: "Null")
            })

            partnerAvatarUrl.observe(this@ChatDetailActivity, Observer {
                Glide.with(this@ChatDetailActivity)
                    .asDrawable()
                    .load(it)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            mMessageAdapter.partnerAvatar = resource
                            return true
                        }
                    })
                    .submit()
            })

            messages.observe(this@ChatDetailActivity,
                Observer {
                    if (it.isEmpty()) {
                        aniSayHi.visibility = View.VISIBLE
                    } else
                        aniSayHi.visibility = View.GONE
                    mMessageAdapter.messages = it as MutableList<MessageModel>
                    mMessageAdapter.notifyDataSetChanged()
                    rvMessages.smoothScrollToPosition(
                        mMessageAdapter.messages.size
                    )
                })
            pendingWriteMessageIds.observe(this@ChatDetailActivity,
                Observer {
                    mMessageAdapter.updatePendingWriteMessageIds(it)
                })
        }
    }

    override fun registerOnClick() {
        btnSend.setOnClickListener {
            mMessageAdapter.seen = false
            mMessageAdapter.updateLastItem()
            val content = edtChat.text.toString()
            edtChat.text?.clear()
            viewModelx.sendMessage(
                conversationId = conversationId,
                senderId = senderId,
                content = content
            )
        }

        aniSayHi.setOnClickListener {
            viewModelx.sendMessage(conversationId, senderId, "Hello Doctor")
        }
    }

    companion object {
        val KEY_SENDER = "sender"
        val KEY_RECIEVER = "reciever"
    }
}