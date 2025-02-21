package com.roadjourney.Login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.roadjourney.CreateAccount.CreateAccountActivity
import com.roadjourney.Login.Model.LoginData
import com.roadjourney.Login.Model.LoginResponseData
import com.roadjourney.MainActivity
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.LoginService
import com.roadjourney.Retrofit.SharedPreferencesHelper
import com.roadjourney.databinding.ActivityLoginBinding
import com.roadjourney.databinding.DialogFindIdPwBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            if (binding.etLoginId.text.toString().trim().isEmpty() || binding.etLoginPw.text.toString().trim().isEmpty()) {
                    binding.tvNoInputError.visibility = View.VISIBLE
                binding.tvLoginError.visibility = View.INVISIBLE
                    return@setOnClickListener
            } else {
                binding.tvNoInputError.visibility = View.INVISIBLE
            }

            val id = binding.etLoginId.text.toString()
            val pw = binding.etLoginPw.text.toString()

            val service = RetrofitObject.retrofit.create(LoginService::class.java)
            val call = service.postLoginData(LoginData(id, pw))
            call.enqueue(object : Callback<LoginResponseData> {
                override fun onResponse(
                    call: Call<LoginResponseData>,
                    response: Response<LoginResponseData>
                ) {
                    if (response.isSuccessful) {
                        val tokenData = response.body()?.data
                        if (tokenData != null) {
                            SharedPreferencesHelper.saveTokenData(this@LoginActivity, tokenData)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java).apply {
                                putExtra("accessToken", tokenData.accessToken)
                                putExtra("userId", tokenData.userId)
                            }
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        binding.tvLoginError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                    binding.tvLoginError.visibility = View.VISIBLE
                }

            })
        }
    }
}