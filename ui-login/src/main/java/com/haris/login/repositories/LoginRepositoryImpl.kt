package com.haris.login.repositories

import com.haris.base.AppPreferences
import javax.inject.Inject

internal class LoginRepositoryImpl @Inject constructor(
    private val api: LoginApi,
    private val appPreferences: AppPreferences
) : LoginRepository {

    override suspend fun login(email: String, password: String) {
        val result = api.login(LoginBody(email = email, password = password))

        val token = result.body()?.token ?: ""

        if (token.isNotEmpty()) {
            appPreferences.saveToken(token)
        } else {
            throw Exception()
        }
    }
}