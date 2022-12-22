package com.haris.base

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAgenturenDateFormatter = staticCompositionLocalOf<AgenturenDateFormatter> {
    error("AgenturenDateFormatter not provided")
}