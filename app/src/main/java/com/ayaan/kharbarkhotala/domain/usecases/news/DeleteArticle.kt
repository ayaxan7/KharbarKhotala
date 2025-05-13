package com.ayaan.kharbarkhotala.domain.usecases.news

import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.domain.model.Article
import javax.inject.Inject

class DeleteArticle @Inject constructor(
    private val newsDao: NewsDao
) {
    suspend operator fun invoke(article: Article) {
        newsDao.delete(article)
    }
}