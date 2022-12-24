package com.haris.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity

@TypeConverters(AgenturenTypeConverters::class)
@Database(entities = [TodoEntity::class], version = 2)
abstract class AgenturenDb : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}