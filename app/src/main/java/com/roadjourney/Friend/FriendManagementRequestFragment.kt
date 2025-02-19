package com.roadjourney.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendManagementRequestBinding

class FriendManagementRequestFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementRequestBinding
    private lateinit var friendRequestAdapter: FriendRequestAdapter
    private var requests = ArrayList<FriendRequestData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendManagementRequestBinding.inflate(inflater, container, false)

        initAdapter()
        initRequestData()

        return binding.root
    }

    private fun initRequestData() {
        requests.addAll(
            arrayListOf(
                FriendRequestData(
                    friendId = 724,
                    userId = 1385,
                    accountId = "user03",
                    nickname = "기니디",
                    profileImage = R.drawable.img_friend_profile2.toString(),
                    statusMessage = "안녕하세요",
                    friendStatus = "PENDING",
                    createdAt = "2024.02.05 19:04"
                )
            )
        )

        if (requests.isEmpty()) {
            binding.tvFriendManagementRequestEmpty.visibility = View.VISIBLE
        } else {
            binding.tvFriendManagementRequestEmpty.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        friendRequestAdapter = FriendRequestAdapter(requests)
        binding.rvFriendManagementRequest.apply {
            adapter = friendRequestAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

    }
}