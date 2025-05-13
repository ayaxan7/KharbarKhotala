package com.ayaan.kharbarkhotala.presentation.details

import com.ayaan.kharbarkhotala.domain.model.Article

sealed class DetailsEvent {
    data class InsertDeleteArticle(val article: Article) : DetailsEvent()
    object RemoveSideEffect : DetailsEvent()
}