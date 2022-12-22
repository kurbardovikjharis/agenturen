package com.haris.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "text") val text: String?
)