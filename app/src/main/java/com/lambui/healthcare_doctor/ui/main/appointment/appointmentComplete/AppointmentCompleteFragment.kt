package com.lambui.healthcare_doctor.ui.main.appointment.appointmentComplete

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.base.recycleview.OnItemClickListener
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_ITEM_APPOINTMENT
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.appointment.detail.DetailBookAppointmentActivity
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.fragment_appointment_complete.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentCompleteFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_complete
    override val viewModelx: AppointmentVM by sharedViewModel()
    private lateinit var appointmentHistoryAdapter: AppointmentHistoryAdapter

    private val adapterObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                appointmentHistoryAdapter.let {
                    linearLayoutEmpty.show(appointmentHistoryAdapter.itemCount == 0)
                }
            }
        }

    override fun initialize() {
        initAdapter()
        callApi()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            listAppointmentCompleteOfDoctor.observe(this@AppointmentCompleteFragment, Observer {
                bindData(it.toMutableList())
                rvAppointmentComplete.stopRefreshData()
                rvAppointmentComplete.stopAllStatusLoadData()
            })
        }
    }

    override fun registerOnClick() {
    }

    private fun initAdapter() {
        context?.let {
            appointmentHistoryAdapter = AppointmentHistoryAdapter(it).apply {
                registerItemClickListener(object : OnItemClickListener<AppointmentFullModel> {
                    override fun onItemViewClick(item: AppointmentFullModel, position: Int) {
                        val bundle = Bundle()
                        bundle.putParcelable(EXTRA_ITEM_APPOINTMENT, item)
                        bundle.putString(ExtraKeyConstants.KEY_STATUS, StatusAppointmentType.COMPLETED.name)
                        this@AppointmentCompleteFragment.goTo(
                            DetailBookAppointmentActivity::class,
                            bundle
                        )
                    }
                })
            }
        }
        rvAppointmentComplete.apply {
            setAdapter(appointmentHistoryAdapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setEnableLoadMore(false)
            setEnableSwipe(false)
            setHasFixedSize(true)
        }
    }

    private fun callApi() {
        with(viewModelx) {
            getAllAppointmentComplete()
        }
    }

    override fun onResume() {
        super.onResume()
        appointmentHistoryAdapter.registerAdapterDataObserver(adapterObserver)
    }

    override fun onPause() {
        super.onPause()
        appointmentHistoryAdapter.unregisterAdapterDataObserver(adapterObserver)
    }

    private fun bindData(newList: MutableList<AppointmentFullModel>) {
        appointmentHistoryAdapter.replaceData(newList)
    }
}