package com.roadjourney

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.databinding.ActivityArchiveBinding

class ArchiveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArchiveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArchiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.ivArchiveBack.setOnClickListener {
            finish()
        }
    }
}
