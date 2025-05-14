package com.ayaan.kharbarkhotala.domain.model.trending
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Parcelize
@Entity
data class TrendingArticle(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: TrendingSource?,
    val title: String?,
    @PrimaryKey val url: String?,
    val urlToImage: String?
):Parcelable