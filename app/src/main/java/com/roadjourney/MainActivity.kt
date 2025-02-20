package com.roadjourney

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.roadjourney.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val accessToken = intent.getStringExtra("accessToken") ?: ""
        val userId = intent.getIntExtra("userId", -1)
        sharedViewModel.setUserData(accessToken, userId)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController()
        setupBottomNavigation(navController)
        handleBottomNavigation(navController)
    }

    private fun findNavController(): NavController {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fcvHome.id) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupBottomNavigation(navController: NavController) {
        binding.bnvHome.setupWithNavController(navController)
    }

    private fun handleBottomNavigation(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvHome.visibility = if (showBottomNav(destination.id)) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun showBottomNav(destinationId: Int): Boolean {
        return when (destinationId) {
            R.id.fragment_home,
            R.id.fragment_friend,
            R.id.fragment_shop,
            R.id.fragment_my_page -> true
            else -> false
        }
    }
}
