package com.haris.domain.repositories

import com.haris.base.AppPreferences
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val appPreferences: AppPreferences
) : MainRepository {

    override suspend fun getToken(): String {
        return appPreferences.token() ?: ""
    }
}