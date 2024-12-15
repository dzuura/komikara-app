package com.dza.komikara.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dza.komikara.R
import com.dza.komikara.databinding.ActivityUserMainBinding


class UserMain : AppCompatActivity() {
    private lateinit var binding: ActivityUserMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.userProfileFragment -> {
                    navController.navigate(R.id.userProfile)
                    true
                }
                R.id.userHomeFragment -> {
                    navController.navigate(R.id.userHome)
                    true
                }
                R.id.userNewestFragment -> {
                    navController.navigate(R.id.userNewest)
                    true
                }
                R.id.userBookmarkFragment -> {
                    navController.navigate(R.id.userBookmark)
                    true
                }
                else -> false
            }
        }
    }
}