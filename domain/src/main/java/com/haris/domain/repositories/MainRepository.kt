package com.haris.domain.repositories

interface MainRepository {

    suspend fun getToken(): String
}