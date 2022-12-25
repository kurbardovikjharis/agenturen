package com.haris.create.interactors

import com.haris.create.repositories.CreateRepository
import com.haris.data.entities.TodoEntity
import com.haris.domain.ResultInteractor
import javax.inject.Inject

internal class GetTodo @Inject constructor(
    private val repository: CreateRepository
) : ResultInteractor<Long, TodoEntity>() {

    override suspend fun doWork(params: Long): TodoEntity {
        return repository.getTodo(params)
    }
}