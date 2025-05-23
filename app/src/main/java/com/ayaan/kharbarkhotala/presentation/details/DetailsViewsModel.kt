package com.ayaan.kharbarkhotala.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayaan.kharbarkhotala.domain.model.Article
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
    private val insertArticleUseCase: InsertArticle
) : ViewModel() {

    var sideEffect by mutableStateOf<UIComponent?>(null)
        private set

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