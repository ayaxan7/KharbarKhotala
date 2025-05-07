package com.ayaan.kharbarkhotala.domain.repository

import kotlinx.coroutines.flow.Flow
import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.Article
interface NewsRepository {
    fun getNews(sources:List<String>): Flow<PagingData<Article>>
    fun searchNews(query: String, sources:List<String>): Flow<PagingData<Article>>
    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getArticles(): Flow<List<Article>>
}