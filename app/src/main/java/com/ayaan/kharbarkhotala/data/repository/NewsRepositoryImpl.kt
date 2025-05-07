package com.ayaan.kharbarkhotala.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import com.ayaan.kharbarkhotala.data.remote.NewsApi
import com.ayaan.kharbarkhotala.data.remote.NewsPagingSource
import com.ayaan.kharbarkhotala.data.remote.SearchNewsPagingSource
class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val newsDao: NewsDao
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

    override fun searchNews(
        query: String,
        sources: List<String>
    ): Flow<PagingData<Article>> {
        val pager= Pager(
            config= PagingConfig(pageSize=10),
            pagingSourceFactory={
                SearchNewsPagingSource(
                    query=query,
                    newsApi = newsApi,
                    sources = sources.joinToString(separator = ",")
                )
            }
        )
        return pager.flow
    }
    override suspend fun upsertArticle(article: Article) {
        newsDao.insert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun getArticles(): Flow<List<Article>> {
        return newsDao.getArticles()
    }
}