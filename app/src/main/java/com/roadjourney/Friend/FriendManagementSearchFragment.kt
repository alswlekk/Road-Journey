package com.roadjourney.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendManagementSearchBinding

class FriendManagementSearchFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendManagementSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

}