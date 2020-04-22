package com.lambui.healthcare_doctor.base.recycleview

import com.lambui.healthcare_doctor.constant.Constants.POSITION_DEFAULT

interface OnItemClickListener<T> {
    fun onItemViewClick(item: T, position: Int = POSITION_DEFAULT)
}