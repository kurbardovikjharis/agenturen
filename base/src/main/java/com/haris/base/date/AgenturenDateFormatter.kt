package com.haris.base.date

import dagger.Lazy
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AgenturenDateFormatter @Inject constructor(
    @Time private val shortTimeFormatter: Lazy<DateTimeFormatter>,
    @Date private val mediumDateFormatter: Lazy<DateTimeFormatter>,
) {
    fun formatMediumDate(temporalAmount: LocalDate): String {
        return mediumDateFormatter.get().format(temporalAmount)
    }

    fun formatShortTime(localTime: LocalTime): String {
        return shortTimeFormatter.get().format(localTime)
    }
}
