package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentCheckPwBinding

class CheckPwFragment : Fragment() {
    lateinit var binding: FragmentCheckPwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckPwBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etPwCheck.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnNextFilled.visibility = View.VISIBLE
                    binding.btnNextUnfilled.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.etPwCheck.addTextChangedListener(textWatcher)
        binding.btnNextFilled.setOnClickListener {
            // 서버에서 account의 어떤 값 변경하려 한건지 받을 수 있는 지 확인 필요
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_change_account, ChangeEmailFragment()) // 임시로 이메일 창 뜨도록 해둠
                .addToBackStack(null)
                .commit()ㅁㅇ
        }
    }
}