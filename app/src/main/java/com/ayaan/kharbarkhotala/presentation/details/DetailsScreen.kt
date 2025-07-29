package com.ayaan.kharbarkhotala.presentation.details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.presentation.Dimensions.ArticleImageHeight
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding0
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1
import com.ayaan.kharbarkhotala.presentation.details.components.DetailsTopBar
import com.ayaan.kharbarkhotala.utils.UIComponent
import androidx.core.net.toUri
import com.ayaan.kharbarkhotala.ui.theme.BarBlue

@Composable
fun DetailsScreen(
    article: Article?,
    trendingArticle: TrendingArticle?,
    event: (DetailsEvent) -> Unit,
    sideEffect: UIComponent?,
    navigateUp: () -> Unit,
    viewModel: DetailsViewModel
) {
    val context = LocalContext.current
    // Set current article in viewModel when screen is composed
    LaunchedEffect(article, trendingArticle) {
        viewModel.setCurrentArticle(article, trendingArticle)
    }

    LaunchedEffect(key1 = sideEffect) {
        sideEffect?.let {
            when (sideEffect) {
                is UIComponent.Toast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                    event(DetailsEvent.RemoveSideEffect)
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Color.White)
    ) {
        DetailsTopBar(
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also {
                    it.data = (article?.url ?: trendingArticle?.url)?.toUri()
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also {
                    it.putExtra(Intent.EXTRA_TEXT, article?.url ?: trendingArticle?.url)
                    it.type = "text/plain"
                    if (it.resolveActivity(context.packageManager) != null) {
                        context.startActivity(it)
                    }
                }
            },
            onBookMarkClick = {
                article?.let {
                    event(DetailsEvent.InsertDeleteArticle(it))
                }
                trendingArticle?.let {
                    event(DetailsEvent.InsertDeleteTrendingArticle(it))
                }
            },
            onBackClick = navigateUp
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth().background(Color.White),
            contentPadding = PaddingValues(
                start = MediumPadding1, end = MediumPadding1, top = MediumPadding1
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(article?.urlToImage ?: trendingArticle?.urlToImage)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))
                Text(
                    text = article?.title ?: trendingArticle?.title ?: "No Title",
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(id = R.color.text_title)
                )
                Column {
                    Text(
                        text = article?.content?.split("[+")?.firstOrNull()?.let {
                            if (it.length > 3000) it.take(3000) + "..." else it
                        } ?: trendingArticle?.content?.split("[+")?.firstOrNull()?.let {
                            if (it.length > 3000) it.take(3000) + "..." else it
                        } ?: "No content available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.body)
                    )

                    Spacer(modifier = Modifier.height(MediumPadding0))

                    Text(
                        text = "Click here to read more",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = BarBlue, fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                Intent(Intent.ACTION_VIEW).also {
                                    it.data = (article?.url ?: trendingArticle?.url)?.toUri()
                                    if (it.resolveActivity(context.packageManager) != null) {
                                        context.startActivity(it)
                                    }
                                }
                            }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Summarize Button
                    Button(
                        onClick = { event(DetailsEvent.SummarizeArticle) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BarBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !viewModel.isLoadingSummary
                    ) {
                        if (viewModel.isLoadingSummary) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text("Generating Summary...")
                            }
                        } else {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text("Summarize with AI")
                            }
                        }
                    }

                    // Summary Display
                    viewModel.articleSummary?.let { summary ->
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF8F9FA)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = BarBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.padding(4.dp))
                                    Text(
                                        text = "AI Summary",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = BarBlue
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = summary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colorResource(id = R.color.body),
                                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
