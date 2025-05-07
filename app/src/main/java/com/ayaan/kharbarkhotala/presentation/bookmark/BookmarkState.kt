package com.ayaan.kharbarkhotala.presentation.bookmark

import com.ayaan.kharbarkhotala.domain.model.Article

data class BookmarkState(
     val articles: List<Article> = emptyList(),
)
