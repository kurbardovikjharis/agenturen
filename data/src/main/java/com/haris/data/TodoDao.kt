package com.haris.data

import androidx.paging.PagingSource
import androidx.room.*

@Dao
abstract class TodoDao {

    @Transaction
    @Query("SELECT * FROM todo")
    abstract fun pagedListAll(): PagingSource<Int, TodoEntity>

    @Insert
    abstract suspend fun insert(entity: TodoEntity): Long

    @Query("DELETE FROM todo WHERE uid = :id")
    abstract suspend fun delete(id: Long): Int
}