package com.sunggil.flowandroidtest.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sunggil.flowandroidtest.R

sealed class NavigationItem(
    @StringRes val title : Int,
    @DrawableRes val icon : Int,
    val route : String
) {
    object SEARCH : NavigationItem(R.string.title_search, R.drawable.ic_baseline_search_24, NavigationGroup.Main.SCREEN_SEARCH)
    object RECENT : NavigationItem(R.string.title_recent, R.drawable.ic_baseline_history_24, NavigationGroup.Main.SCREEN_RECENT)
    object FAVORITE : NavigationItem(R.string.title_favorite, R.drawable.ic_baseline_star_24, NavigationGroup.Main.SCREEN_FAVORITE)
}