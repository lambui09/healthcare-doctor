package com.lambui.healthcare_doctor.ui.main.appointment.detail

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentVM
import com.lambui.healthcare_doctor.utils.RxView
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_complete_book_appointment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CompleteBookAppointmentFragment : BaseFragment<AppointmentVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_complete_book_appointment
    override val viewModelx: AppointmentVM by sharedViewModel()

    override fun initialize() {

    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clickCheckNetwork(
                btnCompleteAppointment.getViewClick(),
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribeBy {
                showConfirmDialog(resources.getString(R.string.text_title_dialog_complete),
                    resources.getString(R.string.text_content_dialog_complete),
                    object : DialogConfirm.OnButtonClickedListener {
                        override fun onPositiveClicked() {
                            viewModelx.completeRequest()
                        }

                        override fun onNegativeClicked() {

                        }
                    })

            }
        }
    }

}