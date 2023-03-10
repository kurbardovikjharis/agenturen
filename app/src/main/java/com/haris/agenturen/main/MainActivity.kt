package com.haris.agenturen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import com.haris.agenturen.ui.theme.AgenturenTheme
import com.haris.base.date.AgenturenDateFormatter
import com.haris.base.date.LocalAgenturenDateFormatter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    internal lateinit var agenturenDateFormatter: AgenturenDateFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgenturenTheme {
                CompositionLocalProvider(
                    LocalAgenturenDateFormatter provides agenturenDateFormatter,
                ) {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Main()
                    }
                }
            }
        }
    }
}
