package com.ayaan.kharbarkhotala.presentation.navgraph

sealed class Route(
    val route:String
) {
    data object OnBoardingScreen: Route(route="onBoardingScreen")
    data object HomeScreen: Route(route="homeScreen")
    data object SplashScreen: Route(route="splashScreen")
    data object BookmarkScreen:Route(route="bookmarkScreen")
    data object DetailsScreen:Route(route="detailsScreen")
    data object AppStartNavigation:Route(route="appStartNavigation")
    data object NewsNavigation:Route(route="newsNavigation")
    data object NewsNavigatorScreen:Route(route="newsNavigator")
}