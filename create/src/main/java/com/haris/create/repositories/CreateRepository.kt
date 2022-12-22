package com.haris.create.repositories

interface CreateRepository {

    suspend fun addTodo(title: String, description: String)
}