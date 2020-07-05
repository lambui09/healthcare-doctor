package com.lambui.healthcare_doctor.ui.main.appointment.detail

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.constant.Constants.EXTRA_ARGS
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.EXTRA_ITEM_APPOINTMENT
import com.lambui.healthcare_doctor.constant.ExtraKeyConstants.KEY_STATUS
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
            if (bundle != null) {
                val status = bundle.getString(KEY_STATUS)
                status?.let {
                    when (it) {
                        StatusAppointmentType.PENDING.name -> {
                            viewModelx.appointmentItem =
                                bundle.getParcelable(EXTRA_ITEM_APPOINTMENT)
                            viewModelx.setNavigationDetailAppointment(DetailAppointmentNav.DETAIL_APPOINTMENT)
                        }
                        StatusAppointmentType.CONFIRMED.name -> {
                            viewModelx.appointmentItem =
                                bundle.getParcelable(EXTRA_ITEM_APPOINTMENT)
                            viewModelx.setNavigationDetailAppointment(DetailAppointmentNav.COMPLETE_APPOINTMENT)

                        }
                        StatusAppointmentType.COMPLETED.name -> {
                            viewModelx.appointmentItem =
                                bundle.getParcelable(EXTRA_ITEM_APPOINTMENT)

                        }
                        StatusAppointmentType.CANCELED.name -> {
                            viewModelx.appointmentItem =
                                bundle.getParcelable(EXTRA_ITEM_APPOINTMENT)

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
                finish()
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}