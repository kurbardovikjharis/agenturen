package com.haris.create.repositories

import com.haris.data.TodoDao
import com.haris.data.TodoEntity
import java.time.LocalDateTime
import java.time.OffsetDateTime
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao
) : CreateRepository {

    override suspend fun addTodo(title: String, description: String) {
        val id = System.currentTimeMillis()
        dao.insert(
            TodoEntity(
                uid = id,
                title = title,
                description = description,
                date = OffsetDateTime.now()
            )
        )
    }
}