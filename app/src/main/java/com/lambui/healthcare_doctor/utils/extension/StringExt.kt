package com.lambui.healthcare_doctor.utils.extension

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.view.View
import android.widget.TextView
import com.lambui.healthcare_doctor.constant.Constants.DIVIDER_CHARACTER
import com.lambui.healthcare_doctor.constant.Constants.INDEX_INSERT_CHARACTER_FIRST
import com.lambui.healthcare_doctor.constant.Constants.INDEX_INSERT_CHARACTER_SECOND
import com.lambui.healthcare_doctor.utils.StringUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun String.toInt(): Int {
  return try {
    Integer.parseInt(this)
  } catch (e: NumberFormatException) {
    Integer.MIN_VALUE
  }
}

fun String.toDouble(): Double {
  return try {
    java.lang.Double.parseDouble(this)
  } catch (e: NumberFormatException) {
    Double.MIN_VALUE
  }
}

@Throws(ParseException::class)
fun String.toDate(format: String): Date {
  val parser = SimpleDateFormat(format, Locale.getDefault())
  return parser.parse(this)
}

@Throws(ParseException::class)
fun String.toDateWithFormat(inputFormat: String, outputFormat: String): String {
  val gmtTimeZone = TimeZone.getTimeZone("UTC")
  val inputDateTimeFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
  inputDateTimeFormat.timeZone = gmtTimeZone

  val outputDateTimeFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
  outputDateTimeFormat.timeZone = gmtTimeZone
  return outputDateTimeFormat.format(inputDateTimeFormat.parse(this))
}

fun String.validWithPattern(pattern: Pattern): Boolean {
  return pattern.matcher(toLowerCase()).find()
}

fun String.validWithPattern(regex: String): Boolean {
  return Pattern.compile(regex).matcher(this).find()
}

fun List<String>.toStringWithFormatPattern(format: String): String {
  if (this.isEmpty()) {
    return ""
  }
  val builder = StringBuilder()
  for (s in this) {
    builder.append(s)
    builder.append(format)
  }
  var result = builder.toString()
  result = result.substring(0, result.length - format.length)
  return result
}

fun String.removeWhitespaces(): String {
  return this.replace("[\\s-]*".toRegex(), "")
}

fun String.subString(beginInDex: Int, endIndex: Int): String {
  return this.substring(beginInDex, endIndex)
}

fun String.insert(index: Int, contentInsert: String): String {
  val builder = StringBuilder(this)
  builder.insert(index, contentInsert)
  return builder.toString()
}

fun List<String>.convertStringToListStringWithFormatPattern(format: String): String {
  if (this.isEmpty()) {
    return ""
  }
  val builder = StringBuilder()
  for (s in this) {
    builder.append(s)
    builder.append(format)
  }
  var result = builder.toString()
  result = result.substring(0, result.length - format.length)
  return result
}

fun SpannableString.withClickableSpan(
  color: Int,
  textView: TextView,
  clickablePart: String, onClickListener: () -> Unit
): SpannableString {
  val clickableSpan = object : ClickableSpan() {
    override fun onClick(view: View) = onClickListener.invoke()
  }
  val clickablePartStart = indexOf(clickablePart)
  setSpan(
    clickableSpan,
    clickablePartStart,
    clickablePartStart + clickablePart.length,
    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
  )

  setSpan(
    ForegroundColorSpan(color),
    clickablePartStart, clickablePartStart + clickablePart.length,
    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
  )

  with(textView) {
    movementMethod = LinkMovementMethod.getInstance()
    isClickable = true
    highlightColor = Color.TRANSPARENT
  }

  return this
}

fun String.validateEmailOrPhoneNo(): Boolean {
  if (!Patterns.EMAIL_ADDRESS.matcher(this).matches()
    && !this.matches("[0-9]+".toRegex())
  ) {
    return false
  }
  return true
}

fun String.validateEmail(): Boolean {
  return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.validateTextAndNumber(): Boolean {
  val patternTextNumber = Pattern.compile("^[a-zA-Z0-9]+\$")
  val matcherTextNumber = patternTextNumber.matcher(this)
  return matcherTextNumber.find()
}

fun String.isValidatePattern(regex: String): Boolean =
  if (StringUtils.isBlank(this)) {
    false
  } else {
    Pattern.compile(regex).matcher(this).find()
  }

fun String.formatPhoneNumber(): String {
  val sb = StringBuilder(this)
  sb.insert(INDEX_INSERT_CHARACTER_FIRST, DIVIDER_CHARACTER)
  sb.insert(INDEX_INSERT_CHARACTER_SECOND, DIVIDER_CHARACTER)
  return sb.toString()
}