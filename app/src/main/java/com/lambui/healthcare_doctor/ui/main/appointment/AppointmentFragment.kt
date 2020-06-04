package com.lambui.healthcare_doctor.ui.main.appointment

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_HAS_NOTIFICATION
import com.lambui.healthcare_doctor.ui.main.appointment.adapter.AppointmentTabLayoutAdapter
import kotlinx.android.synthetic.main.fragment_appointment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()
    private lateinit var appointmentTabLayoutAdapter: AppointmentTabLayoutAdapter

    override fun initialize() {
        if (arguments != null && arguments!!.getBoolean(EXTRA_HAS_NOTIFICATION, false)) {
            arguments?.remove(EXTRA_HAS_NOTIFICATION)
            viewPager.setCurrentItem(1, false)
        }
        initAdapter()
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
    private fun initAdapter(){
        appointmentTabLayoutAdapter = AppointmentTabLayoutAdapter(
            requireContext(),
            childFragmentManager
        )
        viewPager.adapter = appointmentTabLayoutAdapter
        viewPagerTab.setupWithViewPager(viewPager)
    }
}