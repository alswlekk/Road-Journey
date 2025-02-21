package com.roadjourney.Friend

import FriendSearchAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.AddGoal.FriendResponse
import com.roadjourney.AddGoal.FriendService
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.FriendManageService
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentFriendManagementSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendManagementSearchFragment : Fragment() {
    lateinit var binding: FragmentFriendManagementSearchBinding
    private lateinit var friendSearchAdapter: FriendSearchAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FriendManagementSearch", "sharedViewModel: $sharedViewModel")
        binding = FragmentFriendManagementSearchBinding.inflate(inflater, container, false)
        Log.d("FriendManagementSearch", "현재 저장된 토큰: ${sharedViewModel.accessToken.value}")
        friendSearchAdapter = FriendSearchAdapter(emptyList())
        binding.rvFriendSearchResult.adapter = friendSearchAdapter
        binding.rvFriendSearchResult.layoutManager = LinearLayoutManager(requireActivity())
        // LiveData를 감지해서 값이 설정될 때까지 기다리기
        sharedViewModel.accessToken.observe(viewLifecycleOwner) { tokenValue ->
            Log.d("FriendManagementSearch", "현재 저장된 토큰: $tokenValue")
            token = tokenValue
            if (!token.isNullOrEmpty()) {
                searchFriend() // 토큰이 설정된 후에 검색 기능 실행
            }
        }
        return binding.root
    }

    private fun searchFriend() {

        binding.ivFriendSearch.setOnClickListener {
            val query = binding.etFriendManagementSearch.text.toString().trim()
            val authToken = "Bearer $token"
            Log.d("FriendManagementSearch", "Search Query: $query")
            if (query.isNotEmpty()) {
                Log.d("FriendManagementSearch", "authToken: $authToken")

                val service = RetrofitObject.retrofit.create(FriendService::class.java)
                val call = service.searchFriends(authToken, query)
                call.enqueue(object : Callback<FriendResponse> {
                    override fun onResponse(
                        call: Call<FriendResponse>,
                        response: Response<FriendResponse>
                    ) {
                        if (response.isSuccessful) {
                            val friends = response.body()?.users ?: emptyList()
                            friends.forEach { friend ->
                                Log.d("사진 확인", "User: ${friend.nickname}, Image: ${friend.profileImage}")
                            }
                            if (friends.isEmpty()) {
                                binding.tvFriendNoResult.visibility = View.VISIBLE
                            } else {
                                binding.tvFriendNoResult.visibility = View.GONE
                                friendSearchAdapter.updateList(friends)
                            }
                        } else {
                            Log.e("FriendManagementSearch", "response is not successful")
                        }
                    }

                    override fun onFailure(call: Call<FriendResponse>, t: Throwable) {
                        Log.e("FriendManagementSearch", "네트워크 오류로 친구를 찾을 수 없습니다.")
                    }
                })
            }
        }

        binding.rvFriendSearchResult.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFriendSearchResult.adapter = friendSearchAdapter
    }
}
