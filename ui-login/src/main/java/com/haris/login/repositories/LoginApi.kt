package com.haris.login.repositories

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface LoginApi {

    @POST("api/login")
    suspend fun login(@Body loginBody: LoginBody): Response<LoginDto>
}