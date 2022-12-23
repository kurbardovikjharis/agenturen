package com.haris.home.repositories

import androidx.paging.PagingSource
import com.haris.data.entities.TodoEntity

internal interface HomeRepository {

    fun getData(): PagingSource<Int, TodoEntity>

    suspend fun deleteTodo(id: Long)
}