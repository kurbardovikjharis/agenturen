package com.haris.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object AgenturenTypeConverters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?) =
        value?.let { formatter.parse(value, OffsetDateTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? = date?.format(formatter)

    @TypeConverters
    @JvmStatic
    fun fromType(type: Type): String {
        return type.name
    }

    @TypeConverters
    @JvmStatic
    fun toType(value: String): Type {
        return if (value == Type.Daily.name) {
            Type.Daily
        } else {
            Type.Weekly
        }
    }
}
