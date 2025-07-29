package com.ayaan.kharbarkhotala.presentation.newsnavigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.ayaan.kharbarkhotala.domain.model.Article
import com.ayaan.kharbarkhotala.domain.model.trending.TrendingArticle
import com.ayaan.kharbarkhotala.presentation.bookmark.BookmarkScreen
import com.ayaan.kharbarkhotala.presentation.bookmark.BookmarkViewModel
import com.ayaan.kharbarkhotala.presentation.details.DetailsScreen
import com.ayaan.kharbarkhotala.presentation.details.DetailsViewModel
import com.ayaan.kharbarkhotala.presentation.home.HomeScreen
import com.ayaan.kharbarkhotala.presentation.home.HomeViewModel
import com.ayaan.kharbarkhotala.presentation.navgraph.Route
import com.ayaan.kharbarkhotala.presentation.search.SearchScreen
import com.ayaan.kharbarkhotala.presentation.search.SearchViewModel

@Composable
fun NewsNavigator() {
    val navController = rememberNavController()
    val detailsViewModel: DetailsViewModel=hiltViewModel()

    // Scaffold without bottom bar
    Scaffold(modifier = Modifier.fillMaxSize()) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()
                val trendingArticles = viewModel.trendingNews.collectAsLazyPagingItems()
                HomeScreen(
                    articles = articles,
                    trendingArticles = trendingArticles,
                    state = viewModel.state.value,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    },
                    navigateToTrendingDetails = { trendingArticle ->
                        navigateToDetails(
                            navController = navController,
                            article = trendingArticle
                        )
                    },
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToBookmarks = {
                        navigateToTab(
                            navController = navController,
                            route = Route.BookmarkScreen.route
                        )
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                SearchScreen(
                    state = state,
                    event = viewModel::onEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    },
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToBookmarks = {
                        navigateToTab(
                            navController = navController,
                            route = Route.BookmarkScreen.route
                        )
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                val trendingArticle = navController.previousBackStackEntry?.savedStateHandle?.get<TrendingArticle?>("trendingArticle")

                when {
                    article != null -> {
                        DetailsScreen(
                            article = article,
                            event = viewModel::onEvent,
                            navigateUp = {
                                navigateUp(
                                    navController=navController,
                                    article = article
                                )
                            },
                            sideEffect = viewModel.sideEffect,
                            trendingArticle = null,
                            viewModel = viewModel
                        )
                    }
                    trendingArticle != null -> {
                        DetailsScreen(
                            trendingArticle = trendingArticle,
                            event = viewModel::onEvent,
                            navigateUp = {
                                navigateUp(
                                    navController=navController,
                                    trendingArticle = trendingArticle
                                )
                            },
                            sideEffect = viewModel.sideEffect,
                            article = null,
                            viewModel = viewModel
                        )
                    }
                }
            }
            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                OnBackClickStateSaver(navController = navController)
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    },
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToBookmarks = {
                        navigateToTab(
                            navController = navController,
                            route = Route.BookmarkScreen.route
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController,
            route = Route.HomeScreen.route
        )
    }
}
private fun navigateUp(navController: NavController,article: Article?=null,trendingArticle: TrendingArticle?=null) {
    if(article!=null) {
        navController.previousBackStackEntry?.savedStateHandle?.set<Article?>("article", null)
    }else if(trendingArticle!=null) {
        navController.previousBackStackEntry?.savedStateHandle?.set<TrendingArticle?>("trendingArticle", null)
    }
    navController.navigateUp()}
private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}

private fun navigateToDetails(
    navController: NavController,
    article: TrendingArticle,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("trendingArticle", article)
    navController.navigate(
        route = Route.DetailsScreen.route
    )
}