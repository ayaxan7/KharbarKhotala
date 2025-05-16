package com.ayaan.kharbarkhotala.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import com.ayaan.kharbarkhotala.presentation.common.TrendingSection
import kotlinx.coroutines.launch
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.paging.LoadState
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.presentation.common.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    trendingArticles: LazyPagingItems<TrendingArticle>,
    state: HomeState,
    event: (HomeEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
    navigateToTrendingDetails: (TrendingArticle) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToBookmarks: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val tabTitles = listOf("Trending", "News")
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        TopBar(
            onSearchClick = navigateToSearch,
            onBookmarkClick = navigateToBookmarks,
        )

        Spacer(modifier = Modifier.height(SmallPadding))
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    color = Color.Blue
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title, color = Color.Blue) }
                )
            }
        }
        Spacer(modifier= Modifier.height(MediumPadding1))

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            when (page) {
                0 -> { // Trending
                    val trendingRefreshState = rememberPullToRefreshState()
                    PullToRefreshBox(
                        state= trendingRefreshState,
                        isRefreshing = trendingArticles.loadState.refresh is LoadState.Loading,
                        onRefresh = { trendingArticles.refresh() }
                    ) {
                        TrendingSection(
                            trendingArticles = trendingArticles,
                            state = state,
                            event = event,
                            navigateToTrendingDetails = navigateToTrendingDetails
                        )
                    }
                }

                1 -> { // Regular News
                    val newsRefreshState = rememberPullToRefreshState()
                    PullToRefreshBox(
                        state= newsRefreshState,
                        isRefreshing = articles.loadState.refresh is LoadState.Loading,
                        onRefresh = { articles.refresh() }
                    ) {
                        ArticlesList(
                            modifier = Modifier.padding(horizontal = MediumPadding1),
                            articles = articles,
                            onClick = navigateToDetails
                        )
                    }
                }
            }
        }
    }
}