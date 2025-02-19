package com.roadjourney.Login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFindPw3Binding

class FindPw3Fragment : Fragment() {
    lateinit var binding: FragmentFindPw3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPw3Binding.inflate(layoutInflater)
        return binding.root
    }

    private fun clickFindBack() {
        binding.ivFindPwBack.setOnClickListener() {
            requireActivity().finish()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickFindBack()


        binding.btnCheckFilled.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        binding.etCheckNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isAllFilled = binding.etCheckNumber.text.isNotEmpty()

                if (isAllFilled) {
                    binding.btnCheckFilled.visibility = View.VISIBLE
                    binding.btnCheckUnfilled.visibility = View.GONE
                } else {
                    binding.btnCheckFilled.visibility = View.GONE
                    binding.btnCheckUnfilled.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnCheckFilled.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_find_pw, FindPw4Fragment())
                .addToBackStack(null)
                .commit()
        }
    }
}