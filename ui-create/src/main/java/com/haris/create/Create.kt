package com.haris.create

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haris.data.entities.Type
import com.haris.ui.R

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

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.save()
                navigateUp()
            }) {
            val text =
                if (state.isUpdate) stringResource(id = R.string.update_button)
                else stringResource(id = R.string.create_button)
            Text(text = text)
        }
    }
}