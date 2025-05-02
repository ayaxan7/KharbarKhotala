package com.ayaan.kharbarkhotala.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.presentation.Dimensions
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding2
import com.ayaan.kharbarkhotala.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier, page: Page
) {
    Column(modifier = modifier) {
        Image(
            painter=painterResource(id = page.image),
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentDescription = "Onboarding Image",
            contentScale= ContentScale.Crop
        )
        Spacer(modifier=modifier.height(Dimensions.MediumPadding1))
        Column(modifier=modifier.fillMaxHeight(.75f)) {
            Text(
                text = page.title,
                modifier = modifier.padding(horizontal = MediumPadding2),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = colorResource(R.color.display_small)
            )
            Text(
                text = page.description,
                modifier = modifier.padding(horizontal = MediumPadding2),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(R.color.text_medium)
            )
        }
    }
}