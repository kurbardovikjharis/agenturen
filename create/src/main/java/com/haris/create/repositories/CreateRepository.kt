package com.haris.create.repositories

interface CreateRepository {

    suspend fun addTodo(text: String)
}