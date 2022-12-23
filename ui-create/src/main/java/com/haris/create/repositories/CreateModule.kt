package com.haris.create.repositories

import com.haris.alarm.AlarmManager
import com.haris.data.daos.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal object CreateModule {

    @Provides
    fun provideRepository(dao: TodoDao, alarmManager: AlarmManager): CreateRepository {
        return CreateRepositoryImpl(dao = dao, alarmManager = alarmManager)
    }
}