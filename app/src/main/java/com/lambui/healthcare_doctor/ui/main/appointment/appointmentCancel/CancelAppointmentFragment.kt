package com.lambui.healthcare_doctor.ui.main.appointment.appointmentCancel

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.base.recycleview.OnItemClickListener
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_ITEM_APPOINTMENT
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_STATUS
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.ui.main.appointment.detail.DetailBookAppointmentActivity
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.show
import kotlinx.android.synthetic.main.fragment_appointment_cancel.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CancelAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment_cancel
    override val viewModelx: AppointmentVM by sharedViewModel()
    private lateinit var appointmentCancelAppointmentAdapter: CancelAppointmentAdapter

    private val adapterObserver: RecyclerView.AdapterDataObserver =
        object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                appointmentCancelAppointmentAdapter.let {
                    linearLayoutEmpty.show(appointmentCancelAppointmentAdapter.itemCount == 0)
                }
            }
        }

    override fun initialize() {
        initAdapter()
        callApi()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            listAppointmentCancelOfDoctor.observe(this@CancelAppointmentFragment, Observer {
                bindData(it.toMutableList())
                rvAppointmentCancel.stopRefreshData()
                rvAppointmentCancel.stopAllStatusLoadData()
            })
        }
    }

    override fun registerOnClick() {
    }

    private fun initAdapter() {
        context?.let {
            appointmentCancelAppointmentAdapter = CancelAppointmentAdapter(it).apply {
                registerItemClickListener(object : OnItemClickListener<AppointmentFullModel> {
                    override fun onItemViewClick(item: AppointmentFullModel, position: Int) {
                        val bundle = Bundle()
                        bundle.putParcelable(EXTRA_ITEM_APPOINTMENT, item)
                        bundle.putString(KEY_STATUS, StatusAppointmentType.CANCELED.name)
                        this@CancelAppointmentFragment.goTo(
                            DetailBookAppointmentActivity::class,
                            bundle
                        )
                    }
                })
            }
            rvAppointmentCancel.apply {
                setAdapter(appointmentCancelAppointmentAdapter)
                setLayoutManager(LinearLayoutManager(context?.applicationContext))
                setEnableLoadMore(false)
                setEnableSwipe(true)
                setHasFixedSize(true)
            }
        }
    }

    private fun callApi() {
        with(viewModelx) {
            getAllAppointmentCancel()
        }
    }

    override fun onResume() {
        super.onResume()
        appointmentCancelAppointmentAdapter.registerAdapterDataObserver(adapterObserver)
    }

    override fun onPause() {
        super.onPause()
        appointmentCancelAppointmentAdapter.unregisterAdapterDataObserver(adapterObserver)
    }

    private fun bindData(newList: MutableList<AppointmentFullModel>) {
        appointmentCancelAppointmentAdapter.updateData(newList)
    }
}