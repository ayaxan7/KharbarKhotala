package com.ayaan.kharbarkhotala.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayaan.kharbarkhotala.data.remote.GeminiService
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.utils.UIComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ayaan.kharbarkhotala.domain.usecases.news.DeleteArticle
import com.ayaan.kharbarkhotala.domain.usecases.news.GetSavedArticle
import com.ayaan.kharbarkhotala.domain.usecases.news.InsertArticle

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getSavedArticleUseCase: GetSavedArticle,
    private val deleteArticleUseCase: DeleteArticle,
    private val insertArticleUseCase: InsertArticle,
    private val geminiService: GeminiService
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

    var articleSummary by mutableStateOf<String?>(null)
        private set

    var isLoadingSummary by mutableStateOf(false)
        private set

    private var currentArticle: Article? = null
    private var currentTrendingArticle: TrendingArticle? = null

    fun setCurrentArticle(article: Article?, trendingArticle: TrendingArticle?) {
        currentArticle = article
        currentTrendingArticle = trendingArticle
        articleSummary = null
        sideEffect = null
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.InsertDeleteArticle -> {
                viewModelScope.launch {
                    val article = getSavedArticleUseCase(url = event.article.url)
                    if (article == null){
                        insertArticle(article = event.article)
                    }else{
                        deleteArticle(article = event.article)
                    }
                }
            }
            is DetailsEvent.RemoveSideEffect ->{
                sideEffect = null
            }

            is DetailsEvent.InsertDeleteTrendingArticle -> TODO()

            is DetailsEvent.SummarizeArticle -> {
                summarizeCurrentArticle()
            }
        }
    }

    private fun summarizeCurrentArticle() {
        val content = currentArticle?.content ?: currentTrendingArticle?.content
        if (content.isNullOrBlank()) {
            sideEffect = UIComponent.Toast("No content available to summarize")
            return
        }

        viewModelScope.launch {
            isLoadingSummary = true
            try {
                val result = geminiService.summarizeArticle(content)
                result.fold(
                    onSuccess = { summary ->
                        articleSummary = summary
                        sideEffect = UIComponent.Toast("Summary generated successfully")
                    },
                    onFailure = { error ->
                        sideEffect = UIComponent.Toast("Failed to generate summary: ${error.message}")
                    }
                )
            } finally {
                isLoadingSummary = false
            }
        }
    }

    private suspend fun deleteArticle(article: Article) {
        deleteArticleUseCase(article = article)
        sideEffect = UIComponent.Toast("Article deleted")
    }

    private suspend fun insertArticle(article: Article) {
        insertArticleUseCase(article = article)
        sideEffect = UIComponent.Toast("Article Inserted")
    }

}