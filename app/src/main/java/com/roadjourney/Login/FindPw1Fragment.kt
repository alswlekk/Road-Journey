package com.roadjourney.Login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFindPw1Binding

class FindPw1Fragment : Fragment() {
    lateinit var binding: FragmentFindPw1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPw1Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickFindBack()

        binding.etCheckId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) {
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
                .replace(R.id.fcv_find_pw, FindPw2Fragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun clickFindBack() {
        binding.ivFindPwBack.setOnClickListener() {
            requireActivity().finish()
        }
    }

}