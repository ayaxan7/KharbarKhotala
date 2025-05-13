package com.ayaan.kharbarkhotala.trendingEndpoint

data class TrendingNews(
    val articles: List<TrendingArticle>,
    val status: String,
    val totalResults: Int
)