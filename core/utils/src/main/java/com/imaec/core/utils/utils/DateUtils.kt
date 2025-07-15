package com.imaec.core.utils.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private fun sdf(format: String): SimpleDateFormat = SimpleDateFormat(format, Locale.getDefault())

fun Date.formatDate(format: String): String = sdf(format).format(this)

fun String.parseDate(format: String): Date? = sdf(format).parse(this)

fun String.formatDate(fromFormat: String, toFormat: String): String =
    (parseDate(format = fromFormat) ?: Date()).formatDate(format = toFormat)

fun Long.diffDays(): Int {
    val calInstall = Calendar.getInstance().apply {
        timeInMillis = this@diffDays
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val calToday = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    val diffMillis = calToday.timeInMillis - calInstall.timeInMillis
    return (diffMillis / (1000 * 60 * 60 * 24)).toInt() + 1 // 1일차 포함
}

fun String.toYearMonthDay(format: String = "yyyy-MM-dd"): Triple<String, String, String> {
    val date = parseDate(format = format) ?: Date()
    return Triple(
        date.formatDate(format = "yyyy"),
        date.formatDate(format = "MM"),
        date.formatDate(format = "dd")
    )
}

fun Calendar.getYearMonthDay(): Triple<Int, Int, Int> = Triple(
    get(Calendar.YEAR),
    get(Calendar.MONTH) + 1,
    get(Calendar.DAY_OF_MONTH)
)

fun Calendar.isToday(): Boolean {
    val today = Calendar.getInstance()
    return get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
        get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
        get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)
}
