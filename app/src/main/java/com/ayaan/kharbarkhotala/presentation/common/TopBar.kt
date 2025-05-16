package com.ayaan.kharbarkhotala.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayaan.kharbarkhotala.R
import com.ayaan.kharbarkhotala.presentation.Dimensions.IconSize
import com.ayaan.kharbarkhotala.presentation.Dimensions.MediumPadding1

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    onBookmarkClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MediumPadding1),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo on the left
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .width(150.dp)
                .height(30.dp)
        )

        // Action buttons on the right
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    modifier = Modifier.size(IconSize)
                )
            }

            IconButton(onClick = onBookmarkClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Bookmarks",
                    modifier = Modifier.size(IconSize)
                )
            }
        }
    }
}