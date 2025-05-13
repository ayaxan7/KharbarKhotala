package com.ayaan.kharbarkhotala.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ayaan.kharbarkhotala.domain.usecases.news.SearchNews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNewsUseCase: SearchNews
) : ViewModel() {

    private var _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(query = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500) // 500ms debounce delay
                    searchNews()
                }
            }

            is SearchEvent.SearchNews -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    searchNews() // Immediate search when button is clicked
                }
            }
        }
    }

    private fun searchNews() {
        if (_state.value.query.isNotBlank()) {
            val articles = searchNewsUseCase(
                query = _state.value.query,
                sources = listOf("bbc-news", "abc-news", "al-jazeera-english")
            ).cachedIn(viewModelScope)
            _state.value = _state.value.copy(articles = articles)
        }
    }
}

