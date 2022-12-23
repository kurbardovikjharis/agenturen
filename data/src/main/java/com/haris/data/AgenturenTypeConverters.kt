package com.haris.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.haris.data.entities.Type
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object AgenturenTypeConverters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val formatterTime = DateTimeFormatter.ISO_LOCAL_TIME

    @TypeConverter
    @JvmStatic
    fun toLocalDate(value: String?) =
        value?.let { formatter.parse(value, LocalDate::from) }

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? = date?.format(formatter)

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

    @TypeConverter
    @JvmStatic
    fun toLocalTime(value: String?) =
        value?.let { formatterTime.parse(value, LocalTime::from) }

    @TypeConverter
    @JvmStatic
    fun fromLocalTime(time: LocalTime?): String? = time?.format(formatterTime)
}
