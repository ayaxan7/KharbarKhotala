package com.ayaan.kharbarkhotala.data.remote.dto

import com.ayaan.kharbarkhotala.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)