package com.roadjourney.Login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.roadjourney.CreateAccount.CreateAccountActivity
import com.roadjourney.MainActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityLoginBinding
import com.roadjourney.databinding.DialogFindIdPwBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickLoginButton()
        clickCreateAccountButton()
        clickFindAccountText()
    }

    private fun clickFindAccountText() {
        binding.tvFindIdPw.setOnClickListener() {
            // 아이디/비밀번호 찾기 클릭 시 dialog_find_id_pw.xml 띄우기
            addFindIdPwDialog()
        }
    }

    private fun addFindIdPwDialog() {
        val dialogFindIdPwBinding = DialogFindIdPwBinding.inflate(LayoutInflater.from(this))

        val findIdPwDialog = AlertDialog
            .Builder(this)
            .setView(dialogFindIdPwBinding.root)
            .create()

        dialogFindIdPwBinding.btnFindId.setOnClickListener {
            val intent = Intent(this, FindIdActivity::class.java)
            startActivity(intent)
        }

        dialogFindIdPwBinding.btnFindPw.setOnClickListener {
            val intent = Intent(this, FindPwActivity::class.java)
            startActivity(intent)
        }
        findIdPwDialog.show()

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