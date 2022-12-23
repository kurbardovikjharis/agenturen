package com.haris.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

private const val TOKEN_KEY = "TOKEN_KEY"

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val tokenKey = stringPreferencesKey(TOKEN_KEY)
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

    suspend fun token(): String? {
        return runBlocking {
            context.dataStore.data.map { preferences ->
                preferences[tokenKey]
            }.first()
        }
    }

    suspend fun saveToken(value: String) {
        Timber.d("set token: %s", value)
        context.dataStore.edit { preferences ->
            Timber.d("preferences kidsMode: %s", value)
            preferences[tokenKey] = value
        }
    }
}