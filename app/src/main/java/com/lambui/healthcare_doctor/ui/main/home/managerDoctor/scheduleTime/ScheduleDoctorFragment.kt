package com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime

import android.annotation.SuppressLint
import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.model.WorkingScheduleBody
import com.lambui.healthcare_doctor.utils.*
import com.lambui.healthcare_doctor.utils.StringUtils.isBlank
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_schedule_time_doctor.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ScheduleDoctorFragment : BaseFragment<ScheduleDoctorVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_schedule_time_doctor
    override val viewModelx: ScheduleDoctorVM by sharedViewModel()

    override fun initialize() {
        listener()
        viewModelx.getWorkingScheduleOfDoctor()
    }

    @SuppressLint("SetTextI18n")
    override fun onSubscribeObserver() {
        with(viewModelx) {
            workingSchedule.observe(this@ScheduleDoctorFragment, Observer {
                fromDate.setText(it.fromDate.toString() ?: "")
                endDate.setText(it.endDate.toString() ?: "")
                timeStart.setText(it.startTime.toString() ?: "")
                timeEnd.setText(it.endTime.toString() ?: "")
                durationAppointment.setText(it.duration_default_appointment.toString() ?: "")
            })
            onError.observe(this@ScheduleDoctorFragment, Observer {
                fromDate.setText("")
                endDate.setText("")
                timeStart.setText("")
                timeEnd.setText("")
                durationAppointment.setText("")
            })
        }
    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clickCheckNetwork(
                btnSubmit.getViewClick(),
                object : RxView.IListenerCheckNetWork {
                    override fun showError(isCheckNetwork: Boolean) {
                        showErrorInternet()
                    }
                }).subscribe {
                if (validate()) {
                    val workingScheduleBody = WorkingScheduleBody(
                        fromDate = fromDate.text?.trim().toString(),
                        endDate = endDate.text?.trim().toString(),
                        startTime = timeStart.text?.trim().toString(),
                        endTime = timeEnd.text?.trim().toString()
                    )
                    viewModelx.createWorkingScheduleDoctor(workingScheduleBody)
                }
            }
        }
    }


    private fun validate(): Boolean {
        if (isBlank(fromDate.text?.trim().toString())) {
            return false
        }
        if (isBlank(endDate.text?.trim().toString())) {
            return false
        }
        if (isBlank(timeStart.text?.trim().toString())) {
            return false
        }
        if (isBlank(timeEnd.text?.trim().toString())) {
            return false
        }
        if (isBlank(durationAppointment.text?.trim().toString())) {
            return false
        }
        return true
    }

    private fun listener() {
        launchDisposable {
            RxView.search(durationAppointment)
                .map { return@map ValidateUtils.validateName(it, requireContext()) }
                .subscribeBy(
                    onNext = {
                        if (it == "") {
                            btnSubmit.setButtonSelected(validate())
                        } else {
                            btnSubmit.setButtonSelected(validate())
                        }
                    }
                )
        }
        EditTextInputValidateStartDate(fromDate).listen()
        EditTextInputValidateStartDate(endDate).listen()
        EditTextInputValidateTimeSchedule(timeStart).listen()
        EditTextInputValidateTimeSchedule(timeEnd).listen()
    }
}