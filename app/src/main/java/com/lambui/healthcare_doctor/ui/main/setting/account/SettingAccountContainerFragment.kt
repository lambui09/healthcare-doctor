package com.lambui.healthcare_doctor.ui.main.setting.account

import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.enums.SettingAccountNav
import com.lambui.healthcare_doctor.utils.RxView
import kotlinx.android.synthetic.main.fragment_setting_account_container.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingAccountContainerFragment : BaseFragment<AccountVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_setting_account_container
    override val viewModelx: AccountVM by sharedViewModel()

    override fun initialize() {

    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
        launchDisposable {
            RxView.clicks(stProfile, false)
                .subscribe {
                    viewModelx.setNavigationSettingAccount(SettingAccountNav.PROFILE)
                }
            RxView.clicks(stHistoryPatient, false)
                .subscribe {
                    viewModelx.setNavigationSettingAccount(SettingAccountNav.HISTORY)
                }
        }
    }
}