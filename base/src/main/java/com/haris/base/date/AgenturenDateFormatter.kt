package com.haris.base.date

import dagger.Lazy
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AgenturenDateFormatter @Inject constructor(
    @Time private val timeFormatter: Lazy<DateTimeFormatter>,
    @Date private val dateFormatter: Lazy<DateTimeFormatter>,
) {
    fun formatMediumDate(temporalAmount: LocalDate): String {
        return dateFormatter.get().format(temporalAmount)
    }

    fun formatShortTime(localTime: LocalTime): String {
        return timeFormatter.get().format(localTime)
    }
}
