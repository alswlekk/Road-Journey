package com.roadjourney.CreateAccount

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roadjourney.Login.LoginActivity
import com.roadjourney.Login.Model.SignupData
import com.roadjourney.Login.Model.SignupResponseData
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.LoginService
import com.roadjourney.databinding.DialogSuccessCreateAccountBinding
import com.roadjourney.databinding.FragmentCreateAccount1Binding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CreateAccount1Fragment : Fragment() {
    lateinit var binding: FragmentCreateAccount1Binding
    private var isIdChecked = false
    private var isPwChecked = false
    private var isPwCheckChecked = false
    private var isEmailChecked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateAccount1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SharedPreferences에서 프로필 이미지 URI 가져오기
        val sharedPref = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val profileImageUri = sharedPref.getString("profileImageUri", "") ?: ""

        // 텍스트 변경 리스너 설정
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        // EditText에 TextWatcher 추가
        binding.etCreateAccountId.addTextChangedListener(textWatcher)
        binding.etCreateAccountPw.addTextChangedListener(textWatcher)
        binding.etCreateAccountPwCheck.addTextChangedListener(textWatcher)
        binding.etCreateAccountEmail.addTextChangedListener(textWatcher)

        binding.btnNextFilled.setOnClickListener {
            val id = binding.etCreateAccountId.text.toString()
            val pw = binding.etCreateAccountPw.text.toString()
            val email = binding.etCreateAccountEmail.text.toString()
            val name = arguments?.getString("name").toString()
            val stateMessage = arguments?.getString("stateMessage").toString()

            val signupData = SignupData(id, pw, email, name, profileImageUri, stateMessage)
            val service = RetrofitObject.retrofit.create(LoginService::class.java)
            val call = service.postUser(signupData)

            call.enqueue(object : retrofit2.Callback<SignupResponseData> {
                override fun onResponse(call: Call<SignupResponseData>, response: Response<SignupResponseData>) {
                    if (response.isSuccessful) {
                        Log.d("SignupSuccess", "상태코드 ${response.code()}")
                        showSuccessDialog()
                    } else {
                        handleSignupError(response)
                    }
                }

                override fun onFailure(call: Call<SignupResponseData>, t: Throwable) {
                    Log.e("SignupError", "네트워크 오류 : ${t.message}")
                }
            })
        }
    }

    private fun validateInputs() {
        // 아이디 유효성 검사
        if (binding.etCreateAccountId.text.isNotEmpty()) {
            if (binding.etCreateAccountId.text.length < 8) {
                binding.tvIdShortError.visibility = View.VISIBLE
                binding.tvIdAlreadyError.visibility = View.GONE
                binding.tvIdAvailable.visibility = View.GONE
                isIdChecked = false
            } else {
                binding.tvIdShortError.visibility = View.GONE
                binding.tvIdAlreadyError.visibility = View.GONE
                binding.tvIdAvailable.visibility = View.INVISIBLE
                isIdChecked = true
            }
        } else {
            binding.tvIdShortError.visibility = View.GONE
            binding.tvIdAlreadyError.visibility = View.GONE
            binding.tvIdAvailable.visibility = View.INVISIBLE
            isIdChecked = false
        }

        // 비밀번호 유효성 검사
        val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{10,}$")
        val password = binding.etCreateAccountPw.text.toString()

        if (password.isNotEmpty()) {
            if (!passwordPattern.matches(password)) {
                binding.tvPwError.visibility = View.VISIBLE
                binding.tvPwAvailable.visibility = View.GONE
                isPwChecked = false
            } else {
                binding.tvPwError.visibility = View.GONE
                binding.tvPwAvailable.visibility = View.VISIBLE
                isPwChecked = true
            }
        } else {
            binding.tvPwError.visibility = View.GONE
            binding.tvPwAvailable.visibility = View.INVISIBLE
            isPwChecked = false
        }

        // 비밀번호 확인 검사
        val confirmPassword = binding.etCreateAccountPwCheck.text.toString()
        if (confirmPassword.isNotEmpty()) {
            if (password != confirmPassword) {
                binding.tvPwCheckError.visibility = View.VISIBLE
                isPwCheckChecked = false
            } else {
                binding.tvPwCheckError.visibility = View.INVISIBLE
                isPwCheckChecked = true
            }
        } else {
            binding.tvPwCheckError.visibility = View.INVISIBLE
            isPwCheckChecked = false
        }

        // 이메일 유효성 검사
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
        val email = binding.etCreateAccountEmail.text.toString()

        if (email.isNotEmpty()) {
            if (!emailPattern.matches(email)) {
                binding.tvEmailError.visibility = View.INVISIBLE
                isEmailChecked = false
            } else {
                binding.tvEmailError.visibility = View.INVISIBLE
                isEmailChecked = true
            }
        } else {
            binding.tvEmailError.visibility = View.INVISIBLE
            isEmailChecked = false
        }

        // 모든 입력이 올바르면 버튼 활성화
        if (isIdChecked && isPwChecked && isPwCheckChecked && isEmailChecked) {
            binding.btnNextFilled.visibility = View.VISIBLE
            binding.btnNextUnfilled.visibility = View.GONE
        } else {
            binding.btnNextFilled.visibility = View.GONE
            binding.btnNextUnfilled.visibility = View.VISIBLE
        }
    }

    private fun showSuccessDialog() {
        val successDialogBinding = DialogSuccessCreateAccountBinding.inflate(LayoutInflater.from(requireContext()))
        val successDialog = AlertDialog.Builder(requireContext())
            .setView(successDialogBinding.root)
            .create()

        successDialogBinding.btnFinish.setOnClickListener {
            successDialog.dismiss()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        successDialog.show()
    }

    private fun handleSignupError(response: Response<SignupResponseData>) {
        try {
            val errorJson = response.errorBody()?.string()
            val errorMessage = JSONObject(errorJson).getString("message")
            Log.e("SignupError", errorMessage)

            if (errorMessage.contains("이미 존재하는 계정 ID입니다.")) {
                binding.tvIdAlreadyError.visibility = View.VISIBLE
                binding.tvIdAvailable.visibility = View.GONE
            } else if (errorMessage.contains("이미 사용 중인 이메일입니다.")) {
                binding.tvEmailError.visibility = View.VISIBLE
                binding.tvIdAlreadyError.visibility = View.GONE
                binding.tvIdAvailable.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            Log.e("SignupError", "오류 응답 처리 중 예외 발생")
        }
    }
}
