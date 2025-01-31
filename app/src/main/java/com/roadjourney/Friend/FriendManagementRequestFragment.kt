package com.roadjourney.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendManagementRequestBinding

class FriendManagementRequestFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementRequestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendManagementRequestBinding.inflate(inflater, container, false)

        return binding.root
    }
}