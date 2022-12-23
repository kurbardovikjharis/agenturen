package com.haris.home

import com.haris.domain.Interactor
import com.haris.home.repositories.HomeRepository
import javax.inject.Inject

internal class DeleteTodo @Inject constructor(
    private val repository: HomeRepository
) : Interactor<Long>() {

    override suspend fun doWork(params: Long) {
        repository.deleteTodo(params)
    }
}