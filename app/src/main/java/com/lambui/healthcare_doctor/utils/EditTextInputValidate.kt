package com.lambui.healthcare_doctor.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.lambui.healthcare_doctor.constant.Constants.LENGTH_INPUT_BIRTHDAY
import com.lambui.healthcare_doctor.constant.Constants.POSITION_SEPARATOR_FIRST
import com.lambui.healthcare_doctor.constant.Constants.POSITION_SEPARATOR_SECOND

class EditTextInputValidate(val input: EditText, val iListener: IListener? = null) {
    fun listen() {
        input.addTextChangedListener(mDateEntryWatcher)
    }

    private val mDateEntryWatcher = object : TextWatcher {
        var edited = false
        val dividerCharacter = "/"
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (edited) {
                edited = false
                return
            }
            var working = getEditText()
            working = manageDateDivider(working, POSITION_SEPARATOR_FIRST, start, before)
            working = manageDateDivider(working, POSITION_SEPARATOR_SECOND, start, before)
            edited = true
            input.setText(working)
            input.setSelection(input.text.length)
            iListener?.changeText(input.text.toString())
        }

        private fun manageDateDivider(
            working: String,
            position: Int,
            start: Int,
            before: Int
        ): String {
            if (working.length == position) {
                return if (before <= position && start < position)
                    working + dividerCharacter
                else
                    working.dropLast(1)
            }
            return working
        }

        private fun getEditText(): String {
            return if (input.text.length >= LENGTH_INPUT_BIRTHDAY)
                input.text.toString().substring(0, LENGTH_INPUT_BIRTHDAY)
            else
                input.text.toString()
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }

    interface IListener {
        fun changeText(text: String)
    }
}