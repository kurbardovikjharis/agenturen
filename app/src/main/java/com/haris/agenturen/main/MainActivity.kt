package com.haris.agenturen.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.haris.agenturen.ui.theme.AgenturenTheme
import com.haris.base.AgenturenDateFormatter
import com.haris.base.LocalAgenturenDateFormatter
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
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Main()
                    }
                }
            }
        }
    }
}
