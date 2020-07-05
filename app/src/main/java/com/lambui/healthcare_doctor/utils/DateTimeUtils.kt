package com.lambui.healthcare_doctor.utils

import android.text.TextUtils
import android.text.format.DateUtils
import com.lambui.healthcare_doctor.constant.Constants.BLANK
import com.lambui.healthcare_doctor.constant.Constants.MINIMUM_MINUTE_DURATION
import com.lambui.healthcare_doctor.utils.extension.convertDateToDate
import com.lambui.healthcare_doctor.utils.extension.convertDateToString
import com.lambui.healthcare_doctor.utils.extension.convertStringToDate
import com.lambui.healthcare_doctor.utils.extension.convertUiFormatToDataFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object DateTimeUtils {

  const val SECOND_MILLIS = 1000
  const val MINUTE_MILLIS = 60 * SECOND_MILLIS
  const val HOUR_MILLIS = 60 * MINUTE_MILLIS
  const val DAY_MILLIS = 24 * HOUR_MILLIS
  const val WEEK_MILLIS = 7 * DAY_MILLIS

  const val DAY = "日"
  const val MONTH = "月"
  const val YEAR = "年"

  const val TIME_ZONE_UTC = "UTC"
  const val DATE_TIME_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss'Z'"

  const val DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
  const val DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM_SS_JP = "yyyy/MM/dd HH:mm:ss"
  const val CATEGORY_DATE_PATTERN = "yyyy/MM/dd HH:mm:ss:SSS"
  const val TIME_FORMAT_HH_MM = "HH:mm"
  const val TIME_FORMAT_HH_MM_SS = "HH:mm:ss"
  const val DATE_TIME_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
  const val DATE_TIME_FORMAT_YYYY_MM_DD_EN = "yyyy/MM/dd"
  const val DATE_TIME_FORMAT_MM_DD_EN = "MM/dd"
  const val DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm"
  const val DATE_TIME_FORMAT_MM_DD_HH_MM_JP = "MM/dd HH:mm"
  const val DATE_TIME_FORMAT_YYYY_年_MM_月_DD_日 = "yyyy年MM月dd日"
  const val DATE_TIME_FORMAT_MM_月_DD_日_E = "MM月dd日(E)"
  const val DATE_TIME_FORMAT_MM_月_DD_日_E_HH_MM = "MM月dd日(E) HH:mm"
  const val DATE_TIME_FORMAT_MM_月_DD_日_HH_MM = "MM月dd日 HH:mm"
  const val DATE_TIME_FORMAT_YYYY_MM_月_DD_日_E = "yyyy年MM月dd日(E)"
  const val DATE_TIME_FORMAT_MM_月_DD_日 = "MM月dd日"
  const val DATE_TIME_FORMAT_YYYY_年_MM_月 = "yyyy年MM月"
  const val DAY_OF_WEEK = "E"
  const val DATE_TIME_FORMAT_YYYY_MM_月_DD_日_E_HH_MM = "yyyy年MM月dd日(E) HH:mm"
  const val DATE_TIME_FORMAT_YYYY_MM_月_DD_日_E_HH_MM_SS = "yyyy年MM月dd日(E) HH:mm:ss"
  const val DATE_FORMAT_MM_SEPERATOR_YY = "MM/yy"
  const val DATE_FORMAT_MM_YY = "MMyy"
  const val DATE_FORMAT_DD_MM = "dd MMM"
  const val DATE_FORMAT_YYYY_MM = "yyyyMM"
  const val DATE_FORMAT_YYYY_DASH_MM = "yyyy-MM"
  const val DATE_FORMAT_MM_YYYY = "MM/yyyy"
  const val DATE_FORMAT_YYYY_MM_DD_HH_mm_ss = "yyyy年MM月dd日 HH:mm:ss"
  private const val TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]" //HH:00
  const val DATE_TIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ssZZZZZ"

  val currentDateString: Date
    get() = Calendar.getInstance().time

  val calendar: Calendar
    get() = Calendar.getInstance(Locale.JAPAN)

  fun getDaysInMonth(month: Int, year: Int): Int {
    return when (month) {
      Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
      Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
      Calendar.FEBRUARY -> if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) 29 else 28
      else -> throw IllegalArgumentException("Invalid Month") as Throwable
    }
  }

  private fun getDayOfWeekDisplay(dayOfWeek: Int): String {
    return when (dayOfWeek) {
      Calendar.MONDAY -> "(月)"
      Calendar.TUESDAY -> "(火)"
      Calendar.WEDNESDAY -> "(水)"
      Calendar.THURSDAY -> "(木)"
      Calendar.FRIDAY -> "(金)"
      Calendar.SATURDAY -> "(木)"
      Calendar.SUNDAY -> "(土)"
      else -> ""
    }
  }

  fun getDateStringFromDatePicker(
    year: Int, month: Int, dayOfMonth: Int,
    outputFormat: String
  ): String? {
    return convertDatePickerToDate(year, month, dayOfMonth).convertDateToString(outputFormat)
  }

  fun getTimeStringFromTimePicker(hourOfDay: Int, minute: Int, outputFormat: String): String? {
    return convertTimePickerToDate(hourOfDay, minute).convertDateToString(outputFormat)
  }

  fun convertTimePickerToDate(hourOfDay: Int, minute: Int): Date {
    val cal = Calendar.getInstance()
    cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
    cal.set(Calendar.MINUTE, minute)
    return cal.time
  }

  fun convertDatePickerToDate(year: Int, month: Int, day: Int): Date {
    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR, year)
    cal.set(Calendar.MONTH, month)
    cal.set(Calendar.DAY_OF_MONTH, day)
    return cal.time
  }

  fun getCurrentDateTimeDisPlay(format: String, locale: Locale = Locale.JAPAN): String? {
    return Date().convertDateToString(format, locale)
  }

  fun getDateTimeDisPlay(date: Date, format: String): String? {
    return date.convertDateToString(format)
  }

  fun getCurrentDateSearchCourse(format: String, locale: Locale = Locale.JAPAN): String? {
    val cal: Calendar = getCalendarActualForSearch()
    return Date(cal.timeInMillis).convertDateToString(format, locale)
  }

  fun getCurrentTimeSearchCourse(): String? {
    val cal: Calendar = getCalendarActualForSearch()
    val minute =
      if (cal.get(Calendar.MINUTE) < 10) "0${cal.get(Calendar.MINUTE)}" else cal.get(
        Calendar.MINUTE).toString()
    return cal.get(Calendar.HOUR_OF_DAY).toString() + ":" + minute
  }

  fun getCalendarActualForSearch(): Calendar {
    val cal: Calendar = Calendar.getInstance()
    cal.add(Calendar.MINUTE, MINIMUM_MINUTE_DURATION)
    if (cal.get(Calendar.MINUTE) < 30) {
      cal.set(Calendar.MINUTE, 30)
    } else if (cal.get(Calendar.MINUTE) > 30) {
      cal.add(Calendar.HOUR_OF_DAY, 1)
      cal.set(Calendar.MINUTE, 0)
    }
    cal.set(Calendar.SECOND, 0)
    return cal
  }

  @JvmStatic
  fun convertDateTimeWithFormat(
    dateTime: String, fromFormat: String, toFormat: String,
    locale: Locale = Locale.JAPAN
  ): String {
    var spf = SimpleDateFormat(fromFormat, locale)
    val newDate = spf.parse(dateTime)
    spf = SimpleDateFormat(toFormat, locale)
    return spf.format(newDate)
  }

  fun isValidTime(time: String): Boolean {
    val pattern: Pattern = Pattern.compile(TIME24HOURS_PATTERN)
    return pattern.matcher(time).matches()
  }

  fun twoDaysEqual(firstDay: Date?, secondDay: Date?): Boolean {
    return firstDay?.convertDateToDate(
      outputFormat = DATE_TIME_FORMAT_YYYY_MM_DD
    ) == secondDay?.convertDateToDate(
      outputFormat = DATE_TIME_FORMAT_YYYY_MM_DD
    )
  }


  fun replaceHHmm(date: String?, hour: String?, locale: Locale = Locale.JAPAN): String {
    if (date.isNullOrBlank() || hour.isNullOrBlank()) return ""
    val newDate = date?.convertUiFormatToDataFormat(
      outputFormat = DATE_TIME_FORMAT_YYYY_MM_DD_EN,
      locale = locale
    )
    val newDateTime = newDate + BLANK + hour
    return newDateTime.convertUiFormatToDataFormat(
      inputFormat = DATE_TIME_FORMAT_YYYY_MM_DD_HH_MM,
      outputFormat = DATE_TIME_FORMAT_UTC,
      locale = locale
    ).toString()
  }

  fun getCurrentTimeFormat(format: String): String? {
    val cal: Calendar = getCalendarActualForSearch()
    return Date(cal.timeInMillis).convertDateToString(outputFormat = format)
  }

  fun convertUTCTimeToLocalTimeDisplay(time: String, inputFormat: String,
                                       outputFormat: String, locale: Locale): String? {
    if (TextUtils.isEmpty(time)) {
      return ""
    }
    Calendar.getInstance(TimeZone.getTimeZone("GMT"), Locale.getDefault())
    val fromFormat = SimpleDateFormat(inputFormat, locale)
    val newDate = fromFormat.parse(time)

    val newFormat = SimpleDateFormat(outputFormat, locale)
    return try {
      newFormat.format(newDate!!)
    } catch (e: ParseException) {
      ""
    }
  }
  fun isToday(day: String): Boolean {
    if (TextUtils.isEmpty(day)) {
      return false
    }

    val date = day.convertStringToDate(DATE_TIME_FORMAT_YYYY_MM_DD)

    return DateUtils.isToday(date.time)
  }
  fun convertIOStoDefault(input: String): String {
    val defaultTimezone = TimeZone.getDefault().id;
    val  date = SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSS'Z'", Locale.getDefault()).parse(input.replace("Z$", "+0000"))
    var output = SimpleDateFormat(DATE_TIME_FORMAT_YYYY_MM_DD)
    return try {
      output.format(date).toString()
    } catch (e: ParseException) {
      // TODO Auto-generated catch block
      e.printStackTrace()
    }.toString()
  }

}