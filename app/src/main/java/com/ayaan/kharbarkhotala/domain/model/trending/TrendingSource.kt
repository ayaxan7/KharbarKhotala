package com.ayaan.kharbarkhotala.domain.model.trending

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TrendingSource(
    val id: String?,
    val name: String?
): Parcelable