package com.sunggil.flowandroidtest.ui.compose.navigation

//Navigation Group
sealed class NavigationGroup(val group : String) {
    //Main associated group
    object Main : NavigationGroup("Main") {
        const val SCREEN_SEARCH = "screen_search"
        const val SCREEN_RECENT = "screen_recent"
        const val SCREEN_FAVORITE = "screen_favorite"
    }
}