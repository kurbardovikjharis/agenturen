package com.haris.login.interactors

import com.haris.data.AppCoroutineDispatchers
import com.haris.domain.Interactor
import com.haris.login.repositories.LoginRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LoginInteractor @Inject constructor(
    private val dispatchers: AppCoroutineDispatchers,
    private val repository: LoginRepository
) : Interactor<LoginInteractor.Params>() {

    data class Params(val email: String, val password: String)

    override suspend fun doWork(params: Params) {
        withContext(dispatchers.io) {
            repository.login(email = params.email, password = params.password)
        }
    }
}