package com.ayaan.kharbarkhotala.domain.usecases.news

import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNews(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(query: String, sources: List<String>):Flow<PagingData<Article>> {
        return newsRepository.searchNews(query, sources)
    }
}