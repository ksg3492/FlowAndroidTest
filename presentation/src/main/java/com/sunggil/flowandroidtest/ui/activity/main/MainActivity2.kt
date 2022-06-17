package com.sunggil.flowandroidtest.ui.activity.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sunggil.flowandroidtest.R
import com.sunggil.flowandroidtest.databinding.ActivityMain2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity2 : AppCompatActivity() {

    private lateinit var binding : ActivityMain2Binding

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_search, R.id.navigation_recent, R.id.navigation_favorite),
            null,
            ::onSupportNavigateUp
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_detail -> {
                    binding.navView.visibility = View.GONE
                }
                else -> {
                    binding.navView.visibility = View.VISIBLE
                }
            }
        }
        this.binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp() : Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        return navController.navigateUp()
    }
}