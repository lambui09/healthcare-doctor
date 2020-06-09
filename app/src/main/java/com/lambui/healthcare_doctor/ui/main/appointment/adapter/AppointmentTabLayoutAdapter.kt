package com.lambui.healthcare_doctor.ui.main.appointment.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentCancel.CancelAppointmentFragment
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentConfirm.ConfirmAppointmentFragment
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentPrevious.AppointmentCompleteFragment
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentUpcoming.AppointmentPendingFragment

class AppointmentTabLayoutAdapter(
    val context: Context?,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AppointmentPendingFragment()
            }
            1 -> {
                ConfirmAppointmentFragment()
            }
            2 -> {
                AppointmentCompleteFragment()
            }
            else -> {
                CancelAppointmentFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Pending"
            1 -> "Confirm"
            2 -> "Complete"
            else -> "Cancel"
        }
    }
}