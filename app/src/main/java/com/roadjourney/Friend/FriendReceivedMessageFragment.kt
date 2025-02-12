package com.roadjourney.Friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendReceivedMessageBinding

class FriendReceivedMessageFragment : Fragment() {
    lateinit var binding: FragmentFriendReceivedMessageBinding
    private lateinit var friendReceivedMessageAdapter: FriendReceivedMessageAdapter
    private var messages = ArrayList<FriendMessageData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendReceivedMessageBinding.inflate(inflater, container, false)

        initAdapter()
        initMessageData()
        return binding.root
    }

    private fun initMessageData() {
        messages.addAll(
            arrayListOf(
                FriendMessageData(
                    "거너더님이 공동 목표를 요청했어요!",
                    "2024.02.05 19:04",
                    "SHARED_GOAL", // 요청
                    100
                ),
                FriendMessageData(
                    "거너더님이 내 프로필에 좋아요를 눌렀어요!",
                    "2024.02.05 18:58",
                    "ANNOUNCEMENT", // 알림
                    200
                ),
            )
        )

        if (messages.isEmpty()) {
            binding.tvFriendNoReceivedMessage.visibility = View.VISIBLE
        } else {
            binding.tvFriendNoReceivedMessage.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        friendReceivedMessageAdapter = FriendReceivedMessageAdapter(messages)
        binding.rvFriendReceivedMessage.apply {
            adapter = friendReceivedMessageAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }
}