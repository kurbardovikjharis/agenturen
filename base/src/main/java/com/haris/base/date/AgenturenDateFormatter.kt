package com.haris.base.date

import dagger.Lazy
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class AgenturenDateFormatter @Inject constructor(
    @ShortTime private val shortTimeFormatter: Lazy<DateTimeFormatter>,
    @ShortDate private val shortDateFormatter: Lazy<DateTimeFormatter>,
    @MediumDate private val mediumDateFormatter: Lazy<DateTimeFormatter>,
    @MediumDateTime private val mediumDateTimeFormatter: Lazy<DateTimeFormatter>,
) {
    fun formatShortDate(temporalAmount: OffsetDateTime): String {
        return shortDateFormatter.get().format(temporalAmount)
    }

    fun formatMediumDate(temporalAmount: OffsetDateTime): String {
        return mediumDateFormatter.get().format(temporalAmount)
    }

    fun formatMediumDateTime(temporalAmount: OffsetDateTime): String {
        return mediumDateTimeFormatter.get().format(temporalAmount)
    }

    fun formatShortTime(localTime: OffsetDateTime): String {
        return shortTimeFormatter.get().format(localTime)
    }

    fun formatTime(dateTime: OffsetDateTime): String {
        return "${dateTime.hour}:${dateTime.minute}"
    }
}
