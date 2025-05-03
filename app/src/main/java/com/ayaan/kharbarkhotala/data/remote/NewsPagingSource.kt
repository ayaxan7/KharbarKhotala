package com.ayaan.kharbarkhotala.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.BuildConfig

class NewsPagingSource(
    private val newsApi: NewsApi,
    private val sources: String
):PagingSource<Int,Article>() {
    private var totalNewsCount=0
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage=state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?:anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: 1
        val apiKey = BuildConfig.NEWS_API_KEY
        return try {
            val newsResponse = newsApi.getNews(sources = sources, page = page, apiKey = apiKey)
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