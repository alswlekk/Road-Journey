package com.roadjourney.Shop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.databinding.ActivityShopBinding

class ShopActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivShopBack.setOnClickListener {
            finish()
        }
    }
}
