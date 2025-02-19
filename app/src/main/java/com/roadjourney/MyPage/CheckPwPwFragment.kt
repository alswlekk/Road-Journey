package com.roadjourney.MyPage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentCheckPwPwBinding

class CheckPwPwFragment : Fragment() {
    lateinit var binding: FragmentCheckPwPwBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckPwPwBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

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
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_change_account, ChangePwFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.ivCheckPwBack.setOnClickListener {
            requireActivity().finish()
        }
    }
}