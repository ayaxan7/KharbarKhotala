package com.ayaan.kharbarkhotala.presentation.details

import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle

sealed class DetailsEvent {
    data class InsertDeleteArticle(val article: Article) : DetailsEvent()
    data class InsertDeleteTrendingArticle(val trendingArticle: TrendingArticle) : DetailsEvent()
    object RemoveSideEffect : DetailsEvent()
}