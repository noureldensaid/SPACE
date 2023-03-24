package com.spc.space.ui.main.explore.workspaceDetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navArgs
import com.spc.space.R
import com.spc.space.models.UnsplashPhoto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkSpaceDetailsActivity : AppCompatActivity() {
    private lateinit var secondaryNavController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_space_details)
        setUpNavigation()

    }


    private fun navigateToDetailsFragment() {
        val workSpaceData = intent.getParcelableExtra<UnsplashPhoto>("workspaceData")
        val secondFragment = workSpaceData?.let { WorkspaceDetailsFragment.newInstance(it) }

        if (secondFragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.book_fragment_container, secondFragment)
                .commit()
        }

    }


    private fun setUpNavigation() {
        secondaryNavController =
            (supportFragmentManager.findFragmentById(R.id.book_fragment_container) as NavHostFragment).navController
    }

    override fun onSupportNavigateUp(): Boolean {
        val secondaryNavController =
            findNavController(R.id.book_fragment_container)
        return secondaryNavController.navigateUp()
    }


}