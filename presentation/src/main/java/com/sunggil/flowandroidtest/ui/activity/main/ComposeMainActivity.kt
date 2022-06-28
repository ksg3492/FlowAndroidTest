package com.sunggil.flowandroidtest.ui.activity.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sunggil.flowandroidtest.ui.compose.navigation.MainBottomNavigation
import com.sunggil.flowandroidtest.ui.compose.navigation.NavigationGroup
import com.sunggil.flowandroidtest.ui.compose.navigation.mainGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeMainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.e("SG2", "setContent()")
            val navController = rememberNavController()
            MainScreen(navController)
        }
    }
}

@Composable
fun MainScreen(navController : NavHostController) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        bottomBar = { MainBottomNavigation(navController) },
        scaffoldState = scaffoldState
    ) {
        NavHost(
            navController = navController,
            startDestination = NavigationGroup.Main.group
        ) {
            mainGraph(scaffoldState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    MainScreen(navController)
}