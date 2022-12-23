package com.haris.data

import android.content.Context
import android.os.Debug
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.haris.data.daos.TodoDao
import com.haris.data.entities.TodoEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@TypeConverters(AgenturenTypeConverters::class)
@Database(entities = [TodoEntity::class], version = 1)
abstract class AgenturenDb : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

@InstallIn(SingletonComponent::class)
@Module
object RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AgenturenDb {
        val builder = Room.databaseBuilder(context, AgenturenDb::class.java, "agenture.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun provideTodoDao(db: AgenturenDb) = db.todoDao()
}