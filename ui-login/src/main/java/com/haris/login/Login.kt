package com.haris.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haris.ui.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Login(onLogin: () -> Unit) {
    Login(hiltViewModel(), onLogin)
}

@Composable
private fun Login(viewModel: LoginViewModel, onLogin: () -> Unit) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(true) {
        viewModel.singleEvent.collectLatest {
            if (it == SingleEvent.Success) {

            } else {

            }
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.email,
            onValueChange = { viewModel.updateEmail(it) },
            label = {
                Text(text = stringResource(id = R.string.login_email_label))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        TextField(
            value = state.password,
            onValueChange = { viewModel.updatePassword(it) },
            label = {
                Text(text = stringResource(id = R.string.login_password_label))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.login(onLogin) }, enabled = state.isButtonEnabled) {
            Text(text = stringResource(id = R.string.login_button))
        }
    }
}