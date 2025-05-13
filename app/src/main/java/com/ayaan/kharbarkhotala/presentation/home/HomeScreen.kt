package com.ayaan.kharbarkhotala.presentation.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.ExtraSmallPadding
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    state: HomeState,
    event: (HomeEvent) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val isRefreshing = remember { mutableStateOf(false) }
    rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
                .padding(horizontal = MediumPadding1)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        val scrollState = rememberScrollState(initial = state.scrollValue)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1),
            horizontalArrangement = Arrangement.spacedBy(MediumPadding1)
        ) {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    Card(
                        modifier = Modifier
                            .width(200.dp)
                            .height(250.dp) // Increased height
                            .clickable { navigateToDetails(article) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.white),
                            contentColor = colorResource(id = R.color.white)
                        ),
                        shape = RectangleShape
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(SmallPadding)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp) // Reserved space for the image
                                    .clip(RectangleShape),
                                model = article.urlToImage,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(ExtraSmallPadding))
                            Text(
                                text = article.title ?: "No Title",
                                style = MaterialTheme.typography.bodySmall,
                                color = colorResource(id = R.color.text_title),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f) // Ensures the text gets space
                            )
                        }
                    }
                }
            }
        }

        // Update the maxScrollingValue
        LaunchedEffect(key1 = scrollState.maxValue) {
            event(HomeEvent.UpdateMaxScrollingValue(scrollState.maxValue))
        }
        // Save the state of the scrolling position
        LaunchedEffect(key1 = scrollState.value) {
            event(HomeEvent.UpdateScrollValue(scrollState.value))
        }
        // Animate the scrolling
        LaunchedEffect(key1 = state.maxScrollingValue) {
            delay(500)
            if (state.maxScrollingValue > 0) {
                scrollState.animateScrollTo(
                    value = state.maxScrollingValue, animationSpec = infiniteRepeatable(
                        tween(
                            durationMillis = (state.maxScrollingValue - state.scrollValue) * 50_000 / state.maxScrollingValue,
                            easing = LinearEasing,
                            delayMillis = 1000
                        )
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        PullToRefreshBox(
            isRefreshing = isRefreshing.value, onRefresh = {
                coroutineScope.launch {
                    isRefreshing.value = true
                    articles.refresh()
                    delay(500)
                    isRefreshing.value = false
                }
            }, modifier = Modifier.fillMaxSize()
        ) {
            ArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                articles = articles,
                onClick = navigateToDetails
            )
        }
    }
}