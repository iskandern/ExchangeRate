package com.example.exchangerate.utils

import java.text.SimpleDateFormat
import java.util.*

const val UNIX_TIME_SERIES_INTERVAL = 518400 // 6 days (last included)
const val MILLISECONDS_IN_SECONDS = 1000
const val UNIX_TIMESTAMP_BORDER = 86400  // 1 day in seconds
val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd")
val DAY_FORMAT = SimpleDateFormat("EEE")

inline fun getCurrentUNIXDate() : Long {
    return System.currentTimeMillis() / MILLISECONDS_IN_SECONDS
}

inline fun getUNIXDate(date: String) : Long? {
    return DATE_FORMAT.parse(date)?.time
}

inline fun getStringDate(date: Long) : String {
    val dateObj = Date(date)
    return DATE_FORMAT.format(dateObj)
}

inline fun getDay(date: Long) : String {
    val dateObj = Date(date * MILLISECONDS_IN_SECONDS)
    return DAY_FORMAT.format(dateObj)
}

inline fun newRequestNeeds(baseUnixTimestamp: Long) : Boolean {
    return baseUnixTimestamp < getCurrentUNIXDate() - UNIX_TIMESTAMP_BORDER
}

fun getStartEndTimePoints(lastUNIXDate: Long) : Pair<String, String> {
    val lastMillisDate = lastUNIXDate * MILLISECONDS_IN_SECONDS
    val endDate = Date(lastMillisDate)
    val startDate = Date(lastMillisDate - UNIX_TIME_SERIES_INTERVAL * MILLISECONDS_IN_SECONDS)

    return DATE_FORMAT.format(startDate) to DATE_FORMAT.format(endDate)
}