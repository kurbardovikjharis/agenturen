package com.haris.create.repositories

import com.haris.data.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
internal object CreateModule {

    @Provides
    fun provideRepository(dao: TodoDao): CreateRepository {
        return CreateRepositoryImpl(dao = dao)
    }
}