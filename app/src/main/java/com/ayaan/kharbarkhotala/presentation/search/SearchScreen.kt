package com.ayaan.kharbarkhotala.presentation.search
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayaan.kharbarkhotala.presentation.Dimensions.ExtraSmallPadding
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import com.ayaan.kharbarkhotala.presentation.common.SearchBar

@Composable
fun SearchScreen(
    state: SearchState,
    event:(SearchEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = MediumPadding1, start = ExtraSmallPadding, end = ExtraSmallPadding)
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(
            text = state.query,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchNews)
            }
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        state.articles?.let {
            val articles = it.collectAsLazyPagingItems()
            if(articles.itemCount == 0) {
             Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MediumPadding1),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
             ) {
                 Text(text="Nothing Found", color =Color.Black)
             }
            }
            ArticlesList(
                articles = articles,
                onClick = {
                    //TODO: Navigate to details screen
                }
            )
        }
    }
}