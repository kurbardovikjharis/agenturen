package com.haris.create

import com.haris.base.Interactor
import com.haris.create.repositories.CreateRepository
import javax.inject.Inject

internal class AddTodo @Inject constructor(
    private val repository: CreateRepository
) : Interactor<String>() {

    override suspend fun doWork(params: String) {
        repository.addTodo(params)
    }
}