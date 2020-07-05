package com.lambui.healthcare_doctor.ui.main.appointment.appointmentPending

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.base.recycleview.OnItemClickListener
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_ITEM_APPOINTMENT
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_PENDING_ITEM
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_STATUS
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.appointment.detail.DetailBookAppointmentActivity
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.fragment_appointment_upcoming.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentPendingFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_upcoming
    override val viewModelx: AppointmentVM by sharedViewModel()
    private lateinit var upcomingAdapter: AppointmentPendingAdapter
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
            listAppointmentPending.observe(this@AppointmentPendingFragment, Observer {
                bindData(it.toMutableList())
                rvAppointmentUpcoming.stopAllStatusLoadData()
            })
            onError.observe(this@AppointmentPendingFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {

    }

    override fun onResume() {
        super.onResume()
        callApi()
        upcomingAdapter.registerAdapterDataObserver(adapterObserver)
    }

    override fun onPause() {
        super.onPause()
        upcomingAdapter.unregisterAdapterDataObserver(adapterObserver)
    }

    private fun callApi() {
        with(viewModelx) {
            getAllAppointmentPending()
        }
    }

    private fun initAdapter() {
        context?.let {
            upcomingAdapter = AppointmentPendingAdapter(it).apply {
                registerItemClickListener(object : OnItemClickListener<AppointmentFullModel> {
                    override fun onItemViewClick(item: AppointmentFullModel, position: Int) {
                        val bundle = Bundle()
                        bundle.putParcelable(EXTRA_ITEM_APPOINTMENT, item)
                        bundle.putString(KEY_STATUS, StatusAppointmentType.PENDING.name)
                        this@AppointmentPendingFragment.goTo(
                            DetailBookAppointmentActivity::class,
                            bundle
                        )
                    }
                })
            }
        }
        rvAppointmentUpcoming.apply {
            setAdapter(upcomingAdapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setEnableLoadMore(false)
            setEnableSwipe(false)
            setHasFixedSize(true)
        }
    }

    private fun bindData(newList: MutableList<AppointmentFullModel>) {
        upcomingAdapter.replaceData(newList)
    }
}