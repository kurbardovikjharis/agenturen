package com.haris.create

import com.haris.base.Interactor
import com.haris.create.repositories.CreateRepository
import javax.inject.Inject

internal class AddTodo @Inject constructor(
    private val repository: CreateRepository
) : Interactor<AddTodo.Params>() {

    override suspend fun doWork(params: Params) {
        repository.addTodo(title = params.title, description = params.description)
    }

    data class Params(
        val title: String,
        val description: String
    )
}