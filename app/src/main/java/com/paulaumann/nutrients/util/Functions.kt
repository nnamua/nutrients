package com.paulaumann.nutrients.util

import android.graphics.Canvas
import android.graphics.Color
import android.text.StaticLayout
import android.util.Log
import android.util.Pair
import androidx.core.graphics.withTranslation
import java.util.*

/**
 * Utility function to convert a week/year to a range, consisting of a start and end date.
 * @param week Week of the year
 * @param year Year
 * @return A Pair, consisting of (start, end)
 */
fun toTimeRange(week: Int, year: Int): Pair<Date, Date> {
    val calendar = GregorianCalendar(Locale.GERMANY).apply {
        set(Calendar.YEAR, year)
        set(Calendar.WEEK_OF_YEAR, week)
        firstDayOfWeek = Calendar.MONDAY
    }
    // Retrieve entries after monday of week
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    }
    val after = calendar.time

    // Retrieve entries before monday of next week
    calendar.add(Calendar.WEEK_OF_YEAR, 1)
    val before = calendar.time
    return Pair(after, before)
}

/**
 * Utility function to convert a date to a range, consisting of a start and end date.
 * @param weekday Day of the week
 * @param week Week of the year
 * @param year Year
 * @return A Pair, consisting of (start, end)
 */
fun toTimeRange(weekday: Int, week: Int, year: Int): Pair<Date, Date> {
    val calendar = GregorianCalendar(Locale.GERMANY).apply {
        set(Calendar.YEAR, year)
        set(Calendar.WEEK_OF_YEAR, week)
        firstDayOfWeek = Calendar.MONDAY
    }
    // Retrieve entries after monday of week
    calendar.apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.DAY_OF_WEEK, weekday)
    }
    val after = calendar.time

    // Retrieve entries before monday of next week
    calendar.add(Calendar.DAY_OF_WEEK, 1)
    val before = calendar.time
    return Pair(after, before)
}

class MaterialColorGenerator {
    companion object {

        private val colors = arrayOf(
            "#f44336", "#673ab7", "#03a9f4", "#4caf50", "#ffeb3b", "#ff5722", "#4a148c", "#0d47a1",
            "#004d40", "#827717", "#e65100", "#e91e63", "#3f51b5", "#00bcd4", "#8bc34a", "#ffc107",
            "#b71c1c", "#311b92", "#01579b", "#1b5e20", "#f57f17", "#bf360c", "#9c27b0", "#2196f3",
            "#009688", "#cddc39", "#ff9800", "#880e4f", "#1a237e", "#006064", "#33691e", "#ff6f00"
        )

        private var colorPointer = 0

        fun palette(): Int {
            val color = Color.parseColor(colors[colorPointer])
            colorPointer = (colorPointer + 1) % colors.size
            return color
        }

        fun random(): Int{
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    }
}