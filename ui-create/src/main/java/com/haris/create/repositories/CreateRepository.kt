package com.haris.create.repositories

import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import java.time.LocalDate
import java.time.LocalTime

interface CreateRepository {

    suspend fun addTodo(
        id: Long,
        title: String,
        description: String,
        time: LocalTime,
        date: LocalDate?,
        type: Type
    )

    suspend fun getTodo(id: Long): TodoEntity
}