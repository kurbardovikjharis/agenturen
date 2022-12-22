package com.haris.login

import com.haris.base.AppCoroutineDispatchers
import com.haris.base.ResultInteractor
import com.haris.login.repositories.LoginRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LoginInteractor @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val repository: LoginRepository
) : ResultInteractor<LoginInteractor.Params, String>() {

    override suspend fun doWork(params: Params): String =
        withContext(dispatchers.io) {
            repository.login(email = params.email, password = params.password)
        }

    data class Params(val email: String, val password: String)
}