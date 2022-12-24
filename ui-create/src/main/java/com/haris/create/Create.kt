package com.haris.create

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.haris.base.date.LocalAgenturenDateFormatter
import com.haris.data.entities.Type
import com.haris.ui.R
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun Create(navigateUp: () -> Unit) {
    Create(viewModel = hiltViewModel(), navigateUp = navigateUp)
}

@Composable
private fun Create(viewModel: CreateViewModel, navigateUp: () -> Unit) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    val errorMessage = stringResource(id = R.string.common_error)
    LaunchedEffect(true) {
        viewModel.singleEvent.collectLatest {
            if (it == SingleEvent.Error) {
                Toast.makeText(
                    context,
                    errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.title,
            onValueChange = { viewModel.updateTitle(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.create_title_label)
                )
            })
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            value = state.description,
            onValueChange = { viewModel.updateDescription(it) },
            label = {
                Text(
                    text = stringResource(id = R.string.create_description_label)
                )
            })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Time(state.time) {
                viewModel.updateTime(it)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Date(state.date) {
                viewModel.updateDate(it)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = state.type == Type.Daily,
                    onClick = { viewModel.updateType(Type.Daily) })
                Text(text = stringResource(id = R.string.create_daily))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButton(
                    selected = state.type == Type.Weekly,
                    onClick = { viewModel.updateType(Type.Weekly) })
                Text(text = stringResource(id = R.string.create_weekly))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SaveButton(enabled = state.enabled, isUpdate = state.isUpdate) {
            viewModel.save(navigateUp)
        }
    }
}

@Composable
private fun Time(time: LocalTime?, onTimeSelected: (LocalTime) -> Unit) {
    val formatter = LocalAgenturenDateFormatter.current
    val formatted = if (time != null) formatter.formatShortTime(time) else "set time*"

    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.common_ok))
            negativeButton(stringResource(id = R.string.common_cancel))
        }
    ) {
        timepicker { time ->
            onTimeSelected(time)
        }
    }

    Button(onClick = { dialogState.show() }) {
        Text(text = formatted)
    }
}

@Composable
private fun Date(date: LocalDate?, onDateSelected: (LocalDate) -> Unit) {
    val formatter = LocalAgenturenDateFormatter.current
    val formatted = if (date != null) formatter.formatMediumDate(date) else "set date"

    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.common_ok))
            negativeButton(stringResource(id = R.string.common_cancel))
        }
    ) {
        datepicker { date ->
            onDateSelected(date)
        }
    }

    Button(onClick = { dialogState.show() }) {
        Text(text = formatted)
    }
}

@Composable
private fun SaveButton(enabled: Boolean, isUpdate: Boolean, onSaveClicked: () -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        onSaveClicked() // handle click even if the user doesn't allow post notifications
    }

    val context = LocalContext.current
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    onSaveClicked()
                }
                else -> {
                    // Asking for permission
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        onSaveClicked()
                    }
                }
            }
        },
        enabled = enabled
    ) {
        val text =
            if (isUpdate) stringResource(id = R.string.update_button)
            else stringResource(id = R.string.create_button)
        Text(text = text)
    }
}