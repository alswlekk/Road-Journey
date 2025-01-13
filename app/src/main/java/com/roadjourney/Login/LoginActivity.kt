package com.roadjourney.Login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.CreateAccount.CreateAccountActivity
import com.roadjourney.MainActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLoginButton()
        clickCreateAccountButton()
    }

    private fun clickCreateAccountButton() {
        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
        }
    }

    private fun clickLoginButton() {
        binding.btnLogin.setOnClickListener {
            // 로그인 버튼 클릭 시 MainActivity로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}