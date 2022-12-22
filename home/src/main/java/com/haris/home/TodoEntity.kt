package com.haris.home

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "text") val text: String?
)