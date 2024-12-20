package com.evg.api.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.GetTestsResponse

class TestPageSourceRemote(
    private val apiRepository: ApiRepository,
): PagingSource<Int, GetTestsResponse>() {
    //var filter = CharacterFilterDTO()

    override fun getRefreshKey(state: PagingState<Int, GetTestsResponse>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetTestsResponse> {
        val page = params.key ?: 1
        TODO()
        /*when (val response = apiRepository.getAllTestsByPage(page = page)) {
            *//*is Response.Success -> {
                val characters = response.data.results
                val prevKey = getPage(response.data.info.prev)
                val nextKey = getPage(response.data.info.next)

                return LoadResult.Page(
                    data = characters,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            }
            is Response.Failure -> {
                return LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }*//*
            is ServerResult.Success -> {
                TODO()
                val tests = response.data
            }
            is ServerResult.Error -> {
                TODO()
                val tests = response.error
            }
        }*/
    }

    private fun getPage(page: String?): Int? {
        if (page == null) return null

        val uri = Uri.parse(page)
        val pageQuery = uri.getQueryParameter("page")
        return pageQuery?.toInt()
    }
}