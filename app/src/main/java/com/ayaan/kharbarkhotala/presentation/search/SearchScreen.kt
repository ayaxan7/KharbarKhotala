package com.ayaan.kharbarkhotala.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import com.ayaan.kharbarkhotala.presentation.common.SearchBar
import com.ayaan.kharbarkhotala.presentation.common.TopBar
import com.ayaan.kharbarkhotala.ui.theme.BarBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Article) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToBookmarks: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {
        // Top bar for consistent navigation
        TopBar(
            onSearchClick = navigateToSearch,
            onBookmarkClick = navigateToBookmarks
        )

        Spacer(modifier = Modifier.height(SmallPadding))

        // Tab row for visual consistency
        TabRow(
            selectedTabIndex = 0,
            containerColor = Color.White,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[0]),
                    color = BarBlue
                )
            }
        ) {
            Tab(
                selected = true,
                onClick = { /* Already selected */ },
                text = { Text(text = "Search", color = BarBlue) }
            )
        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        // Search input
        SearchBar(
            text = state.query,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchNews) },
            modifier = Modifier.padding(horizontal = MediumPadding1)
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        // Search results with pull-to-refresh
        state.articles?.let { flow ->
            val articles = flow.collectAsLazyPagingItems()
            val refreshState = rememberPullToRefreshState()

            PullToRefreshBox(
                state = refreshState,
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