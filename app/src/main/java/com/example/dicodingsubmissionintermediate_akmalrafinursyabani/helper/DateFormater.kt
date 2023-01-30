package com.example.dicodingsubmissionintermediate_akmalrafinursyabani.helper

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}