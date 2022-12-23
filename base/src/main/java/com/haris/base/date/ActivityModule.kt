package com.haris.base.date

import android.app.Activity
import androidx.core.os.ConfigurationCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@InstallIn(ActivityComponent::class)
@Module
class ActivityModule {
    @Provides
    @MediumDate
    fun provideMediumDateFormatter(
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
    }

    @Provides
    @MediumDateTime
    fun provideDateTimeFormatter(
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale)
    }

    @Provides
    @ShortDate
    fun provideShortDateFormatter(
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale)
    }

    @Provides
    @ShortTime
    fun provideShortTimeFormatter(
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale)
    }

    @Provides
    fun provideActivityLocale(activity: Activity): Locale {
        return ConfigurationCompat.getLocales(activity.resources.configuration).get(0)
            ?: Locale.getDefault()
    }
}
