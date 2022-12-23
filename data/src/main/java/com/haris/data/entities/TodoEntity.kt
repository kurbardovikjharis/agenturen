package com.haris.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.time.OffsetDateTime

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "time") val time: LocalTime?,
    @ColumnInfo(name = "date") val date: OffsetDateTime,
    @ColumnInfo(name = "type") val type: Type,
)