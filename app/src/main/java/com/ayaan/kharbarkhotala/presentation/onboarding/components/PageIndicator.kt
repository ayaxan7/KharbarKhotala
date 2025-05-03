package com.ayaan.kharbarkhotala.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import com.ayaan.kharbarkhotala.presentation.Dimensions.IndicatorSize
import com.ayaan.kharbarkhotala.ui.theme.*

@Composable
fun PageIndicator(
    modifier: Modifier=Modifier,
    pageSize:Int,
    currentPage:Int,
    selectedColor:Color= Blue,
    unselectedColor:Color=BlueGray
) {
    Row(
        modifier=modifier,
        horizontalArrangement= Arrangement.SpaceBetween
    ) {
        repeat(pageSize){
            Box(modifier=Modifier
                .size(IndicatorSize)
                .clip(CircleShape)
                .background(color=if(it == currentPage) selectedColor else unselectedColor)
            )
        }
    }
}