package com.haris.home

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Query("SELECT * FROM todoentity")
    fun getAll(): PagingSource<Int, TodoEntity>

    @Insert
    fun insertAll(vararg users: TodoEntity)

    @Delete
    fun delete(user: TodoEntity)
}