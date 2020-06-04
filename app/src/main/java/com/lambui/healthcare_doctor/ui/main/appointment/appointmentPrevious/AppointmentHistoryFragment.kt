package com.lambui.healthcare_doctor.ui.main.appointment.appointmentPrevious

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.fragment_appointment_previous.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentHistoryFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_previous
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
            listAppointmentOfDoctor.observe(this@AppointmentHistoryFragment, Observer {
                bindData(it.toMutableList())
                rvAppointmentHisTory.stopRefreshData()
                rvAppointmentHisTory.stopAllStatusLoadData()
            })
        }
    }

    override fun registerOnClick() {
    }

    private fun initAdapter() {
        context?.let {
            appointmentHistoryAdapter = AppointmentHistoryAdapter(it)
        }
        rvAppointmentHisTory.apply {
            setAdapter(appointmentHistoryAdapter)
            setLayoutManager(LinearLayoutManager(context?.applicationContext))
            setEnableLoadMore(true)
            setEnableSwipe(true)
            setHasFixedSize(true)
        }
        rvAppointmentHisTory.stopRefreshData()
        rvAppointmentHisTory.stopAllStatusLoadData()
    }

    private fun callApi() {
        with(viewModelx) {
            getAppointmentOfDoctor()
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
        appointmentHistoryAdapter.updateData(newList)
    }
}