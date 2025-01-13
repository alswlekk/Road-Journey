package com.roadjourney.CreateAccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.R
import com.roadjourney.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    binding = ActivityCreateAccountBinding.inflate(layoutInflater)
    setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_create_account, CreateAccount1Fragment())
            .commit()
    }
}