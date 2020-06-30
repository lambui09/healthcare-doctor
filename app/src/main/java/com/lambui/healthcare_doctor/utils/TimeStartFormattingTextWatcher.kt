package com.lambui.healthcare_doctor.utils

import android.graphics.Canvas
import android.graphics.Paint
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.style.ReplacementSpan

class TimeStartFormattingTextWatcher : TextWatcher {
    private var internalStopFormatFlag: Boolean = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (internalStopFormatFlag) {
            return
        }
        internalStopFormatFlag = true
        val maxLength = 4
        formatExpiryDate(s, maxLength)
        internalStopFormatFlag = false
    }

    private fun formatExpiryDate(expiryDate: Editable, maxLength: Int) {
        var textLength = expiryDate.length
        // first remove any previous span
        val spans = expiryDate.getSpans(0, expiryDate.length, SlashSpan::class.java)
        for (i in spans.indices) {
            expiryDate.removeSpan(spans[i])
        }
        // then truncate to max length
        if (maxLength > 0 && textLength > maxLength - 1) {
            expiryDate.replace(maxLength, textLength, "")
            --textLength
        }
        // finally add margin spans
        for (i in 1..(textLength - 1) / 2) {
            val end = i * 2 + 1
            val start = end - 1
            val marginSPan = SlashSpan()
            expiryDate.setSpan(marginSPan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    class SlashSpan internal constructor() : ReplacementSpan() {

        override fun getSize(
            paint: Paint,
            text: CharSequence,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            val widths = FloatArray(end - start)
            val slashWidth = FloatArray(1)
            paint.getTextWidths(text, start, end, widths)
            paint.getTextWidths(":", slashWidth)
            var sum = slashWidth[0].toInt()
            for (width in widths) {
                sum += width.toInt()
            }
            return sum
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            val xtext = ":" + text.subSequence(start, end)
            canvas.drawText(xtext, 0, xtext.length, x, y.toFloat(), paint)
        }
    }
}