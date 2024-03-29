package com.lambui.healthcare_doctor.utils.extension

import android.text.TextUtils
import com.lambui.healthcare_doctor.utils.DateTimeUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.convertUiFormatToDataFormat(inputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  outputFormat: String,
  locale: Locale = Locale.JAPAN): String? {
  if (TextUtils.isEmpty(this)) {
    return ""
  }
  val gmtTime = TimeZone.getTimeZone(DateTimeUtils.TIME_ZONE_UTC)
  val sdf = SimpleDateFormat(inputFormat, locale)
  sdf.timeZone = gmtTime
  val newSdf = SimpleDateFormat(outputFormat, locale)
  newSdf.timeZone = gmtTime
  return try {
    newSdf.format(sdf.parse(this))
  } catch (e: ParseException) {
    null
  }
}

fun String.convertToUTC(inputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  locale: Locale = Locale.JAPAN): String? {
  if (TextUtils.isEmpty(this)) {
    return ""
  }
  val gmtTime = TimeZone.getTimeZone(DateTimeUtils.TIME_ZONE_UTC)
  val sdf = SimpleDateFormat(inputFormat, locale)
  val newSdf = SimpleDateFormat(outputFormat, locale)
  newSdf.timeZone = gmtTime
  return try {
    newSdf.format(sdf.parse(this))
  } catch (e: ParseException) {
    null
  }
}


fun Calendar.convertUiFormatToDataFormat(outputFormat: String,
  locale: Locale = Locale.JAPAN): String? {
  val gmtTime = TimeZone.getTimeZone(DateTimeUtils.TIME_ZONE_UTC)
  val sdf = SimpleDateFormat(outputFormat, locale)
  sdf.timeZone = gmtTime
  return try {
    sdf.format(this.time)
  } catch (e: ParseException) {
    null
  }
}

fun Calendar.getDateTime(outputFormat: String = DateTimeUtils.TIME_FORMAT_HH_MM,
  locale: Locale = Locale.JAPAN): String? {
  return this.time.convertDateToString(outputFormat, locale)
}

fun Calendar.getCurrentDate(outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD_EN,
  locale: Locale = Locale.JAPAN): String? {
  val calendar = Calendar.getInstance(locale)
  return calendar.time.convertDateToString(outputFormat)
}

fun Date.getCurrentDate(outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_YYYY_MM_DD,
  locale: Locale = Locale.JAPAN): String? {
  return convertDateToString(outputFormat, locale)
}

fun Date.convertDateToDate(outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  locale: Locale = Locale.JAPAN): Date? {
  val df = SimpleDateFormat(outputFormat, locale)
  return df.format(this).convertStringToDate(outputFormat, locale)
}

fun Date.convertDateToString(outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  locale: Locale = Locale.getDefault()): String? {
  val df = SimpleDateFormat(outputFormat, locale)
  return df.format(this)
}

fun String.convertStringToDate(outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  locale: Locale = Locale.JAPAN): Date {
  val parser = SimpleDateFormat(outputFormat, locale)
  return try {
    parser.parse(this)
  } catch (e: ParseException) {
    Date()
  }
}

fun Date.getDayOfWeek(locale: Locale = Locale.JAPAN): String {
  return this.convertDateToString(DateTimeUtils.DAY_OF_WEEK, locale).toString()
}

fun Date.getDayOfMonth(locale: Locale = Locale.JAPAN): String {
  val calendar = Calendar.getInstance(locale)
  calendar.time = this
  return calendar.get(Calendar.DAY_OF_MONTH).toString()
}

fun Date.getMonthOfYear(locale: Locale = Locale.JAPAN): String {
  val calendar = Calendar.getInstance(locale)
  calendar.time = this
  return (calendar.get(Calendar.MONTH) + 1).toString()
}

fun String.getFirstDayOfWeek(locale: Locale = Locale.JAPAN): Date {
  val calendar = Calendar.getInstance(locale)
  calendar.time = this.convertStringToDate()
  while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
    calendar.add(Calendar.DATE, -1)
  }
  return calendar.time
}

fun String.isValidDateFormat(format: String, locale: Locale = Locale.JAPAN): Boolean {
  val formatter = SimpleDateFormat(format, locale)
  formatter.isLenient = false
  return try {
    formatter.parse(this)
    true
  } catch (e: ParseException) {
    false
  }
}

fun Date.isSameDay(expectedDay: Int, locale: Locale = Locale.JAPAN): Boolean {
  val calendar = Calendar.getInstance(locale)
  calendar.time = this
  return calendar.get(Calendar.DAY_OF_WEEK) == expectedDay
}

fun String.convertDateStringWithPlusTime(plusTime: Long,
  outputFormat: String = DateTimeUtils.DATE_TIME_FORMAT_UTC,
  locale: Locale = Locale.JAPAN): String {
  val date = this.convertStringToDate(outputFormat)
  val calendar = Calendar.getInstance(locale)
  calendar.time = date
  return Date(calendar.timeInMillis + plusTime).convertDateToString().toString()
}

fun Date.convertDateWithPlusTime(plusTime: Long, locale: Locale = Locale.JAPAN): Date {
  val calendar = Calendar.getInstance(locale)
  calendar.time = this
  return Date(calendar.timeInMillis + plusTime)
}
