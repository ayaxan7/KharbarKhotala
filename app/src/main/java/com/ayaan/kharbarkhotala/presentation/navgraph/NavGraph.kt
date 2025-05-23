package com.ayaan.kharbarkhotala.presentation.navgraph

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayaan.kharbarkhotala.presentation.bookmark.BookmarkScreen
import com.ayaan.kharbarkhotala.presentation.bookmark.BookmarkViewModel
import com.ayaan.kharbarkhotala.presentation.home.HomeScreen
import com.ayaan.kharbarkhotala.presentation.home.HomeViewModel
import com.ayaan.kharbarkhotala.presentation.newsnavigation.NewsNavigator
import com.ayaan.kharbarkhotala.presentation.onboarding.OnBoardingScreen
import com.ayaan.kharbarkhotala.presentation.onboarding.OnBoardingViewModel
import com.ayaan.kharbarkhotala.presentation.search.SearchScreen
import com.ayaan.kharbarkhotala.presentation.search.SearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(onEvent = viewModel::onEvent)
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route){
                NewsNavigator()
            }
        }
    }
}