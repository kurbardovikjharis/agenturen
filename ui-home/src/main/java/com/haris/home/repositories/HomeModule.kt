package com.haris.home.repositories

import com.haris.data.daos.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal object HomeModule {

    @Provides
    fun provideRepository(todoDao: TodoDao): HomeRepository {
        return HomeRepositoryImpl(todoDao)
    }
}