package com.haris.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

@Composable
fun Home(navigateToCreate: () -> Unit) {
    Home(hiltViewModel(), navigateToCreate)
}

@Composable
private fun Home(viewModel: HomeViewModel, navigateToCreate: () -> Unit) {
    val list = viewModel.pagedList.collectAsLazyPagingItems()

    Scaffold(floatingActionButton = {
        SmallFloatingActionButton(onClick = navigateToCreate) {
            Text(modifier = Modifier.padding(16.dp), text = "Create")
        }
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(list) { item ->
                Text(text = item?.text ?: "")
            }
        }
    }
}