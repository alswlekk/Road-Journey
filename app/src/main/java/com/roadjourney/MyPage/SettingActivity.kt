package com.roadjourney.MyPage

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private var isAlarm = false
    private var isProfileShare = false
    private var isGoalShare = false

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

        binding.ivSettingGoal.setOnClickListener {
            isGoalShare = !isGoalShare
            updateToggleImage(binding.ivSettingGoal, isGoalShare)
        }

        binding.ivSettingAlarmActivate.setOnClickListener {
            isAlarm = !isAlarm
            updateToggleImage(binding.ivSettingAlarmActivate, isAlarm)
        }

        binding.ivSettingProfile.setOnClickListener {
            isProfileShare = !isProfileShare
            updateToggleImage(binding.ivSettingProfile, isProfileShare)
        }

    }

    private fun updateToggleImage(imageView: ImageView, isEnabled: Boolean) {
        val newImageRes = if (isEnabled) {
            R.drawable.ic_toggle_on
        } else {
            R.drawable.ic_toggle_off
        }
        imageView.setImageResource(newImageRes)
    }
}
