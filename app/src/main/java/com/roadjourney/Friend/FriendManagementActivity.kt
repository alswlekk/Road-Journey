package com.roadjourney.Friend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.ActivityFriendManagementBinding

class FriendManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendManagementBinding
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFriendManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedToken = intent.getStringExtra("accessToken") ?: ""
        if (receivedToken.isNotEmpty()) {
            sharedViewModel.setUserData(receivedToken, -1)
        }


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_friend_management, FriendManagementFragment())
            .commit()
    }
}