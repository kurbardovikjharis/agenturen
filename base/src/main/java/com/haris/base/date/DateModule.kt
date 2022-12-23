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
class DateModule {
    @Provides
    @Date
    fun provideMediumDateFormatter(
        locale: Locale,
    ): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
    }

    @Provides
    @Time
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
