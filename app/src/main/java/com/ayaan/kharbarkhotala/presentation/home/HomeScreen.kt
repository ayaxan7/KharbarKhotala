package com.ayaan.kharbarkhotala.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding0
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.common.ArticlesList
import com.ayaan.kharbarkhotala.presentation.common.SearchBar
import com.ayaan.kharbarkhotala.presentation.navgraph.Route
import com.ayaan.kharbarkhotala.ui.theme.Black

@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>, navigate: (String) -> Unit
) {
    val titles by remember {
        derivedStateOf {
            if (articles.itemCount > 10) {
                articles.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9))
                    .joinToString(separator = "") { "🟥${it.title}" }
            } else {
                ""
            }
        }
    }
    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
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
        Spacer(modifier = Modifier.padding(1.dp))
        SearchBar(text = "", readOnly = true, onValueChange = {}, onClick = {
            navigate(Route.SearchScreen.route)
        }, onSearch = {})
        Spacer(modifier = Modifier.padding(MediumPadding0))
        Text(
            text = titles,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MediumPadding1)
                .basicMarquee(),
            fontSize = 14.sp,
            color = Black
        )
        Spacer(modifier = Modifier.padding(MediumPadding0))
        ArticlesList(
            modifier = Modifier.padding(horizontal = MediumPadding1),
            articles = articles,
            onClick = {
                navigate(Route.DetailsScreen.route)
            })
    }
}