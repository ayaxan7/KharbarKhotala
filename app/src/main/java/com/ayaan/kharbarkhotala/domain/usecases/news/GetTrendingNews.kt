package com.ayaan.kharbarkhotala.domain.usecases.news

import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.domain.repository.NewsRepository
import com.ayaan.kharbarkhotala.domain.repository.TrendingNewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingNews@Inject constructor(
    private val trendingNewsRepository: TrendingNewsRepository
) {
    operator fun invoke(sources:List<String>):Flow<PagingData<TrendingArticle>> {
        return trendingNewsRepository.getTrendingNews(sources)
    }
}