package com.haris.domain.repositories

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