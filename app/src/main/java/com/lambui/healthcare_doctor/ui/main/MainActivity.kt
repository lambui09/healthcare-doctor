package com.lambui.healthcare_doctor.ui.main

import androidx.viewpager.widget.ViewPager
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.enums.TabType
import com.lambui.healthcare_doctor.ui.main.appointment.AppointmentFragment
import com.lambui.healthcare_doctor.ui.main.chat.ChatFragment
import com.lambui.healthcare_doctor.ui.main.home.HomeFragment
import com.lambui.healthcare_doctor.ui.main.notification.NotificationFragment
import com.lambui.healthcare_doctor.ui.main.setting.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<MainVM>() {
    override val layoutID: Int
        get() = R.layout.activity_main
    override val viewModelx: MainVM by viewModel()

    override fun initialize() {
        initViewPager()
        initTabLayout()
    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {

    }

    private fun initViewPager() {
        supportFragmentManager.let {
            val mainPagerAdapter = MainViewPagerAdapter(it)
            val homeFragment = HomeFragment()
            val appointmentFragment = AppointmentFragment()
            val chatFragment = ChatFragment()
            val notificationFragment = NotificationFragment()
            val settingFragment = SettingFragment()
            with(mainPagerAdapter) {
                addFragment(homeFragment)
                addFragment(appointmentFragment)
                addFragment(chatFragment)
                addFragment(notificationFragment)
                addFragment(settingFragment)
            }
            with(viewPagerMain) {
                adapter = mainPagerAdapter
                offscreenPageLimit = 4
                setPagingEnabled(false)
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        when (position) {
                            0 -> {
                                viewPagerMain.setCurrentItem(TabType.HOME.value, false)
                            }
                            1 -> {
                                viewPagerMain.setCurrentItem(TabType.APPOINTMENT.value, false)
                            }
                            2 -> {
                                viewPagerMain.setCurrentItem(TabType.CHAT.value, false)
                            }
                            3 -> {
                                viewPagerMain.setCurrentItem(TabType.NOTIFICATION.value, false)
                            }
                            4 -> {
                                viewPagerMain.setCurrentItem(TabType.SETTING.value, false)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun initTabLayout() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mTabHome -> {
                    viewPagerMain.setCurrentItem(TabType.HOME.value, false)
                    true
                }
                R.id.mTabAppointment -> {
                    viewPagerMain.setCurrentItem(TabType.APPOINTMENT.value, false)
                    true
                }

                R.id.mTabChat -> {
                    viewPagerMain.setCurrentItem(TabType.CHAT.value, false)
                    true
                }
                R.id.mTabNotification -> {
                    viewPagerMain.setCurrentItem(TabType.NOTIFICATION.value, false)
                    true
                }
                R.id.mTabSetting -> {
                    viewPagerMain.setCurrentItem(TabType.SETTING.value, false)
                    true
                }
                else ->false
            }
        }

    }
}
