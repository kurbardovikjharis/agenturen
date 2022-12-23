package com.haris.create.repositories

import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import java.time.OffsetDateTime
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : CreateRepository {

    override suspend fun addTodo(title: String, description: String, type: Type) {
        val id = System.currentTimeMillis()
        dao.insert(
            TodoEntity(
                uid = id,
                title = title,
                description = description,
                date = OffsetDateTime.now(),
                type = type
            )
        )
    }
}