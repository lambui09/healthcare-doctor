package com.lambui.healthcare_doctor.widget.viewInApp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lambui.healthcare_doctor.R
import kotlinx.android.synthetic.main.view_setting_content.view.*

class SettingContent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_setting_content, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.SettingContent, 0, 0
            )

            val content = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .SettingContent_content,
                        R.string.empty
                    )
            )

            tvContent.text = content

            val drawableResId = typedArray.getResourceId(R.styleable.SettingContent_icon, -1)
            mImgIcon.setImageResource(drawableResId)

            val type = typedArray.getInt(R.styleable.SettingContent_type,
                TYPE_HEADER
            )
            setLineType(type)

            typedArray.recycle()
        }
    }

    private fun setLineType(type: Int) {
        when (type) {
            TYPE_HEADER -> {
                mViewLineTop.visibility = View.VISIBLE

                mViewLineBottomPadding.visibility = View.VISIBLE
                mViewLineBottom.visibility = View.INVISIBLE
            }

            TYPE_BOTTOM -> {
                mViewLineBottom.visibility = View.VISIBLE

                mViewLineBottomPadding.visibility = View.INVISIBLE
                mViewLineTop.visibility = View.INVISIBLE
            }

            TYPE_CENTER -> {
                mViewLineBottomPadding.visibility = View.VISIBLE

                mViewLineTop.visibility = View.INVISIBLE
                mViewLineBottom.visibility = View.INVISIBLE
            }

            TYPE_SINGLE -> {
                mViewLineTop.visibility = View.VISIBLE
                mViewLineBottom.visibility = View.VISIBLE

                mViewLineBottomPadding.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        const val TYPE_HEADER: Int = 0
        const val TYPE_BOTTOM: Int = 1
        const val TYPE_SINGLE: Int = 2
        const val TYPE_CENTER: Int = 3
    }
}