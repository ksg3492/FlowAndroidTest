package com.sunggil.flowandroidtest.ui.compose.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MainBottomNavigation(navController : NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val naviItems = listOf(NavigationItem.SEARCH, NavigationItem.RECENT, NavigationItem.FAVORITE)

    BottomNavigation(
        modifier = Modifier.fillMaxWidth()
    ) {
        naviItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                label = {
                    Text(text = stringResource(id = item.title))
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title)
                    )
                },
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.White,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                            // Pop up backstack to the first destination and save state. This makes going back
                            // to the start destination when pressing back in any other bottom tab.
                            popUpTo(findStartDestination(navController.graph).id) {
                                saveState = true
                            }
                        }
                    }

                }
            )
        }
    }
}

private val NavGraph.startDestination : NavDestination?
    get() = findNode(startDestinationId)

/**
 * Copied from similar function in NavigationUI.kt
 *
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-ui/src/main/java/androidx/navigation/ui/NavigationUI.kt
 */
private tailrec fun findStartDestination(graph : NavDestination) : NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}