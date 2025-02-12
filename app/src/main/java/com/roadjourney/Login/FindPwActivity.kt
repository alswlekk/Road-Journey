package com.roadjourney.Login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityFindPwBinding

class FindPwActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindPwBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_find_pw, FindPw1Fragment())
            .commit()
    }
}