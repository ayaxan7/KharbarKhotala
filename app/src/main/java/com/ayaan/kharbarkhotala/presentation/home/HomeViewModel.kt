package com.ayaan.kharbarkhotala.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ayaan.kharbarkhotala.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    val news = newsUseCases.getNews(
        sources = listOf(
            "bbc-news",
            "cnn",
            "the-verge",
            "the-hindu",
            "the-times-of-india",
            "the-washington-post",
            "the-wall-street-journal",
            "the-guardian-us",
            "the-new-york-times",
            "the-economist"
        )
    ).cachedIn(viewModelScope)
}