package com.lambui.healthcare_doctor.ui.introduce

import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.base.BaseActivity
import com.lambui.healthcare_doctor.data.model.IntroduceSliderModel
import com.lambui.healthcare_doctor.ui.auths.login.LoginActivity
import com.lambui.healthcare_doctor.ui.auths.signup.RegisterActivity
import com.lambui.healthcare_doctor.utils.extension.enable
import com.lambui.healthcare_doctor.utils.extension.goTo
import com.lambui.healthcare_doctor.utils.extension.setTextWithSpan
import kotlinx.android.synthetic.main.activity_introduce.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroduceActivity : BaseActivity<IntroduceVM>() {
    override val layoutID: Int
        get() = R.layout.activity_introduce
    override val viewModelx: IntroduceVM by viewModel()
    private var mListSliderItem = ArrayList<IntroduceSliderModel>()
    private lateinit var mIntroduceViewPagerAdapter: IntroduceViewPagerAdapter

    override fun initialize() {
        initData()
        initAdapter()
        initViewpager()
        handleRegister()
    }

    override fun onSubscribeObserver() {

    }

    override fun registerOnClick() {
        btnLogin.getViewClick().setOnClickListener {
            goTo(LoginActivity::class)
        }
    }

    private fun initData() {
        mListSliderItem.let {
            it.add(
                IntroduceSliderModel(
                    resources.getString(R.string.title_splash_1),
                    resources.getString(R.string.text_description_splash_1),
                    R.drawable.image_backgound_plash_1
                )
            )
            it.add(
                IntroduceSliderModel(
                    resources.getString(R.string.title_splash_2),
                    resources.getString(R.string.text_description_splash_2),
                    R.drawable.image_backgournd_splash_2
                )
            )
            it.add(
                IntroduceSliderModel(
                    resources.getString(R.string.title_splash_3),
                    resources.getString(R.string.text_description_splash_3),
                    R.drawable.image_background_spash_3
                )
            )
        }

    }

    private fun initAdapter() {
        mIntroduceViewPagerAdapter = IntroduceViewPagerAdapter(mListSliderItem)
        viewpagerSliderIntroduce.adapter = mIntroduceViewPagerAdapter
        circleIndicatorIntroduce.setViewPager(viewpagerSliderIntroduce)
    }

    private fun initViewpager() {
        viewpagerSliderIntroduce.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
        })
    }

    override fun onResume() {
        tvRegister.enable(true)
        super.onResume()
    }

    private fun handleRegister() {
        tvRegister.setTextWithSpan(
            ContextCompat.getColor(this, R.color.K_8A173833_gray),
            resources.getString(R.string.text_sign_up)
        ) {
            tvRegister.enable(false)
            this.goTo(RegisterActivity::class)
        }
    }
}