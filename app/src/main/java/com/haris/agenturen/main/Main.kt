package com.haris.agenturen.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.haris.agenturen.AppNavigation

@Composable
fun Main() {
    val navController = rememberAnimatedNavController()

    AppNavigation(
        navController = navController,
        modifier = Modifier.fillMaxHeight(),
        viewModel = hiltViewModel()
    )
}