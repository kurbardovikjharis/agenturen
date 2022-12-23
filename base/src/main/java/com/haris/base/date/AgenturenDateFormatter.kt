package com.haris.base.date

import dagger.Lazy
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AgenturenDateFormatter @Inject constructor(
    @ShortTime private val shortTimeFormatter: Lazy<DateTimeFormatter>,
    @ShortDate private val shortDateFormatter: Lazy<DateTimeFormatter>,
    @MediumDate private val mediumDateFormatter: Lazy<DateTimeFormatter>,
    @MediumDateTime private val mediumDateTimeFormatter: Lazy<DateTimeFormatter>,
) {
    fun formatMediumDate(temporalAmount: LocalDate): String {
        return mediumDateFormatter.get().format(temporalAmount)
    }

    fun formatMediumDate(temporalAmount: OffsetDateTime): String {
        return mediumDateFormatter.get().format(temporalAmount)
    }

    fun formatShortTime(localTime: LocalTime): String {
        return shortTimeFormatter.get().format(localTime)
    }
}
