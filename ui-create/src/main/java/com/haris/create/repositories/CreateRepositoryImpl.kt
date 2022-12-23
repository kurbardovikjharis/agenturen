package com.haris.create.repositories

import com.haris.alarm.AlarmManager
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
    private val alarmManager: AlarmManager
) : CreateRepository {

    override suspend fun addTodo(title: String, description: String, type: Type) {
        val id = System.currentTimeMillis()
        dao.insert(
            TodoEntity(
                id = id,
                title = title,
                description = description,
                date = OffsetDateTime.now(),
                type = type
            )
        )

        alarmManager.setAlarm(
            id = id.toInt(),
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