package com.ayaan.kharbarkhotala.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.data.remote.NewsApi
import com.ayaan.kharbarkhotala.data.remote.NewsPagingSource
import com.ayaan.kharbarkhotala.data.remote.TrendingPagingSource
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.domain.repository.TrendingNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TrendingNewsRepositoryImpl @Inject constructor(
    private val newsApi: NewsApi,

): TrendingNewsRepository {
//    suspend fun fetchAndLogTrendingNews() {
//        val newsResponse = newsApi.getTrendingNews(page = 10)
//        Log.d("TrendingPagingSource", "Response: $newsResponse")
//    }

    override fun getTrendingNews(sources: List<String>): Flow<PagingData<TrendingArticle>> {
        val pager= Pager(
            config= PagingConfig(pageSize=10),
            pagingSourceFactory={
                TrendingPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        )
        Log.d("TrendingNewsRepository", "getTrendingNews: $pager")
        return pager.flow
    }
}