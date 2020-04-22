package com.lambui.healthcare_doctor.widget.viewInApp

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.SparseArray
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.lambui.healthcare_doctor.R
import com.lambui.healthcare_doctor.utils.extension.enable
import kotlinx.android.synthetic.main.view_edit_text_custom.view.*

class EditTextCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs), TextWatcher {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_edit_text_custom, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.EditTextCustom, 0, 0
            )

            val editable = typedArray.getBoolean(
                R.styleable.EditTextCustom_editable,
                true
            )
            editTextCustom.isEnabled = editable

            val text = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .EditTextCustom_EditTextCustomText,
                        R.string.empty
                    )
            ).toString()
            setText(text)

            //Todo: remove comment - support for disable copy/paste when long click edittext.
//      val disableLongClick = typedArray.getBoolean(R.styleable.EditTextCustom_disableLongClick,
//        false)
//      editTextCustom.isLongClickable = !disableLongClick

            val hint = resources.getText(
                typedArray
                    .getResourceId(
                        R.styleable
                            .EditTextCustom_hint,
                        R.string.empty
                    )
            ).toString()
            editTextCustom.hint = hint

            val inputType = typedArray.getInt(
                R.styleable.EditTextCustom_inputType,
                INPUT_TEXT_TYPE
            )
            setInputType(inputType)

            val imeOption = typedArray.getInt(
                R.styleable.EditTextCustom_imeOption,
                DONE_IME_TYPE
            )
            setImeOption(imeOption)

            val requestFocus = typedArray.getBoolean(
                R.styleable.EditTextCustom_requestFocus,
                false
            )
            requestFocus(requestFocus)

            val maxLength = typedArray.getInt(
                R.styleable.EditTextCustom_maxLength,
                0
            )
            setMaxLength(maxLength)

            val maxLines = typedArray.getInt(
                R.styleable.EditTextCustom_maxLines,
                0
            )
            setMaxLines(maxLines)

            val isAllCaps = typedArray.getBoolean(
                R.styleable.EditTextCustom_textAllCaps,
                false
            )
            if (isAllCaps) {
                editTextCustom.isAllCaps = isAllCaps
            }

            editTextCustom.setOnKeyListener { _, _, keyEvent ->
                when (keyEvent.keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_DOWN_LEFT, KeyEvent.KEYCODE_DPAD_DOWN_RIGHT, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_DPAD_RIGHT, KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_UP_LEFT, KeyEvent.KEYCODE_DPAD_UP_RIGHT
                    -> true
                    else -> false
                }
            }

            typedArray.recycle()

            editTextCustom.addTextChangedListener(this)
        }
    }

    fun setEnableEditText(isEnable: Boolean) {
        editTextCustom.enable(isEnable)
    }

    fun setInputType(type: Int) {
        when (type) {
            INPUT_TEXT_TYPE -> {
                editTextCustom.inputType = InputType.TYPE_CLASS_TEXT
            }

            INPUT_TEXT_PASSWORD_TYPE -> {
                editTextCustom.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
            }

            INPUT_NUMBER_TYPE -> {
                editTextCustom.inputType = InputType.TYPE_CLASS_NUMBER
            }

            INPUT_EMAIL_TYPE -> {
                editTextCustom.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                //Set maxLength for edittext input is email
                editTextCustom.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(255))
            }
            INPUT_CAP_CHARACTER -> {
                editTextCustom.inputType = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
            }
        }
    }

    fun setImeOption(type: Int) {
        when (type) {
            NEXT_IME_TYPE -> {
                editTextCustom.imeOptions = EditorInfo.IME_ACTION_NEXT
            }

            DONE_IME_TYPE -> {
                editTextCustom.imeOptions = EditorInfo.IME_ACTION_DONE
            }
        }
    }

    fun requestFocus(requestFocus: Boolean) {
        if (requestFocus) {
            editTextCustom.requestFocus()
        }
    }

    fun setTextError(textError: String) {
        if (textError.isNotEmpty()) {
            setVisibilityError(VISIBLE)
            tvError.text = textError
            return
        }
        setVisibilityError(GONE)
    }

    fun setVisibilityError(type: Int) {
        when (type) {
            GONE -> {
                tvError.visibility = View.GONE
                editTextCustom.isSelected = false
            }

            VISIBLE -> {
                tvError.visibility = View.VISIBLE
                editTextCustom.isSelected = true
            }

            INVISIBLE -> {
                tvError.visibility = View.INVISIBLE
            }
        }
    }

    fun setText(text: String) {
        editTextCustom.setText(text)
    }

    fun setTextHint(hint: String) {
        editTextCustom.hint = hint
    }

    fun enableBgError(isError: Boolean) {
        editTextCustom.isSelected = isError
    }

    fun getEditText(): EditText {
        return editTextCustom
    }

    fun getText(): String {
        return editTextCustom.text.toString().trim()
    }

    fun getTextValidate(): String {
        return editTextCustom.text.toString()
    }

    fun getHint(): String {
        return editTextCustom.hint.toString()
    }

    fun getTextViewError(): TextView {
        return tvError
    }

    fun setMaxLength(maxLength: Int) {
        if (maxLength != 0) {
            editTextCustom.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        }
    }

    fun setMaxLines(maxLines: Int) {
        if (maxLines != 0) {
            editTextCustom.maxLines = maxLines
        }
    }

    @Suppress("UNCHECKED_CAST")
    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = superState?.let { SavedState(it) }
        savedState?.childrenStates = SparseArray()
        for (i in 0 until childCount) {
            getChildAt(i).saveHierarchyState(savedState?.childrenStates as SparseArray<Parcelable>)
        }
        return savedState
    }

    @Suppress("UNCHECKED_CAST")
    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        for (i in 0 until childCount) {
            getChildAt(i).restoreHierarchyState(savedState.childrenStates as SparseArray<Parcelable>)
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    companion object {
        const val INPUT_TEXT_TYPE: Int = 0
        const val INPUT_TEXT_PASSWORD_TYPE: Int = 1
        const val INPUT_NUMBER_TYPE: Int = 2
        const val INPUT_EMAIL_TYPE: Int = 3
        const val INPUT_CAP_CHARACTER: Int = 4

        const val NEXT_IME_TYPE: Int = 0
        const val DONE_IME_TYPE: Int = 1


        const val GONE: Int = 0
        const val VISIBLE: Int = 1
        const val INVISIBLE: Int = 2
    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s.isNullOrBlank() || s.isNullOrEmpty() || s.isEmpty()) {
            tvError.visibility = View.GONE
        }
    }
}

class SavedState(superState: Parcelable) : View.BaseSavedState(superState) {
    var childrenStates: SparseArray<Any>? = null

    override fun writeToParcel(out: Parcel, flags: Int) {
        super.writeToParcel(out, flags)
        childrenStates?.let {
            out.writeSparseArray(it)
        }
    }
}