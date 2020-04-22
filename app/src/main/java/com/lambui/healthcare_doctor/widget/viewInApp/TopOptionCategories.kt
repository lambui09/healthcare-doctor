package com.lambui.healthcare_doctor.widget.viewInApp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.lambui.healthcare_doctor.R
import kotlinx.android.synthetic.main.view_top_categories.view.*

class TopOptionCategories @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.view_top_categories, this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.TopOptionCategories, 0, 0
            )
            val header = resources.getText(typedArray.getResourceId(
                R.styleable.TopOptionCategories_TopOptionCategoryHeader,
                R.string.empty))
            val description = resources.getText(typedArray.getResourceId(
                R.styleable.TopOptionCategories_TopOptionCategoryDescription,
                R.string.empty))
            val drawableResId = typedArray.getResourceId(
                R.styleable.TopOptionCategories_TopOptionCategoryIcon,
                -1)
            mTvHeader.text = header
            mTvDescription.text = description
            mImgIcon.setImageResource(drawableResId)
            typedArray.recycle()
        }
    }
}