package com.haris.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.haris.base.LocalAgenturenDateFormatter

@Composable
fun Home(navigateToCreate: () -> Unit) {
    Home(hiltViewModel(), navigateToCreate)
}

@Composable
private fun Home(viewModel: HomeViewModel, navigateToCreate: () -> Unit) {
    val list = viewModel.pagedList.collectAsLazyPagingItems()

    Scaffold(
        floatingActionButton = {
            SmallFloatingActionButton(onClick = navigateToCreate) {
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
                val formattedDate =
                    if (date != null) formatter.formatShortDate(date) else ""
                val formattedTime =
                    if (date != null) formatter.formatTime(date) else ""

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        viewModel.delete(item?.uid ?: 0L)
                    }) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = item?.title ?: "")
                        Text(text = item?.description ?: "")
                        Text(text = formattedTime)
                        Text(text = formattedDate)
                        Text(text = item?.type?.name ?: "")
                    }
                }
            }
        }
    }
}