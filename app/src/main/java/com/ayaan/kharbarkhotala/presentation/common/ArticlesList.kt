package com.ayaan.kharbarkhotala.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding0
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: LazyPagingItems<Article>, onClick: (Article) -> Unit
) {
    val handlePagingResult = handlePagingResult(articles = articles)
    if (handlePagingResult) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(MediumPadding0),
            contentPadding = PaddingValues(all = SmallPadding)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { item ->
                    ArticleCard(
                        article = item, onClick = { onClick(item) })
                }
            }
        }
    }
}

@Composable
fun ArticlesList(
    modifier: Modifier = Modifier, articles: List<Article>, onClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = modifier .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding0),
        contentPadding = PaddingValues(all = SmallPadding)
    ) {
        items(count = articles.size) { index ->
            val article = articles[index]
            ArticleCard(
                article = article, onClick = { onClick(article) })

        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Article>
): Boolean {
    val loadingState = articles.loadState
    val error = when {
        loadingState.refresh is LoadState.Error -> loadingState.refresh as LoadState.Error
        loadingState.prepend is LoadState.Error -> loadingState.prepend as LoadState.Error
        loadingState.append is LoadState.Error -> loadingState.append as LoadState.Error
        else -> null
    }
    return when {
        loadingState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            // Handle error state
            // Show error message or UI
            false
        }

        else -> {
            true
        }
    }
}


@Composable
private fun ShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(MediumPadding1),
    ) {
        repeat(10) {
            ArticleCardShimmer(
                modifier = Modifier.padding(horizontal = MediumPadding1)
            )
        }
    }
}