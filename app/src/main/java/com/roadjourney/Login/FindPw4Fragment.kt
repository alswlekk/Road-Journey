package com.roadjourney.Login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFindPw4Binding

class FindPw4Fragment : Fragment() {

    lateinit var binding: FragmentFindPw4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindPw4Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCheckFilled.setOnClickListener() {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }


}