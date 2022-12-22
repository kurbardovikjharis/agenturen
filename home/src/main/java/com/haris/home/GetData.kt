package com.haris.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.haris.base.PagingInteractor
import com.haris.data.TodoEntity
import com.haris.home.repositories.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetData @Inject constructor(
    private val repository: HomeRepository
) : PagingInteractor<GetData.Params, TodoEntity>() {

    override fun createObservable(
        params: Params,
    ): Flow<PagingData<TodoEntity>> =
        Pager(config = params.pagingConfig) { repository.getData() }.flow

    data class Params(override val pagingConfig: PagingConfig) : Parameters<TodoEntity>
}