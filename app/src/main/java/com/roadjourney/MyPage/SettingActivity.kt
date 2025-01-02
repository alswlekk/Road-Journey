package com.roadjourney.MyPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivSettingBack.setOnClickListener {
            finish()
        }
    }
}
