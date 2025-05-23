package com.ayaan.kharbarkhotala.domain.usecases.news

import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedArticles @Inject constructor(
    private val newsDao: NewsDao
) {
    operator fun invoke(): Flow<List<Article>>{
        return newsDao.getArticles()
    }
}