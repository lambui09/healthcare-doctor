package com.lambui.healthcare_doctor.ui.main.home.bookAppointment

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.home.detail.DetailDoctorVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ReviewBookAppointmentFragment : BaseFragment<DetailDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_review_book_appointment
    override val viewModelx: DetailDoctorVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
    }
}