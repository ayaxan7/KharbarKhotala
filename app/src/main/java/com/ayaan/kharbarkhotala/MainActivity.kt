package com.ayaan.kharbarkhotala

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.ayaan.kharbarkhotala.presentation.onboarding.OnBoardingScreen
import com.ayaan.kharbarkhotala.ui.theme.KharbarKhotalaTheme
import javax.inject.Inject
import com.ayaan.kharbarkhotala.domain.usecases.AppEntryUseCase
import com.ayaan.kharbarkhotala.presentation.navgraph.NavGraph
import com.ayaan.kharbarkhotala.presentation.onboarding.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()
    @Inject
    lateinit var appEntryUseCases: AppEntryUseCase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge()
        lifecycleScope.launch {
            appEntryUseCases.readAppEntry().collect{
                Log.d("TestingDI", "App Entry: $it")
            }
        }
        setContent {
            KharbarKhotalaTheme(darkTheme=false) {
                Box(modifier = Modifier
                    .background(color=MaterialTheme.colorScheme.background)
                ){
                    val startDestination = viewModel.stateDestination
                    NavGraph(startDestination=startDestination)
                }
            }
        }
    }
}