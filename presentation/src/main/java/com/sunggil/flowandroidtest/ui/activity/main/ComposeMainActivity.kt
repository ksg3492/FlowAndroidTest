package com.sunggil.flowandroidtest.ui.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sunggil.flowandroidtest.navigation.NavigationGroup
import com.sunggil.flowandroidtest.navigation.NavigationItem
import com.sunggil.flowandroidtest.navigation.mainGraph

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navController : NavHostController) {
    Scaffold(
        bottomBar = { MainBottomNavigation(navController) }
    ) {
        MainNavigation(navController)
    }
}

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
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@Composable
fun MainNavigation(navController : NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationGroup.Main.group
    ) {
        mainGraph()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    MainScreen(navController)
}