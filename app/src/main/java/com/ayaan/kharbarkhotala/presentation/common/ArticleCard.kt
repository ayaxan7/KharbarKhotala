package com.ayaan.kharbarkhotala.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.presentation.Dimensions.ArticleSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.ExtraSmallPadding
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallIconSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding

@Composable
fun ArticleCard(
    modifier: Modifier = Modifier, article: Article, onClick: () -> Unit
) {
    val context = LocalContext.current

    Card(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .searchBarBorder(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = White
        )) {
        Row(modifier = modifier
            .clickable { onClick() }
            .padding(vertical = 1.dp)) {
            AsyncImage(
                modifier = Modifier
                    .padding(2.dp)
                    .size(width = ArticleSize, height = ArticleSize + 12.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest
                    .Builder(context)
                    .data(article.urlToImage)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(ExtraSmallPadding))
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(vertical = 4.dp) // small vertical padding
                    .height(90.dp), // matches image height
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.source.name,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.body)
                    )
                    Spacer(modifier = Modifier.width(SmallPadding))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(SmallIconSize),
                        tint = colorResource(id = R.color.body)
                    )
                    Spacer(modifier = Modifier.width(SmallPadding))
                    Text(
                        text = article.publishedAt,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.body)
                    )
                }
            }
        }
    }
}