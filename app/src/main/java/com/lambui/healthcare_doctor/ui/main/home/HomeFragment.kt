package com.lambui.healthcare_doctor.ui.main.home

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.ExaminationDoctorActivity
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.scheduleTime.ScheduleTimeActivity
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.loadImageUri
import com.lambui.healthcare_doctor.utils.extension.loadImageUrl
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.list_item_top_option_cagories.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<HomeVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_home
    override val viewModelx: HomeVM by sharedViewModel()

    override fun initialize() {
        viewModelx.getDetailDoctor()
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            getDetailDoctorSuccess.observe(this@HomeFragment, Observer {
                imgProfileDoctor.loadImageUrl(it.avatarUrl)
                tvNameDoctor.text = it.fullName ?: ""
                tvNameHospital.text = it.address ?: ""
                tvContentGender.text = it.gender ?: ""
                tvContentExperience.text = it.year_experience.toString() ?: ""

            })
            onError.observe(this@HomeFragment, Observer {
                handleApiError(it)
            })
        }
    }

    override fun registerOnClick() {

        launchDisposable {
            RxView.clickCheckNetwork(btnCreateExamination, object : RxView.IListenerCheckNetWork {
                override fun showError(isCheckNetwork: Boolean) {
                    showErrorInternet()
                }
            }).subscribe {
                this@HomeFragment.goTo(ExaminationDoctorActivity::class)
            }
        }
        launchDisposable {
            RxView.clickCheckNetwork(btnCreateSchedule, object : RxView.IListenerCheckNetWork {
                override fun showError(isCheckNetwork: Boolean) {
                    showErrorInternet()
                }
            }).subscribe {
                this@HomeFragment.goTo(ScheduleTimeActivity::class)
            }
        }
    }
}