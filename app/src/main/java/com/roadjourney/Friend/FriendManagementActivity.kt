package com.roadjourney.Friend

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.R
import com.roadjourney.databinding.ActivityFriendManagementBinding

class FriendManagementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFriendManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_friend_management, FriendManagementFragment())
            .commit()
    }
}