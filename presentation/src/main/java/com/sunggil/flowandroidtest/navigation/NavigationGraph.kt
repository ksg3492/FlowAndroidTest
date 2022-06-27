package com.sunggil.flowandroidtest.navigation

import androidx.compose.material.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation

fun NavGraphBuilder.mainGraph() {
    navigation(
        startDestination = NavigationGroup.Main.SCREEN_SEARCH,
        route = NavigationGroup.Main.group
    ) {
        //Search
        composable(route = NavigationGroup.Main.SCREEN_SEARCH) {
            Text(text = "SCREEN_SEARCH")
        }
        //RECENT
        composable(route = NavigationGroup.Main.SCREEN_RECENT) {
            Text(text = "SCREEN_RECENT")
        }
        //FAVORITE
        composable(route = NavigationGroup.Main.SCREEN_FAVORITE) {
            Text(text = "SCREEN_FAVORITE")
        }
    }
}