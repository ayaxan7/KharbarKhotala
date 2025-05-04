package com.ayaan.kharbarkhotala.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.Source
import com.ayaan.kharbarkhotala.presentation.Dimensions.ArticleSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.ExtraSmallPadding
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallIconSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.SmallPadding
import com.ayaan.kharbarkhotala.ui.theme.KharbarKhotalaTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // For filled icons
import androidx.compose.material.icons.outlined.* // For outlined icons
import androidx.compose.material3.Icon

@Composable
fun ArticleCard(
    modifier:Modifier=Modifier,
    article: Article,
    onClick:()->Unit
){
    val context= LocalContext.current
    Row(
        modifier= modifier.clickable{onClick()}
    ) {
        AsyncImage(
            modifier = Modifier.size(ArticleSize).clip(MaterialTheme.shapes.medium),
            model= ImageRequest
                .Builder(context)
                .data(article.urlToImage)
                .build(),
            contentDescription =null
        )
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier=Modifier.padding(horizontal = ExtraSmallPadding)
                .height(ArticleSize)
        ) {
            Text(text=article.title,
                style= MaterialTheme.typography.bodyMedium,
                color= colorResource(R.color.text_title),
                maxLines=2,
                 overflow = TextOverflow.Ellipsis
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text=article.source.name,
                    style= MaterialTheme.typography.labelMedium.copy(fontWeight=FontWeight.Bold),
                    color= colorResource(R.color.body),
                )
                Spacer(modifier=Modifier.width(SmallPadding))
                Icon(
                    painter=painterResource(id=R.drawable.ic_time),
                    contentDescription =null,
                    modifier= Modifier.size(SmallIconSize),
                    tint= colorResource(id=R.color.body)
                )
                Spacer(modifier=Modifier.width(SmallPadding))
                Text(
                    text=article.publishedAt,
                    style= MaterialTheme.typography.labelMedium.copy(fontWeight=FontWeight.Bold),
                    color= colorResource(R.color.body),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    // Replace `NewsAppTheme` with your actual theme if it's not imported
    KharbarKhotalaTheme {
        ArticleCard(
            article = Article(
                author = "",
                title = "rtjutykgg teyrk dgdrhf",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = "32 hours",
                content = "",
                source = Source(
                    id = "",
                    name = "Kharbar Khotala"
                )
            ),
            onClick = {} // Correct way to pass click lambda
        )
    }
}
