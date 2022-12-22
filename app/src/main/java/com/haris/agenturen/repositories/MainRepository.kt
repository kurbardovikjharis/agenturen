package com.haris.agenturen.repositories

interface MainRepository {

    suspend fun getToken(): String
}