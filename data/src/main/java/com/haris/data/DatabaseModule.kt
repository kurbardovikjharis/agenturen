package com.haris.data

import android.content.Context
import android.os.Debug
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
            .addMigrations(MIGRATION_1_2)
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun provideTodoDao(db: AgenturenDb) = db.todoDao()
}