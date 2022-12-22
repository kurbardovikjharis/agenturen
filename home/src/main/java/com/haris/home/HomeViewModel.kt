package com.haris.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.haris.base.ObservableLoadingCounter
import com.haris.base.collectStatus
import com.haris.data.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getData: GetData,
    private val deleteTodo: DeleteTodo
) : ViewModel() {

    val pagedList: Flow<PagingData<TodoEntity>> = getData.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            getData(GetData.Params(PAGING_CONFIG))
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            deleteTodo(id).collectStatus(ObservableLoadingCounter())
        }
    }

    companion object {
        private val PAGING_CONFIG = PagingConfig(
            pageSize = 16,
            initialLoadSize = 32,
        )
    }
}