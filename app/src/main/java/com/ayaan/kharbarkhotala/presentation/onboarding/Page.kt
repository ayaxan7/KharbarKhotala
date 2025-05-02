package com.ayaan.kharbarkhotala.presentation.onboarding

import androidx.annotation.DrawableRes
import com.ayaan.kharbarkhotala.R
data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)
val pages=listOf(
    Page(
        title = "Welcome to Kharbar Khotala",
        description = "Your one-stop solution for all kharbar.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Saari khabrein yahi milengi",
        description = "Aaj ki kharbar kal ki khabar.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Aaj ki taaza khabar",
        description = "Chai ke saath khabar ka ghunth",
        image = R.drawable.onboarding3
    )
)