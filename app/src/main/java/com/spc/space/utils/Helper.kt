package com.spc.space.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object Helper {

    fun convertTimeFormat(timeStr: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dt = LocalDateTime.parse(timeStr, formatter)
        return dt.format(DateTimeFormatter.ofPattern("h:mm a")).toLowerCase()
    }


    fun convertTimeFormatToDate(timeStr: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dt = LocalDateTime.parse(timeStr, formatter)
        val month = dt.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        val day = dt.dayOfMonth
        val year = dt.year
        return "$month $day, $year"
    }

}