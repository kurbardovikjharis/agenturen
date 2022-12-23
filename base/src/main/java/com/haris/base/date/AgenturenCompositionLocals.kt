package com.haris.base.date

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAgenturenDateFormatter = staticCompositionLocalOf<AgenturenDateFormatter> {
    error("AgenturenDateFormatter not provided")
}