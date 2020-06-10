package com.lambui.healthcare_doctor.ui.main.setting

import android.os.Bundle
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseFragment
import com.lambui.healthcare_doctor.data.source.sharedprf.SharedPrefsKey.KEY_LOGOUT
import com.lambui.healthcare_doctor.dialog.DialogConfirm
import com.lambui.healthcare_doctor.ui.auths.login.LoginActivity
import com.lambui.healthcare_doctor.ui.main.MainActivity
import com.lambui.healthcare_doctor.ui.main.setting.account.SettingAccountActivity
import com.lambui.healthcare_doctor.utils.RxView
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.startActivityAtRoot
import kotlinx.android.synthetic.main.fragment_setting.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SettingFragment : BaseFragment<SettingVM>() {
    override val layoutID: Int
        get() = R.layout.fragment_setting
    override val viewModelx: SettingVM by sharedViewModel()

    override fun initialize() {
    }

    override fun onSubscribeObserver() {
    }

    override fun registerOnClick() {
        launchDisposable {

            RxView.clicks(stAccount, false)
                .subscribe {
//                    val profileModel = viewModelx.getProfileData.value
//                    profileModel.isNull {
//                        viewModelx.reGetProfile(SettingGoToType.GO_TO_ACCOUNT_SETTING.name)
//                        return@subscribe
//                    }
                    this@SettingFragment.goTo(SettingAccountActivity::class)
//                    goToForResult(cls = SettingAccountActivity::class,
//                        parcel = profileModel,
//                        requestCode = REQUEST_UPDATE_ACCOUNT)
                }
            RxView.clicks(stLogout, false)
                .subscribe {
                    showConfirmDialogWithTitleButton(resources.getString(R.string.text_title_logout),
                        resources.getString(R.string.text_confirm_logout),
                        resources.getString(R.string.button_logout),
                        resources.getString(R.string.button_cancel),
                        object : DialogConfirm.OnButtonClickedListener {
                            override fun onPositiveClicked() {
                                val bundle = Bundle()
                                bundle.putBoolean(KEY_LOGOUT, true)
                                (activity as MainActivity).startActivityAtRoot(
                                    requireContext(),
                                    LoginActivity::class.java,
                                    args = bundle
                                )
                                viewModelx.logout()
                            }

                            override fun onNegativeClicked() {

                            }

                        }
                    )
                }
        }
    }

    companion object {
        const val REQUEST_UPDATE_ACCOUNT = 100
        const val REQUEST_UPDATE_NOTIFICATION = 101
    }
}