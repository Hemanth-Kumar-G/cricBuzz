package com.hemanth.cricbuzz.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    @SuppressLint("SimpleDateFormat")
    fun ISODateToMillis(isoDateFormat: String?): Long {
        var epoch: Long = 0
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        df.timeZone = TimeZone.getTimeZone("GMT+11")
        val date: Date
        try {
            date = df.parse(isoDateFormat)
            epoch = date.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return epoch
    }

}