package com.lambui.healthcare_doctor.widget.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.lambui.healthcare_doctor.R
import kotlinx.android.synthetic.main.view_tool_bar.view.*

class MainToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    interface OnToolBarListener {
        fun onClickLeft()

        fun onClickRight()
    }

    private var listener: OnToolBarListener? = null

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_tool_bar, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.MainToolbar, 0, 0
            )
            val title = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .MainToolbar_toolbarTitle,
                        R.string.empty
                    )
            )

            setTitleTooBar(title.toString())

            val type = typedArray.getInt(R.styleable.MainToolbar_toolbarType,
                TYPE_SHOW_BOTH
            )

            setTypeToolBar(type)
            typedArray.recycle()
        }
        btnBack.setOnClickListener {
            listener?.onClickLeft()
        }

        btnClose.setOnClickListener {
            listener?.onClickRight()
        }
    }

    fun setToolBarOnClick(listener: OnToolBarListener) {
        this.listener = listener
    }

    fun setTypeToolBar(type: Int) {
        when (type) {
            TYPE_SHOW_LEFT -> {
                rlBack.visibility = View.VISIBLE
                rlClose.visibility = View.INVISIBLE
            }

            TYPE_SHOW_RIGHT -> {
                rlBack.visibility = View.INVISIBLE
                rlClose.visibility = View.VISIBLE
            }

            TYPE_SHOW_BOTH -> {
                rlBack.visibility = View.VISIBLE
                rlClose.visibility = View.VISIBLE
            }

            TYPE_HIDE_BOTH -> {
                rlBack.visibility = View.INVISIBLE
                rlClose.visibility = View.INVISIBLE
            }
        }
    }

    fun setTitleTooBar(title: String) {
        tvToolbar.text = title
    }

    fun setImageRight(image: Int) {
        mImgRight.setBackgroundResource(image)
    }

    companion object {
        const val TYPE_SHOW_LEFT: Int = 0
        const val TYPE_SHOW_RIGHT: Int = 1
        const val TYPE_SHOW_BOTH: Int = 2
        const val TYPE_HIDE_BOTH: Int = 3
    }
}