package com.ayaan.kharbarkhotala.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ayaan.kharbarkhotala.BuildConfig
import com.ayaan.kharbarkhotala.domain.model.Article

class SearchNewsPagingSource (
    private val newsApi: NewsApi,
    private val query: String,
    private val sources: String
): PagingSource<Int, Article>(){
    private var totalNewsCount = 0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage=state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.searchNews(query=query, page = page, sources=sources)
            totalNewsCount += newsResponse.articles.size
            val articles = newsResponse.articles.distinctBy { it.title }
            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = if (totalNewsCount == newsResponse.totalResults) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(throwable = e)
        }
    }
}