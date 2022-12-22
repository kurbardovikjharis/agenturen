package com.haris.home.repositories

import androidx.paging.PagingSource
import com.haris.home.TodoEntity

internal interface HomeRepository {

    fun getData(): PagingSource<Int, TodoEntity>
}