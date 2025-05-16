package com.ayaan.kharbarkhotala.presentation.bookmark

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import com.ayaan.kharbarkhotala.presentation.common.TopBar
import com.ayaan.kharbarkhotala.ui.theme.BarBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    state: BookmarkState,
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
        TopBar(
            onSearchClick = navigateToSearch,
            onBookmarkClick = navigateToBookmarks,
        )

        Spacer(modifier = Modifier.height(SmallPadding))

        // Single tab for bookmarks - following the Home UI pattern
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
                text = { Text(text = "Bookmarks", color = BarBlue) }
            )
        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        // Pull-to-refresh for bookmarks
        val refreshState = rememberPullToRefreshState()

        PullToRefreshBox(
            state = refreshState,
            isRefreshing = false, // No actual refresh logic for bookmarks since they're local
            onRefresh = { /* Optional: could add a refresh mechanism */ }
        ) {
            // Content
            ArticlesList(
                modifier = Modifier.padding(horizontal = MediumPadding1),
                articles = state.articles,
                onClick = navigateToDetails
            )
        }
    }
}