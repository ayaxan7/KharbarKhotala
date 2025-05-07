package com.ayaan.kharbarkhotala.domain.usecases.news

import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.domain.model.Article
import kotlinx.coroutines.flow.Flow

class SelectArticles (
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>> {
        return  newsDao.getArticles()
    }
}