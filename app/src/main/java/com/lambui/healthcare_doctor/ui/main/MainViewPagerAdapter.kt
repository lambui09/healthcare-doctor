package com.lambui.healthcare_doctor.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MainViewPagerAdapter( fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private var mFragmentList : ArrayList<Fragment> = ArrayList()
    override fun getItem(position: Int): Fragment {
        return  mFragmentList[position]
    }

    override fun getCount(): Int {
        return if (mFragmentList.isNullOrEmpty()) 0 else mFragmentList.size
    }

     fun addFragment(fragment: Fragment){
        mFragmentList.add(fragment)
    }
}