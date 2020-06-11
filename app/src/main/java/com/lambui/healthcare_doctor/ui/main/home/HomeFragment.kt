package com.lambui.healthcare_doctor.ui.main.home

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.ui.main.home.managerDoctor.examination.ExaminationDoctorActivity
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.goTo
import kotlinx.android.synthetic.main.list_item_top_option_cagories.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment<HomeVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_home
    override val viewModelx: HomeVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {

        launchDisposable {
            RxView.clickCheckNetwork(btnCreateSchedule, object : RxView.IListenerCheckNetWork {
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
                this@HomeFragment.goTo(ExaminationDoctorActivity::class)
            }
        }
    }
}