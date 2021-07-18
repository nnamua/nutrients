package com.paulaumann.nutrients.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Converter functions used by Room to convert from Date to Long timestamps
 */

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}