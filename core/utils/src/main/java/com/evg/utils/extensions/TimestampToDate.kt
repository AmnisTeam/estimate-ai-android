package com.evg.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toDateString(): String {
    val date = Date(this)
    val formatter = SimpleDateFormat("dd.MM", Locale.getDefault())
    return formatter.format(date)
}