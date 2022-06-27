package com.sunggil.flowandroidtest.ui.activity.main

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ActivityMainBinding
import com.sunggil.flowandroidtest.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class XmlMainActivity : BaseActivity<ActivityMainBinding>(TransitionMode.NONE) {

    override fun getLayout() : Int = R.layout.activity_main

    override fun setContentView() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_search, R.id.navigation_recent, R.id.navigation_favorite),
            null,
            ::onSupportNavigateUp
        )

        setupWithNavController(this.binding.toolbar, navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_search -> {
                }
                R.id.navigation_recent -> {
                }
                R.id.navigation_favorite -> {
                }
            }
        }
        this.binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() : Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp()
    }
}