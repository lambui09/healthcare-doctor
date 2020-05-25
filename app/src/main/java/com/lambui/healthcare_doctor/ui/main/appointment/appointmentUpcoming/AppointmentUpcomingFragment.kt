package com.lambui.healthcare_doctor.ui.main.appointment.appointmentUpcoming

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.base.recycleview.OnItemClickListener
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_UPCOMING_ITEM
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.appointment.detail.DetailBookAppointmentActivity
import com.lambui.healthcare_doctor.utils.extension.goTo
import kotlinx.android.synthetic.main.fragment_appointment_upcoming.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentUpcomingFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_upcoming
    override val viewModelx: AppointmentVM by sharedViewModel()
    private lateinit var upcomingAdapter: UpcomingAdapter
    private val adapterObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                upcomingAdapter.let {
                    linearLayoutEmpty.show(upcomingAdapter.itemCount == 0)
                }
            }
        }

    override fun initialize() {
        initAdapter()
        callApi()
        rvAppointmentUpcoming.stopAllStatusLoadData()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            listAppointmentPending.observe(this@AppointmentUpcomingFragment, Observer {
                bindData(it.toMutableList())
                rvAppointmentUpcoming.stopAllStatusLoadData()
            })
            onError.observe(this@AppointmentUpcomingFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {

    }

    override fun onResume() {
        super.onResume()
        upcomingAdapter.registerAdapterDataObserver(adapterObserver)
    }

    override fun onPause() {
        super.onPause()
        upcomingAdapter.unregisterAdapterDataObserver(adapterObserver)
    }

    private fun callApi() {
        with(viewModelx) {
            getAppointmentPending()
        }
    }

    private fun initAdapter() {
        context?.let {
            upcomingAdapter = UpcomingAdapter(it).apply {
                registerItemClickListener(object : OnItemClickListener<AppointmentFullModel> {
                    override fun onItemViewClick(item: AppointmentFullModel, position: Int) {
                        val bundle = Bundle()
                        bundle.putParcelable(KEY_UPCOMING_ITEM, item)
                        this@AppointmentUpcomingFragment.goTo(DetailBookAppointmentActivity::class, bundle)
                    }
                })
            }
        }
        rvAppointmentUpcoming.apply {
            setAdapter(upcomingAdapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setEnableLoadMore(true)
            setEnableSwipe(true)
            setHasFixedSize(true)
        }
    }

    private fun bindData(newList: MutableList<AppointmentFullModel>) {
        upcomingAdapter.updateData(newList)
    }
}