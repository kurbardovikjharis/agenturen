package com.haris.create.repositories

import com.haris.alarm.AlarmManager
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import com.haris.data.entities.Type
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

internal class CreateRepositoryImpl @Inject constructor(
    private val dao: TodoDao,
    private val alarmManager: AlarmManager
) : CreateRepository {

    override suspend fun addTodo(
        id: Long,
        title: String,
        description: String,
        time: LocalTime,
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

        val now = Calendar.getInstance()
        val year = date?.year ?: now.get(Calendar.YEAR)
        val month = if (date != null) date.monthValue - 1 else now.get(Calendar.MONTH)
        val day = date?.dayOfMonth ?: now.get(Calendar.DAY_OF_MONTH)

        val calender = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setAlarm(
            id = finalId.toInt(),
            isDaily = type == Type.Daily,
            title = title,
            description = description,
            time = time.format(DateTimeFormatter.ISO_LOCAL_TIME),
            timeInMillis = calender.timeInMillis
        )
    }

    override suspend fun getTodo(id: Long): TodoEntity {
        return dao.getItem(id)
    }
}