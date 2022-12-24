package com.haris.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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

    val context = LocalContext.current
    LaunchedEffect(true) {
        viewModel.singleEvent.collectLatest {
            if (it == SingleEvent.Error) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
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
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            val isPasswordVisible = remember {
                mutableStateOf(false)
            }

            TextField(
                value = state.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = {
                    Text(text = stringResource(id = R.string.login_password_label))
                },
                visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.login(onLogin)
                    },
                ),
                trailingIcon = {
                    val painter = if (isPasswordVisible.value)
                        painterResource(id = R.drawable.ic_baseline_visibility_24)
                    else
                        painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                    val contentDescription =
                        if (isPasswordVisible.value) "Hide password" else "Show password"
                    IconButton(
                        onClick = { isPasswordVisible.value = !isPasswordVisible.value }
                    ) {
                        Icon(painter = painter, contentDescription = contentDescription)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { viewModel.login(onLogin) }, enabled = state.isButtonEnabled) {
                Text(text = stringResource(id = R.string.login_button))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onLogin) {
                Text(text = stringResource(id = R.string.skip_login_button))
            }
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}