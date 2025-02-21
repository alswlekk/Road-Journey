package com.roadjourney.MyPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.roadjourney.MyPage.Model.MyPageUserData
import com.roadjourney.R
import com.roadjourney.Retrofit.RetrofitObject
import com.roadjourney.Retrofit.Service.MyPageService
import com.roadjourney.Retrofit.SharedPreferencesHelper
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentMyPageBinding
import retrofit2.Call
import retrofit2.Response

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        setupClickListeners()
        getMyPageData()
        loadProfileImage()  // 프로필 이미지 로드

        return binding.root
    }

    private fun getMyPageData() {
        val tokenData = SharedPreferencesHelper.loadTokenData(requireContext())

        if (tokenData != null) {
            val service = RetrofitObject.retrofit.create(MyPageService::class.java)
            val call = service.getMyInformation("Bearer ${tokenData.accessToken}")

            call.enqueue(
                object : retrofit2.Callback<MyPageUserData> {
                    override fun onResponse(
                        call: Call<MyPageUserData>,
                        response: Response<MyPageUserData>
                    ) {
                        if (response.isSuccessful) {
                            val userResponse = response.body()

                            if (userResponse != null) {
                                binding.tvMyPageName.text = userResponse.data.nickname
                                Log.d("MyPageFragment", "유저이름 : ${userResponse.data.nickname}")

                                // 서버에서 받은 프로필 이미지를 Glide로 로드
                                val profileImageUrl = userResponse.data.profileImage
                                if (profileImageUrl != null && profileImageUrl.isNotEmpty()) {
                                    Glide.with(requireContext())
                                        .load(profileImageUrl)
                                        .placeholder(R.drawable.ic_create_account_profile)
                                        .error(R.drawable.ic_create_account_profile)
                                        .circleCrop()
                                        .into(binding.ivMyPage)
                                    Log.d("MyPageFragment", "서버에서 받은 프로필 이미지 : $profileImageUrl")
                                }
                            } else {
                                Log.d("MyPageFragment", "response body is null")
                            }
                        } else {
                            Log.d(
                                "MyPageFragment",
                                "서버 응답 실패<데이터 불러오는 중 오류>, 오류코드 : ${response.code()} 메시지 : ${response.message()}"
                            )
                        }
                    }

                    override fun onFailure(call: Call<MyPageUserData>, t: Throwable) {
                        Log.d("MyPageFragment", "네트워크 통신 실패<데이터 불러오는 중 오류>")
                    }
                }
            )
        } else {
            Log.d("MyPageFragment", "토큰 데이터가 없습니다. 로그인 상태를 확인해주세요.")
        }
    }

    private fun loadProfileImage() {
        val sharedPref = requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)
        val profileImageUri = sharedPref.getString("profileImageUri", null)

        if (profileImageUri != null) {
            Glide.with(requireContext())
                .load(Uri.parse(profileImageUri))  // 저장된 URI로 이미지 로드
                .placeholder(R.drawable.ic_create_account_profile)  // 로딩 중 기본 이미지
                .error(R.drawable.ic_create_account_profile)  // 에러 시 기본 이미지
                .circleCrop()
                .into(binding.ivMyPage)

            Log.d("MyPageFragment", "로컬 저장된 프로필 이미지 불러오기 성공: $profileImageUri")
        } else {
            Log.d("MyPageFragment", "저장된 프로필 이미지 없음")
        }
    }

    private fun setupClickListeners() {
        binding.tvMyPageBtn.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.clMyPageItem.setOnClickListener {
            val intent = Intent(requireContext(), ItemActivity::class.java)
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }

        binding.clMyPageSetting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }

        binding.clMyPageAccount.setOnClickListener {
            val intent = Intent(requireContext(), AccountManagementActivity::class.java)
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }

        binding.clMyPageChallenge.setOnClickListener {
            val intent = Intent(requireContext(), AchievementActivity::class.java)
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }

        binding.ivMyPageAlarm.setOnClickListener {
            val intent = Intent(requireContext(), AlarmActivity::class.java)
            sharedViewModel.accessToken.value?.let { token ->
                intent.putExtra("accessToken", token)
            }
            startActivity(intent)
        }
    }
}
