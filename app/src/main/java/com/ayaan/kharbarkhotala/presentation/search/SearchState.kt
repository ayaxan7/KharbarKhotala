package com.ayaan.kharbarkhotala.presentation.search

import androidx.paging.PagingData
import com.ayaan.kharbarkhotala.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState (
    val query: String = "",
    val articles:Flow<PagingData<Article>>? = null,
)
