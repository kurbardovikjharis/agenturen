package com.haris.create.repositories

import com.haris.data.TodoDao
import com.haris.data.TodoEntity
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : CreateRepository {

    override suspend fun addTodo(text: String) {
        val id = System.currentTimeMillis()
        dao.insert(TodoEntity(id, text))
    }
}