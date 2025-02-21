package com.roadjourney.Friend

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.Archive.ArchiveActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityFriendProfileBinding

class FriendProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFriendProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvHomeName.text = intent.getStringExtra("tvHomeName")

        binding.ivFriendProfileBack.setOnClickListener {
            finish()
        }

        binding.ivBlankHeart.setOnClickListener {
            binding.ivBlankHeart.visibility = View.GONE
            binding.ivFilledHeart.visibility = View.VISIBLE
        }

        binding.ivFilledHeart.setOnClickListener {
            binding.ivFilledHeart.visibility = View.GONE
            binding.ivBlankHeart.visibility = View.VISIBLE
        }

        binding.ivHomeCicle.setOnClickListener() { // 사용자 설정에 따라 권한 부여
            val intent = Intent(this, ArchiveActivity::class.java)
            startActivity(intent)
        }

        binding.ivHomeClock.setOnClickListener() { // 사용자 설정에 따라 권한 부여
            val intent = Intent(this, ArchiveActivity::class.java)
            startActivity(intent)
        }
    }
}