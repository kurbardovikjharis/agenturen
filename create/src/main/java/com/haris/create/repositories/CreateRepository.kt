package com.haris.create.repositories

import com.haris.data.Type

interface CreateRepository {

    suspend fun addTodo(title: String, description: String, type: Type)
}