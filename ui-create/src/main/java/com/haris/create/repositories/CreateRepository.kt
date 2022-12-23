package com.haris.create.repositories

import com.haris.data.entities.Type

interface CreateRepository {

    suspend fun addTodo(title: String, description: String, type: Type)
}