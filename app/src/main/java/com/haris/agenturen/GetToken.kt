package com.haris.agenturen

import com.haris.agenturen.repositories.MainRepository
import com.haris.base.ResultInteractor
import javax.inject.Inject

class GetToken @Inject constructor(
    private val mainRepository: MainRepository
) : ResultInteractor<Unit, String>() {

    override suspend fun doWork(params: Unit): String {
        return mainRepository.getToken()
    }
}