package com.haris.domain.interactors

import com.haris.domain.ResultInteractor
import com.haris.domain.repositories.MainRepository
import javax.inject.Inject

class GetToken @Inject constructor(
    private val mainRepository: MainRepository
) : ResultInteractor<Unit, String>() {

    override suspend fun doWork(params: Unit): String {
        return mainRepository.getToken()
    }
}