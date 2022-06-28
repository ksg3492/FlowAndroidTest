package com.sunggil.flowandroidtest.ui.compose.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.sunggil.flowandroidtest.ui.compose.SearchScreen

fun NavGraphBuilder.mainGraph(scaffoldState : ScaffoldState) {
    navigation(
        startDestination = NavigationGroup.Main.SCREEN_SEARCH,
        route = NavigationGroup.Main.group
    ) {
        //Search
        composable(route = NavigationGroup.Main.SCREEN_SEARCH) {
            SearchScreen(scaffoldState)
        }
        //RECENT
        composable(route = NavigationGroup.Main.SCREEN_RECENT) {
            Text(text = "SCREEN_RECENT ${it.destination.route}")
        }
        //FAVORITE
        composable(route = NavigationGroup.Main.SCREEN_FAVORITE) {
            Text(text = "SCREEN_FAVORITE ${it.destination.route}")
        }
    }
}