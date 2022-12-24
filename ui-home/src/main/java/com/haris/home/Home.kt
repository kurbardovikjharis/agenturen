package com.haris.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.haris.base.date.LocalAgenturenDateFormatter
import com.haris.ui.R

@Composable
fun Home(navigateToCreate: (Long?) -> Unit) {
    Home(hiltViewModel(), navigateToCreate)
}

@Composable
private fun Home(viewModel: HomeViewModel, navigateToCreate: (Long?) -> Unit) {
    val list = viewModel.pagedList.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(onClick = { navigateToCreate(null) }) {
                Text(modifier = Modifier.padding(16.dp), text = "Create")
            }
        }) {
        LazyColumn(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(list) { item ->
                val formatter = LocalAgenturenDateFormatter.current
                val date = item?.date
                val time = item?.time
                val formattedDate =
                    if (date != null) formatter.formatMediumDate(date) else ""
                val formattedTime =
                    if (time != null) formatter.formatShortTime(time) else ""

                Card(
                    onClick = {
                        viewModel.delete(item?.id ?: 0L)
                    }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (item?.title?.isNotEmpty() == true) {
                                Text(text = item.title)
                            }
                            val description = item?.description
                            if (description?.isNotEmpty() == true) {
                                Text(text = description)
                            }
                            if (formattedTime.isNotEmpty()) {
                                Text(text = formattedTime)
                            }
                            if (formattedDate.isNotEmpty()) {
                                Text(text = formattedDate)
                            }
                            if (item?.type?.name?.isNotEmpty() == true) {
                                Text(text = item.type.name)
                            }
                        }

                        Button(onClick = { navigateToCreate(item?.id) }) {
                            Text(text = stringResource(id = R.string.edit_button))
                        }
                    }
                }
            }
        }
    }
}