package com.ayaan.kharbarkhotala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ayaan.kharbarkhotala.presentation.onboarding.OnBoardingScreen
import com.ayaan.kharbarkhotala.ui.theme.KharbarKhotalaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KharbarKhotalaTheme(darkTheme=false) {
                Box(modifier = Modifier
                    .background(color=MaterialTheme.colorScheme.background)
                ){
                    OnBoardingScreen()
                }
            }
        }
    }
}