package com.haris.home.repositories

import androidx.paging.PagingSource
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class HomeRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
) : HomeRepository {

    override fun getData(): PagingSource<Int, TodoEntity> {
        return dao.pagedListAll()
    }

    override suspend fun deleteTodo(id: Long) {
        dao.delete(id)
    }
}