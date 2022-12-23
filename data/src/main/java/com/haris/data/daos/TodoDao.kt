package com.haris.data.daos

import androidx.paging.PagingSource
import androidx.room.*
import com.haris.data.entities.TodoEntity

@Dao
abstract class TodoDao {

    @Transaction
    @Query("SELECT * FROM todo")
    abstract fun pagedListAll(): PagingSource<Int, TodoEntity>

    @Transaction
    @Query("SELECT * FROM todo WHERE id = :id")
    abstract suspend fun getItem(id: Long): TodoEntity

    @Insert
    abstract suspend fun insert(entity: TodoEntity): Long

    @Query("DELETE FROM todo WHERE id = :id")
    abstract suspend fun delete(id: Long): Int
}