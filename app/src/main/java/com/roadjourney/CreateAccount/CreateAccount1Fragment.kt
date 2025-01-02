package com.roadjourney.CreateAccount

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roadjourney.R
import com.roadjourney.databinding.FragmentCreateAccount1Binding

class CreateAccount1Fragment : Fragment() {
    lateinit var binding: FragmentCreateAccount1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccount1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 텍스트 변경 리스너를 설정
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 모든 EditText가 채워져 있는지 확인
                val isAllFilled = binding.etCreateAccountId.text.isNotEmpty() &&
                        binding.etCreateAccountPw.text.isNotEmpty() &&
                        binding.etCreateAccountPwCheck.text.isNotEmpty() &&
                        binding.etCreateAccountEmail.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnNextFilled.visibility = View.VISIBLE
                    binding.btnNextUnfilled.visibility = View.GONE
                } else {
                    binding.btnNextFilled.visibility = View.GONE
                    binding.btnNextUnfilled.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        // 각 EditText에 TextWatcher를 추가
        binding.etCreateAccountId.addTextChangedListener(textWatcher)
        binding.etCreateAccountPw.addTextChangedListener(textWatcher)
        binding.etCreateAccountPwCheck.addTextChangedListener(textWatcher)
        binding.etCreateAccountEmail.addTextChangedListener(textWatcher)

        binding.btnNextFilled.setOnClickListener {
            if (!binding.etCreateAccountPw.text.toString().equals(binding.etCreateAccountPwCheck.text.toString())) {
                binding.tvPwCheckError.visibility = View.VISIBLE
            } else {
                binding.tvPwCheckError.visibility = View.GONE
            }

            if (binding.etCreateAccountPw.text.length < 10) {
                binding.tvPwError.visibility = View.VISIBLE
            } else {
                binding.tvPwError.visibility = View.GONE
            }

            if (binding.etCreateAccountPw.text.toString()
                    .equals(binding.etCreateAccountPwCheck.text.toString())
                && binding.etCreateAccountPw.text.length >= 10 ) {
                // 다음 화면으로 이동
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcv_create_account, CreateAccount2Fragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
    }
}