package com.lambui.healthcare_doctor.ui.main.setting.account

import androidx.lifecycle.Observer
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.AnimateType
import com.lambui.healthcare_doctor.enums.SettingAccountNav
import com.lambui.healthcare_doctor.ui.main.setting.account.profile.ProfileFragment
import com.lambui.healthcare_doctor.utils.extension.replaceFragmentInActivity
import kotlinx.android.synthetic.main.activity_setting_account.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingAccountActivity : BaseActivity<AccountVM>() {
    override val layoutID: Int
        get() = R.layout.activity_setting_account
    override val viewModelx: AccountVM by viewModel()

    override fun initialize() {
        replaceFragmentInActivity(
            frameLayoutSettingAccount.id,
            SettingAccountContainerFragment(), false,
            SettingAccountContainerFragment::class.java.simpleName
        )
    }

    override fun onSubscribeObserver() {
        with(viewModelx) {
            navigationSettingAccount.observe(this@SettingAccountActivity, Observer {
                when (it) {
                    SettingAccountNav.PROFILE.name -> {
                        replaceFragmentInActivity(
                            containerId = R.id.frameLayoutSettingAccount,
                            addToBackStack = false,
                            fragment = ProfileFragment(),
                            animateType = AnimateType.SLIDE_TO_LEFT,
                            tag = ProfileFragment::class.java.simpleName
                        )
                    }
                    SettingAccountNav.HISTORY.name -> {

                    }
                }
            })
        }
    }

    override fun registerOnClick() {
    }
}