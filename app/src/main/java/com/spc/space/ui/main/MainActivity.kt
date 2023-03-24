package com.spc.space.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.spc.space.R
import com.spc.space.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mainNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }


    private fun setUpNavigation() {
        mainNavController =
            (supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment).navController

        binding.bottomNavigationView.setupWithNavController(mainNavController)

        mainNavController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.favouritesFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.exploreFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.profileFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                R.id.bookingsFragment -> binding.bottomNavigationView.visibility = View.VISIBLE

                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.main_fragment_container)
        return navController.navigateUp()
    }


}

