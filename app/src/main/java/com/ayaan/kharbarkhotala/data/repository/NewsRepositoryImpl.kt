package com.ayaan.kharbarkhotala.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import com.ayaan.kharbarkhotala.data.remote.NewsApi
import com.ayaan.kharbarkhotala.data.remote.NewsPagingSource

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository{
     override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        val pager= Pager(
            config= PagingConfig(pageSize=10),
            pagingSourceFactory={
                NewsPagingSource(
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        )
        return pager.flow
    }
}