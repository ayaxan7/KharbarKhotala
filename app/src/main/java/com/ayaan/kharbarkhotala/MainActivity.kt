package com.ayaan.kharbarkhotala

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.ayaan.kharbarkhotala.data.local.NewsDao
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.Source
import com.ayaan.kharbarkhotala.domain.usecases.appentry.AppEntryUseCase
import com.ayaan.kharbarkhotala.presentation.navgraph.NavGraph
import com.ayaan.kharbarkhotala.ui.theme.KharbarKhotalaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()

//    @Inject
//    lateinit var appEntryUseCases: AppEntryUseCase
//    @Inject lateinit var newsDao: NewsDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashCondition
            }
        }
        enableEdgeToEdge()
//        lifecycleScope.launch {
////            appEntryUseCases.readAppEntry().collect {
////                Log.d("TestingDI", "App Entry: $it")
////            }
//            newsDao.insert(
//                Article(
//                    author = "",
//                    content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
//                    description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
//                    publishedAt = "2 hours",
//                    source = Source(id = "", name = "BBC"),
//                    title = "Her train broke down. Her phone died. And then she met her Saver in a",
//                    url = "https://www.bbc.com/news/world-us-canada-67000000",
//                    urlToImage = "https://ichef.bbci.co.uk/live-experience/cps/624/cpsprodpb/11787/production/_124395517_bbcbreakingnewsgraphic.jpg"
//                )
//            )
//        }
        setContent {
            KharbarKhotalaTheme(darkTheme = false) {
                Box(
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
                ) {
                    val startDestination = viewModel.stateDestination
                    NavGraph(startDestination = startDestination)
                }
            }
        }
    }
}