package com.haris.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Create(navigateUp: () -> Unit) {
    Create(viewModel = hiltViewModel(), navigateUp = navigateUp)
}

@Composable
private fun Create(viewModel: CreateViewModel, navigateUp: () -> Unit) {
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = state.value, onValueChange = { viewModel.updateText(it) })

        Button(
            onClick = {
                viewModel.save()
                navigateUp()
            }) {
            Text(text = "save")
        }
    }
}