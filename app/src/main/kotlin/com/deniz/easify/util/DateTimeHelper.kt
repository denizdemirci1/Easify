package com.deniz.easify.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * @User: deniz.demirci
 * @Date: 5.03.2020
 */

object DateTimeHelper {

    fun getToday(): String {
        val today = Calendar.getInstance().time
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(today)
    }
}