package com.spc.space.ui.auth

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.spc.space.R
import com.spc.space.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var authNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        authNavController =
            (supportFragmentManager.findFragmentById(R.id.auth_fragment_container) as NavHostFragment)
                .navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            findNavController(R.id.main_fragment_container)
        return navController.navigateUp()
    }

}