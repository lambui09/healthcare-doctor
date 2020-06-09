package com.lambui.healthcare_doctor.ui.main.notification

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.model.NotificationModel
import com.lambui.healthcare_doctor.ui.main.notification.adapter.NotificationAdapter
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.fragment_notification.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NotificationFragment : BaseFragment<NotificationVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_notification
    private lateinit var notificationAdapter: NotificationAdapter
    override val viewModelx: NotificationVM by sharedViewModel()
    private val adapterObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                notificationAdapter.let {
//                    recyclerViewListNotification.gone(notificationAdapter.itemCount == 0)
                    linearLayoutEmpty.show(notificationAdapter.itemCount == 0)
                }
            }
        }

    override fun initialize() {
        initAdapter()
        recyclerViewListNotification.stopAllStatusLoadData()
        callApi()
    }

    override fun onResume() {
        super.onResume()
        notificationAdapter.registerAdapterDataObserver(adapterObserver)
    }

    override fun onStop() {
        super.onStop()
        notificationAdapter.unregisterAdapterDataObserver(adapterObserver)
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            listNotification.observe(this@NotificationFragment, Observer {
                bindData(it?.toMutableList())
                recyclerViewListNotification.stopAllStatusLoadData()
            })
            onError.observe(this@NotificationFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {

    }

    private fun initAdapter() {
        context?.let {
            notificationAdapter = NotificationAdapter(it)
        }
        recyclerViewListNotification.apply {
            setAdapter(notificationAdapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setEnableLoadMore(false)
            setEnableSwipe(true)
            setHasFixedSize(true)
        }
    }

    private fun callApi() {
        with(viewModelx) {
            getNotification()
        }
    }

    private fun bindData(newList: MutableList<NotificationModel>?) {
        notificationAdapter.updateData(newList)
    }
}