package com.ayaan.kharbarkhotala.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.presentation.Dimensions.ArticleSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.ExtraSmallPadding
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding

@Composable
fun TrendingArticleCard(
    modifier: Modifier = Modifier, article: TrendingArticle, onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick?.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white),
            contentColor = colorResource(id = R.color.white)
        ),
    ) {
        Row(
            modifier = Modifier.padding(SmallPadding)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(ArticleSize)
                    .clip(MaterialTheme.shapes.medium),
                model = article.urlToImage,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(horizontal = ExtraSmallPadding)
                    .height(ArticleSize)
            ) {
                Text(
                    text = article.title ?: "No Title",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(id = R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                // Add more fields if needed, e.g., source, publishedAt
            }
        }
    }
}