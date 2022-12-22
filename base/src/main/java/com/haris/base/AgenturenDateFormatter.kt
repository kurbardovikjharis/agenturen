package com.haris.base

import android.text.format.DateUtils
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

    fun formatShortRelativeTime(dateTime: OffsetDateTime): String {
        val now = OffsetDateTime.now()

        return if (dateTime.isBefore(now)) {
            if (dateTime.year == now.year || dateTime.isAfter(now.minusDays(7))) {
                // Within the past week
                DateUtils.getRelativeTimeSpanString(
                    dateTime.toEpochSecond() * 1000,
                    now.toEpochSecond() * 1000,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_SHOW_DATE,
                ).toString()
            } else {
                // More than 7 days ago
                formatShortDate(dateTime)
            }
        } else {
            if (dateTime.year == now.year || dateTime.isBefore(now.plusDays(14))) {
                // In the near future (next 2 weeks)
                DateUtils.getRelativeTimeSpanString(
                    dateTime.toEpochSecond() * 1000,
                    now.toEpochSecond() * 1000,
                    DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_SHOW_DATE,
                ).toString()
            } else {
                // In the far future
                formatShortDate(dateTime)
            }
        }
    }
}
