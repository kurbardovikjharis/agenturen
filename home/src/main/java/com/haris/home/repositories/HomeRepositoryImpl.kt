package com.haris.home.repositories

import androidx.paging.PagingSource
import com.haris.home.TodoDao
import com.haris.home.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HomeRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
) : HomeRepository {

    override fun getData(): PagingSource<Int, TodoEntity> {
        return dao.getAll()
    }
}