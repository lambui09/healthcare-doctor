package com.lambui.healthcare_doctor.ui.main.appointment.detail

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.constant.Constants.EXTRA_ARGS
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_UPCOMING_ITEM
import com.lambui.healthcare_doctor.data.model.AppointmentFullModel
import com.lambui.healthcare_doctor.enums.DetailAppointmentNav
import com.lambui.healthcare_doctor.enums.StatusAppointmentType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import com.lambui.healthcare_doctor.widget.toolbar.MainToolbar
import kotlinx.android.synthetic.main.activity_detail_book_appointment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailBookAppointmentActivity : BaseActivity<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.activity_detail_book_appointment

    override val viewModelx: AppointmentVM by viewModel()

    override fun initialize() {
        intent?.let {
            val bundle = it.getBundleExtra(EXTRA_ARGS)
            val key = ""
            if (bundle != null) {
                when (key) {
                    KEY_UPCOMING_ITEM -> {
                        val appointment =
                            bundle.getParcelable<AppointmentFullModel>(KEY_UPCOMING_ITEM)
                        viewModelx.appointmentItem = appointment
                        appointment?.let { it1 ->
                            when (it1.status) {
                                StatusAppointmentType.PENDING.name -> {
                                    viewModelx.setNavigationDetailAppointment(DetailAppointmentNav.CANCEL_APPOINTMENT)
                                }
                                StatusAppointmentType.CONFIRMED.name -> {
                                    viewModelx.setNavigationDetailAppointment(DetailAppointmentNav.COMPLETE_APPOINTMENT)
                                }
                            }
                        }
                    }
                }

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
                    DetailAppointmentNav.COMPLETE_APPOINTMENT.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutDetailAppointment.id,
                            addToBackStack = false,
                            tag = CompleteBookAppointmentFragment::class.java.simpleName,
                            fragment = CompleteBookAppointmentFragment()
                        )
                    }
                    DetailAppointmentNav.CANCEL_APPOINTMENT.name -> {
                        replaceFragmentInActivity(
                            containerId = frameLayoutDetailAppointment.id,
                            addToBackStack = false,
                            tag = CancelBookAppointmentFragment::class.java.simpleName,
                            fragment = CancelBookAppointmentFragment()
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
        toolbar.setToolBarOnClick(object : MainToolbar.OnToolBarListener {
            override fun onClickLeft() {
                onBackPressed()
            }

            override fun onClickRight() {

            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}