package com.roadjourney.Login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.common.api.Api
import com.roadjourney.CreateAccount.CreateAccountActivity
import com.roadjourney.Login.Model.LoginData
import com.roadjourney.Login.Model.LoginResponseData
import com.roadjourney.MainActivity
import com.roadjourney.R
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
    private var canLogin = false

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
            if (binding.etLoginId.text.toString().trim().isEmpty() || binding.etLoginPw.text.toString().trim().isEmpty()) {
                    binding.tvNoInputError.visibility = View.VISIBLE
                    return@setOnClickListener // 아이디와 비밀번호 중 하나라도 입력하지 않았을 경우 코드 더 이상 실행하지 않음
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
                            // TokenData를 SharedPreferences에 저장
                            SharedPreferencesHelper.saveTokenData(this@LoginActivity, tokenData)
                        }

                        // MainActivity로 이동
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
//                        intent.putExtra("tokenData", tokenData)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("server response fail", "Server response failed with code: ${response.code()}")
                        binding.tvLoginError.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                    if (t is java.net.UnknownHostException) {
                        Log.e("NetworkError", "Unable to resolve host: ${t.message}")
                    } else {
                        Log.e("fetch failure", "Failed to fetch reservation items: ${t.message}")
                    }
                    binding.tvLoginError.visibility = View.VISIBLE
                }

            })
        }
    }
}