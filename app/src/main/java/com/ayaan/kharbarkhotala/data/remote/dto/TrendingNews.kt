package com.ayaan.kharbarkhotala.data.remote.dto

import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle

data class TrendingNews(
    val articles: List<TrendingArticle>,
    val status: String,
    val totalResults: Int
)