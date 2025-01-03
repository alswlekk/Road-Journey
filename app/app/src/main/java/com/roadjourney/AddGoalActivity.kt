package com.roadjourney

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.databinding.ActivityAddGoalBinding

class AddGoalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
