package com.haris.create.repositories

import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type

interface CreateRepository {

    suspend fun addTodo(id: Long, title: String, description: String, type: Type)

    suspend fun getTodo(id: Long): TodoEntity
}