package com.roadjourney.CreateAccount

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.Login.LoginActivity
import com.roadjourney.R
import com.roadjourney.databinding.ActivityCreateAccountBinding
import com.roadjourney.databinding.FragmentCreateAccount3Binding

class CreateAccount3Fragment : Fragment() {
    lateinit var binding: FragmentCreateAccount3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateAccount3Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnNextFilled.setOnClickListener {
            // 로그인창으로 이동
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
        }
    }
