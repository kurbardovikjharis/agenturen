package com.haris.create.repositories

import com.haris.alarm.AlarmManager
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
    private val alarmManager: AlarmManager
) : CreateRepository {

    override suspend fun addTodo(
        id: Long,
        title: String,
        description: String,
        time: LocalTime?,
        date: LocalDate?,
        type: Type
    ) {
        val finalId = if (id != -1L) id else System.currentTimeMillis()
        dao.insert(
            TodoEntity(
                id = finalId,
                title = title,
                description = description,
                time = time,
                date = date,
                type = type,
            )
        )

        alarmManager.setAlarm(
            id = finalId.toInt(),
            isDaily = type == Type.Daily,
            title = title,
            description = description,
            date = OffsetDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        )
    }

    override suspend fun getTodo(id: Long): TodoEntity {
        return dao.getItem(id)
    }
}