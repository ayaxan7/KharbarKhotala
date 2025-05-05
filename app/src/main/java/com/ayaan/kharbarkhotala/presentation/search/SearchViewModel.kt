package com.ayaan.kharbarkhotala.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ayaan.kharbarkhotala.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private var _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(query = event.query)

                // Cancel any ongoing search job
                searchJob?.cancel()

                // Launch a new job with debounce
                searchJob = viewModelScope.launch {
                    delay(500) // debounce time (ms)
                    searchNews()
                }
            }

            is SearchEvent.SearchNews -> {
                searchJob?.cancel()
                searchNews()
            }
        }
    }

    private fun searchNews() {
        val articles = newsUseCases.searchNews(
            query = _state.value.query,
            sources = listOf(
                "bbc-news", "cnn", "the-verge", "the-hindu",
                "the-times-of-india", "the-washington-post",
                "the-wall-street-journal", "the-guardian-us",
                "the-new-york-times", "the-economist"
            )
        ).cachedIn(viewModelScope)

        _state.value = _state.value.copy(articles = articles)
    }
}



