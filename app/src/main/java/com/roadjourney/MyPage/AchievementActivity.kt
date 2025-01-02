package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.R
import com.roadjourney.databinding.ActivityAchievementBinding

class AchievementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAchievementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickCategoryButton()
        clickBackButton()
    }

    private fun clickBackButton() {
        binding.ivAchievementBack.setOnClickListener {
            finish()
        }
    }


    private fun clickCategoryButton() {
        binding.tvAchievementCategory.setOnClickListener {
            binding.rvAchievementCategoryDetail.visibility = View.VISIBLE
            binding.tvAchievementCategory.visibility = View.GONE
        }
    }




}