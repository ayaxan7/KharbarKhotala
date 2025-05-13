package com.ayaan.kharbarkhotala.trendingEndpoint

data class TrendingArticle(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: TrendingSource,
    val title: String,
    val url: String,
    val urlToImage: String
)