package com.evg.api.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.api.domain.model.TestResponse
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult

class TestPageSourceRemote(
    private val apiRepository: ApiRepository,
): PagingSource<Int, ServerResult<TestResponse, NetworkError>>() {

    override fun getRefreshKey(state: PagingState<Int, ServerResult<TestResponse, NetworkError>>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ServerResult<TestResponse, NetworkError>> {
        val page = params.key ?: 1

        val response = apiRepository.getAllTestsByPage(
            page = page,
        )

        return when (response) {
            is ServerResult.Success -> {
                LoadResult.Page(
                    data = response.data.tests.map { ServerResult.Success(it) },
                    prevKey = response.data.prev,
                    nextKey = response.data.next
                )
            }
            is ServerResult.Error -> {
                LoadResult.Page(
                    data = listOf(ServerResult.Error(response.error)),
                    prevKey = null,
                    nextKey = null
                )
            }
        }
    }
}