package com.lambui.healthcare_doctor.widget.viewInApp

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.lambui.healthcare_doctor.R
import kotlinx.android.synthetic.main.view_main_bottom_button.view.*

class BottomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_main_bottom_button, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.BottomButton, 0, 0
            )

            val buttonContent = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .BottomButton_BottomButtonContent,
                        R.string.empty
                    )
            )
            tvContent.text = buttonContent

            val isSelected =
                typedArray.getBoolean(R.styleable.BottomButton_BottomButtonSelected, false)
            setButtonSelected(isSelected)


            val color = typedArray.getInt(
                R.styleable.BottomButton_Background,
                -1
            )
            setBackground(color)

            typedArray.recycle()
        }
    }

    fun setButtonSelected(isEnabled: Boolean) {
        btnClick.isEnabled = isEnabled
    }

    fun getStateSelected(): Boolean {
        return btnClick.isEnabled
    }

    fun setContent(content: String) {
        tvContent.text = content
    }

    fun getViewClick(): View {
        return btnClick
    }

    fun getText() = tvContent.text.toString()

    fun setBackground(color: Int) {
        if (color != -1) {
            btnClick.setBackgroundColor(color)
        }
    }
}