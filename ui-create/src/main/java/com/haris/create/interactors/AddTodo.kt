package com.haris.create.interactors

import com.haris.create.repositories.CreateRepository
import com.haris.data.entities.Type
import com.haris.domain.Interactor
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

internal class AddTodo @Inject constructor(
    private val repository: CreateRepository
) : Interactor<AddTodo.Params>() {

    override suspend fun doWork(params: Params) {
        repository.addTodo(
            id = params.id,
            title = params.title,
            description = params.description,
            time = params.time,
            date = params.date,
            type = params.type
        )
    }

    data class Params(
        val id: Long,
        val title: String,
        val description: String,
        val time: LocalTime,
        val date: LocalDate?,
        val type: Type
    )
}