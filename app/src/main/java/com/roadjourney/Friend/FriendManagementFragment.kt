package com.roadjourney.Friend

import FriendManagementSearchFragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendManagementBinding

class FriendManagementFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendManagementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        childFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_friend_management, FriendReceivedMessageFragment())
            .commit()

        binding.tvMessageUnfilled.setOnClickListener {
            binding.tvMessageUnfilled.visibility = View.GONE
            binding.tvMessageFilled.visibility = View.VISIBLE
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_friend_management, FriendReceivedMessageFragment())
                .addToBackStack(null)
                .commit()

            binding.tvFriendUnfilled.visibility = View.VISIBLE
            binding.tvFriendFilled.visibility = View.GONE

            binding.tvSearchUnfilled.visibility = View.VISIBLE
            binding.tvSearchFilled.visibility = View.GONE

        }

        binding.tvFriendUnfilled.setOnClickListener {
            binding.tvFriendUnfilled.visibility = View.GONE
            binding.tvFriendFilled.visibility = View.VISIBLE
            childFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_friend_management, FriendManagementRequestFragment())
                .addToBackStack(null)
                .commit()

            binding.tvMessageUnfilled.visibility = View.VISIBLE
            binding.tvMessageFilled.visibility = View.GONE

            binding.tvSearchUnfilled.visibility = View.VISIBLE
            binding.tvSearchFilled.visibility = View.GONE

        }

        binding.tvSearchUnfilled.setOnClickListener {
            binding.tvSearchUnfilled.visibility = View.GONE
            binding.tvSearchFilled.visibility = View.VISIBLE

            childFragmentManager
                .beginTransaction()
                .replace(R.id.fcv_friend_management, FriendManagementSearchFragment())
                .addToBackStack(null)
                .commit()

            binding.tvFriendUnfilled.visibility = View.VISIBLE
            binding.tvFriendFilled.visibility = View.GONE

            binding.tvMessageUnfilled.visibility = View.VISIBLE
            binding.tvMessageFilled.visibility = View.GONE

        }

        binding.ivFriendManagementBack.setOnClickListener {
            activity?.finish()
        }

        super.onViewCreated(view, savedInstanceState)
    }
}