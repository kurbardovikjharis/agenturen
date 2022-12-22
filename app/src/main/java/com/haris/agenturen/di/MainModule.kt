package com.haris.agenturen.di

import com.haris.agenturen.repositories.MainRepository
import com.haris.agenturen.repositories.MainRepositoryImpl
import com.haris.base.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object MainModule {

    @Provides
    fun provideRepository(appPreferences: AppPreferences): MainRepository {
        return MainRepositoryImpl(appPreferences)
    }
}