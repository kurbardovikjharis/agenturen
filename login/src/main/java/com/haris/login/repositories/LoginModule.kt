package com.haris.login.repositories

import com.haris.base.AppPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@InstallIn(ViewModelComponent::class)
@Module
internal object LoginModule {

    @Provides
    fun provideApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideRepository(api: LoginApi, appPreferences: AppPreferences): LoginRepository {
        return LoginRepositoryImpl(api, appPreferences)
    }
}