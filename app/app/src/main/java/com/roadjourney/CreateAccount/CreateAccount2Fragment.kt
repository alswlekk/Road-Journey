package com.roadjourney.CreateAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.ActivityCreateAccountBinding
import com.roadjourney.databinding.FragmentShopBinding

class CreateAccount2Fragment : Fragment() {

    lateinit var binding: ActivityCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        return binding.root
    }
}