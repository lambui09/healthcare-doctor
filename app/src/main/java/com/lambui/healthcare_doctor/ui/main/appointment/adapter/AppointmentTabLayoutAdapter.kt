package com.lambui.healthcare_doctor.ui.main.appointment.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentPrevious.AppointmentHistoryFragment
import com.lambui.healthcare_doctor.ui.main.appointment.appointmentUpcoming.AppointmentUpcomingFragment

class AppointmentTabLayoutAdapter(val context: Context?,
                                  fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                AppointmentUpcomingFragment()
            }

            else -> {
                AppointmentHistoryFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Upcoming"
            else -> "History"
        }
    }
}