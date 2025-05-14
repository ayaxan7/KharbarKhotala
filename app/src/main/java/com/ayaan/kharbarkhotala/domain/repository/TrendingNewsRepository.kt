package com.ayaan.kharbarkhotala.domain.repository

import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import kotlinx.coroutines.flow.Flow

interface TrendingNewsRepository {
    fun getTrendingNews(sources: List<String>): Flow<PagingData<TrendingArticle>>
}