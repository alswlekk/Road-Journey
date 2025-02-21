package com.roadjourney.Friend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.FriendManageService
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentFriendBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FriendFragment : Fragment() {
    private lateinit var binding: FragmentFriendBinding
    private var friendAdapter: FriendAdapter? = null
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var token: String? = null
    private var sortBy = "lastLogin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendBinding.inflate(inflater, container, false)
        Log.d("FriendFragment", "sharedViewModel: $sharedViewModel")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = sharedViewModel.accessToken.value
        Log.d("FriendFragment", "토큰: $token")


        // 초기 빈 어댑터 설정
        friendAdapter = FriendAdapter(FriendsData(emptyList()),  sharedViewModel.accessToken.value)
        binding.rvFriend.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFriend.adapter = friendAdapter

        fetchFriendData() // 친구 데이터 가져오기
        val typeCategories = listOf("최근 접속", "가나다 순", "달성 목표 수")
        setupSpinner(binding.spSort, typeCategories, 110)
        clickEditFriend()
    }

    private fun fetchFriendData() {
        val service = RetrofitObject.retrofit.create(FriendManageService::class.java)
        val authToken = "Bearer $token"
        val call = service.getFriendData(authToken, sortBy)

        call?.enqueue(object : Callback<FriendsData> {
            override fun onResponse(call: Call<FriendsData>, response: Response<FriendsData>) {
                Log.d("FriendFragment", "응답 코드: ${response.code()}") // 응답 코드 확인
                if (response.isSuccessful) {
                    val friendResponse = response.body()
                    if (friendResponse != null) {
                        showFriendInfo(friendResponse)
                    } else {
                        Log.d("FriendFragment", "친구가 존재하지 않음")
                    }
                } else {
                    Log.d("FriendFragment", "친구 목록 불러오기 실패: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<FriendsData>, t: Throwable) {
                Log.d("FriendFragment", "친구 목록 불러오기 실패(네트워크 오류): ${t.message}")
            }
        })
    }


    private fun showFriendInfo(friendResponse: FriendsData) {
        friendAdapter?.updateData(friendResponse.friends)
    }

    private fun updateRecyclerViewData(data: List<FriendData>) {
        friendAdapter?.updateData(data)
    }

    private fun clickEditFriend() {
        val intent = Intent(requireContext(), FriendManagementActivity::class.java)

        sharedViewModel.accessToken.value?.let { token ->
            intent.putExtra("accessToken", token)
        }

        binding.ivEditFriend.setOnClickListener { startActivity(intent) }
        binding.ivEditFriendIcon.setOnClickListener { startActivity(intent) }
    }

    private fun setupSpinner(spSort: Spinner, typeCategories: List<String>, left: Int) {
        val adapter = object : ArrayAdapter<String>(
            requireContext(), R.layout.spinner_item, typeCategories
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setBackgroundResource(R.drawable.spinner_background)
                view.gravity = Gravity.CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }

            override fun getDropDownView(
                position: Int, convertView: View?, parent: ViewGroup
            ): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                when (position) {
                    0 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_top)
                    count - 1 -> view.setBackgroundResource(R.drawable.spinner_dropdown_background_bottom)
                }
                view.gravity = Gravity.CENTER
                view.setTextColor(resources.getColor(R.color.white, null))
                view.textSize = 15f
                view.typeface = resources.getFont(R.font.samliphopangche)
                return view
            }
        }
        spSort.adapter = adapter
        spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                filterItems(typeCategories[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun filterItems(selectedCategory: String) {
        val adapter = friendAdapter ?: return // friendAdapter가 null이면 바로 return
        val filteredItems = when (selectedCategory) {
            "최근 접속" -> adapter.items.friends.sortedBy { parseTimeToHours(it.lastLoginTime) }
            "가나다 순" -> adapter.items.friends.sortedBy { it.nickname }
            "달성 목표 수" -> adapter.items.friends.sortedByDescending { it.achievementCount }
            else -> adapter.items.friends.sortedBy { parseTimeToHours(it.lastLoginTime) }
        }
        updateRecyclerViewData(filteredItems)
    }

    private fun parseTimeToHours(time: String): Int {
        return when {
            time.contains("시간 전") -> time.replace("시간 전", "").trim().toInt()
            time.contains("주일 전") -> time.replace("주일 전", "").trim().toInt() * 24 * 7
            time.contains("일 전") -> time.replace("일 전", "").trim().toInt() * 24
            else -> Int.MAX_VALUE
        }
    }
}
