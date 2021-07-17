package com.paulaumann.nutrients.util

import android.util.Log
import android.util.Pair
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
    Log.d("toTimeRange", "week=$week, year=$year, after=$after, before=$before")
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