package com.evg.database.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.database.domain.model.TestTypeDBO
import com.evg.database.domain.repository.DatabaseRepository

class TestPageSourceLocal(
    private val databaseRepository: DatabaseRepository,
): PagingSource<Int, TestTypeDBO>() {

    override fun getRefreshKey(state: PagingState<Int, TestTypeDBO>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TestTypeDBO> {
        //val page = params.key ?: 1

        try {
            val response = databaseRepository.getAllTests(
                /*pageSize = params.loadSize,
                offset = page * params.loadSize,*/
            )
            /*val prevKey = if (page == 0) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1*/

            return LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = null,
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}