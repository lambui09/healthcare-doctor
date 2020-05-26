package com.lambui.healthcare_doctor.ui.main.appointment.detail

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.constant.Constants.EXTRA_ARGS
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_UPCOMING_ITEM
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.DetailAppointmentNav
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_detail_book_appointment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBookAppointmentActivity : BaseActivity<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.activity_detail_book_appointment

    override val viewModelx: AppointmentVM by viewModel()

    override fun initialize() {
        intent?.let {
            val bundle = it.getBundleExtra(EXTRA_ARGS)
            if (bundle != null) {
                val appointment = bundle.getParcelable<AppointmentFullModel>(KEY_UPCOMING_ITEM)
                viewModelx.appointmentItem = appointment
            }
        }
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            navigation.observe(this@DetailBookAppointmentActivity, Observer {
                when (it) {
                    DetailAppointmentNav.DETAIL_APPOINTMENT.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutDetailAppointment.id,
                            addToBackStack = false,
                            tag = DetailBookAppointmentFragment::class.java.simpleName,
                            fragment = DetailBookAppointmentFragment()
                        )
                    }
                    DetailAppointmentNav.COMPELETE_APPOINTMENT.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutDetailAppointment.id,
                            addToBackStack = false,
                            tag = CompleteBookAppointmentFragment::class.java.simpleName,
                            fragment = CompleteBookAppointmentFragment()
                        )
                    }
                }
            })
            typeToolbar.observe(this@DetailBookAppointmentActivity, Observer {
                toolbar.setTypeToolBar(it)
            })
        }
    }

    override fun registerOnClick() {


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}