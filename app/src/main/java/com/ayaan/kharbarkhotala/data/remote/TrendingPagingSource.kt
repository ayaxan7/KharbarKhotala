package com.ayaan.kharbarkhotala.data.remote

import androidx.paging.PagingState
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import androidx.paging.PagingSource

class TrendingPagingSource (
    private val newsApi: NewsApi,
    private val sources: String
):PagingSource<Int, TrendingArticle>() {
    private var totalNewsCount=0
    override fun getRefreshKey(state: PagingState<Int, TrendingArticle>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage=state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingArticle> {
        val page = params.key ?: 1
        return try {
            val newsResponse = newsApi.getTrendingNews()
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