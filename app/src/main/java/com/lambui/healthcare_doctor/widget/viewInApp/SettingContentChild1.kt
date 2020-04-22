package com.lambui.healthcare_doctor.widget.viewInApp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lambui.healthcare_doctor.R
import kotlinx.android.synthetic.main.view_setting_content_child_1.view.*

class SettingContentChild1 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {
    init {
        LayoutInflater.from(context).inflate(
            R.layout.view_setting_content_child_1, this
            , true
        )
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.SettingContentChild1,
                0, 0
            )
            val content = resources.getText(
                typedArray.getResourceId(
                    R.styleable.SettingContentChild1_contentSettingChild,
                    R.string.empty
                )
            )
            val option = resources.getText(
                typedArray.getResourceId(
                    R.styleable.SettingContentChild1_optionSettingChild,
                    R.string.empty
                )
            )
            val isVisible =
                typedArray.getBoolean(R.styleable.SettingContentChild1_disableSettingChild, false)
            if (isVisible) {
                imgRow.visibility = View.INVISIBLE
            } else {
                imgRow.visibility = View.VISIBLE
            }
            tvOption.text = option
            tvContent.text = content
            val type =
                typedArray.getInt(R.styleable.SettingContentChild1_typeSettingChild, TYPE_HEADER)
            setLineType(type)
            typedArray.recycle()
        }
    }

    private fun setLineType(type: Int) {
        when (type) {
            TYPE_HEADER -> {
                viewLineTop.visibility = View.VISIBLE

                viewLineBottomPadding.visibility = View.VISIBLE
                viewLineBottom.visibility = View.INVISIBLE
            }

            TYPE_BOTTOM -> {
                viewLineBottom.visibility = View.VISIBLE

                viewLineBottomPadding.visibility = View.INVISIBLE
                viewLineTop.visibility = View.INVISIBLE
            }

            TYPE_CENTER -> {
                viewLineBottomPadding.visibility = View.VISIBLE

                viewLineTop.visibility = View.INVISIBLE
                viewLineBottom.visibility = View.INVISIBLE
            }

            TYPE_SINGLE -> {
                viewLineTop.visibility = View.VISIBLE
                viewLineBottom.visibility = View.VISIBLE

                viewLineBottomPadding.visibility = View.INVISIBLE
            }
        }
    }

    fun setData(value: String) {
        tvOption.text = value
    }


    companion object {
        const val TYPE_HEADER: Int = 0
        const val TYPE_BOTTOM: Int = 1
        const val TYPE_SINGLE: Int = 2
        const val TYPE_CENTER: Int = 3
    }
}
