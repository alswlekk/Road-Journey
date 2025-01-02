package com.roadjourney.MyPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivProfileBack.setOnClickListener {
            finish()
        }
    }
}
