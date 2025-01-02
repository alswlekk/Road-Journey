package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.Login.LoginActivity
import com.roadjourney.R
import com.roadjourney.databinding.FragmentChangeNameBinding

class ChangeNameFragment : Fragment() {
    lateinit var binding: FragmentChangeNameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeNameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etNameChange.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnCompleteFilled.visibility = View.VISIBLE
                    binding.btnCompleteUnfilled.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        binding.etNameChange.addTextChangedListener(textWatcher)
        binding.btnCompleteFilled.setOnClickListener {
            // 다이얼로그 떠야함
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}