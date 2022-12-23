package com.haris.login.repositories

internal interface LoginRepository {

    suspend fun login(email: String, password: String)
}