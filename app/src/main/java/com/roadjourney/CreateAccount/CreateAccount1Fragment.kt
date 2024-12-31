package com.roadjourney.CreateAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roadjourney.databinding.ActivityCreateAccountBinding

class CreateAccount1Fragment : Fragment() {
    lateinit var binding: ActivityCreateAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        return binding.root
    }

}