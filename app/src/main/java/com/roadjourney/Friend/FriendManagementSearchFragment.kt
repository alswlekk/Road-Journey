package com.roadjourney.Friend

import FriendSearchAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.databinding.FragmentFriendManagementSearchBinding

class FriendManagementSearchFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementSearchBinding
    private lateinit var friendSearchAdapter: FriendSearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendManagementSearchBinding.inflate(inflater, container, false)

        friendSearchAdapter = FriendSearchAdapter(emptyList())
        binding.rvFriendSearchResult.adapter = friendSearchAdapter
        binding.rvFriendSearchResult.layoutManager = LinearLayoutManager(requireActivity())


//        updateRecyclerViewData(getFriendData())
        searchFriend()

        return binding.root
    }

    private fun searchFriend() {
        val allFriends = listOf(
            Friend(R.drawable.img_friend_profile1, "김철수", "kim123"),
            Friend(R.drawable.img_friend_profile1, "이영희", "lee1456"),
            Friend(R.drawable.img_friend_profile1, "박민수", "park1789"),
            Friend(R.drawable.img_friend_profile1, "정하늘", "skyblue1"),
            Friend(R.drawable.img_friend_profile1, "한지민", "hanji199")
        )

        binding.ivFriendSearch.setOnClickListener {
            val query = binding.etFriendManagementSearch.text.toString().trim()
            val filteredFriends = allFriends.filter {
                it.name.contains(query, ignoreCase = true) || it.id.contains(query, ignoreCase = true)
            }

            if (filteredFriends.isEmpty()) {
                binding.tvFriendNoResult.visibility = View.VISIBLE
            } else {
                binding.tvFriendNoResult.visibility = View.GONE
                friendSearchAdapter.updateList(filteredFriends)
            }
        }

        binding.rvFriendSearchResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFriendSearchResult.adapter = friendSearchAdapter
    }
}
