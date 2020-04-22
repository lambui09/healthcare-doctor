package com.lambui.healthcare_doctor.utils

import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.widget.EditText
import android.widget.TextView
import com.lambui.healthcare_doctor.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

object StringUtils {
  fun isBlank(content: String?): Boolean {
    return content == null || content.isEmpty() || "" == content
  }

  fun isNotEmpty(content: String?): Boolean {
    return !isBlank(content)
  }

  fun isNotEmptyEditText(editTexts: List<EditText>): Boolean {
    for (editText in editTexts) {
      if (TextUtils.isEmpty(editText.text.trim())) {
        return false
      }
    }
    return true
  }

  fun getSubString(data: String, beginInDex: Int, endIndex: Int): String {
    return data.substring(beginInDex, endIndex)
  }

  fun setSpannableString(textView: TextView, contentSp: String,
    startIndexSp: Int, endIndexSp: Int, isUnderLineSp: Boolean) {
    val spannableString = SpannableString(contentSp)
    spannableString.setSpan(
      ForegroundColorSpan(textView.resources.getColor(R.color.K_6EACEF_blue)),
      startIndexSp, endIndexSp, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    if (isUnderLineSp) {
      spannableString.setSpan(UnderlineSpan(), startIndexSp, endIndexSp,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    textView.text = spannableString
    textView.movementMethod = LinkMovementMethod.getInstance()
  }

  fun setSpannableString(textView: TextView, clickableSpan: ClickableSpan,
    startIndexSp: Int, endIndexSp: Int) {

    val spannableString = SpannableString(textView.text.toString())
    spannableString.setSpan(clickableSpan, startIndexSp, endIndexSp,
      Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    textView.text = spannableString
    textView.movementMethod = LinkMovementMethod.getInstance()
  }

  fun setSpannableString(what: Any, content: String,
    sub: String): SpannableString {
    if (!content.contains(sub)) {
      return SpannableString(content)
    }
    val startIndex = content.indexOf(sub)
    val endIndex = startIndex + sub.length
    val spannableString = SpannableString(content)
    spannableString.setSpan(what, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    return spannableString
  }

  fun convertStringToNumber(input: String): Int {
    return try {
      Integer.parseInt(input)
    } catch (e: NumberFormatException) {
      Integer.MIN_VALUE
    }

  }

  fun convertStringToListStringWithFormatPattern(strings: List<String>,
    format: String): String {
    if (strings.isEmpty()) {
      return ""
    }
    val builder = StringBuilder()
    for (s in strings) {
      builder.append(s)
      builder.append(format)
    }
    var result = builder.toString()
    result = result.substring(0, result.length - format.length)
    return result
  }

  fun removeWhitespaces(content: String): String {
    return content.replace("[\\s-]*".toRegex(), "")
  }

  fun formatMoney(number: Int): String {
    val formatter = DecimalFormat("###,###,###")
    formatter.decimalFormatSymbols = DecimalFormatSymbols(Locale.JAPAN)
    return formatter.format(number.toLong())
  }

  fun formatMoney(number: Long): String {
    val formatter = DecimalFormat("###,###,###")
    formatter.decimalFormatSymbols = DecimalFormatSymbols(Locale.JAPAN)
    return formatter.format(number)
  }

  fun formatMoneyWithFloat(number: Float): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(number.toDouble())
  }

  fun formatMoneyDisplay(number: Int): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(number.toLong()) + "P"
  }

  fun formatMoneySpaceDisplay(number: Int): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(number.toLong()) + " P"
  }

  fun replaceString(str: String, regex: String, replacement: String): String {
    val msg = str.replace(regex.toRegex(), replacement)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      Html.fromHtml(msg, Html.FROM_HTML_MODE_COMPACT).toString()
    } else Html.fromHtml(msg).toString()

  }

  fun formatMoneyCastClass(number: Int): String {
    val formatter = DecimalFormat("###,###,###")
    return formatter.format(number.toLong()) + "P/30分"
  }

  fun formatCreditCardNumber(creditCardNumber: String): String {
    val result = StringBuilder()
    for (i in creditCardNumber.indices) {
      if (i % 4 == 0 && i != 0) {
        result.append(" ")
      }
      result.append(creditCardNumber[i])
    }
    return result.toString()
  }
}