package com.roadjourney.Home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.roadjourney.AddGoal.AddGoalActivity
import com.roadjourney.Archive.ArchiveActivity
import com.roadjourney.R
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val baseUrl = "http://52.78.84.107:8080"

    val sharedViewModel: SharedViewModel by activityViewModels()

    companion object {
        private const val ADD_GOAL_REQUEST_CODE = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())

        binding.rgHome.check(R.id.rb_repeated)

        sharedViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
                if (!token.isNullOrEmpty() && userId != -1) {
                    homeAdapter = HomeAdapter(emptyList(), requireContext(), token, this)
                    binding.rvHome.adapter = homeAdapter
                    fetchMainInfo(token, userId.toLong())
                    fetchGoals("repeated", token, userId)
                }
            }
        }

        setupSegmentedControl()
        setupClickListeners()
        return binding.root
    }

    fun fetchMainInfo(token: String, userId: Long) {
        lifecycleScope.launch {
            try {
                val response = HomeApi.getInstance(baseUrl, token).getMainInfo(userId, "Bearer $token")

                if (response.isSuccessful) {
                    response.body()?.let { apiResponse ->
                        if (apiResponse.status == 200) {
                            val result = apiResponse.result
                            binding.tvHomeName.text = result.nickName

                            val userItem = result.selectedUserItemList.find {
                                it.itemName in listOf("강아지", "다람쥐", "펭귄", "여우")
                            }

                            userItem?.let {
                                val growthLevels = mapOf(1 to 200, 2 to 1500, 3 to 5000)
                                val convertedGrowthLevel = growthLevels[it.growthLevel ?: 1] ?: 200
                                binding.tvHomeProgress.text = "${it.growthPoint ?: 0}/$convertedGrowthLevel"
                            }

                            val backgroundItem = result.selectedUserItemList.find { it.category == "wallpaper" }
                            backgroundItem?.let {
                                val backgroundMap = mapOf(
                                    "밤하늘" to R.drawable.img_sky,
                                    "기본 방" to R.drawable.img_home_background,
                                    "바다" to R.drawable.img_ocean,
                                    "에펠탑" to R.drawable.img_tower,
                                    "숲" to R.drawable.img_forest,
                                    "벚꽃길" to R.drawable.img_flower,
                                    "설원" to R.drawable.img_snow
                                )

                                val backgroundRes = backgroundMap[it.itemName] ?: R.drawable.img_normal

                                Glide.with(requireContext())
                                    .load(backgroundRes)
                                    .centerCrop()
                                    .into(binding.ivHomeBackground)
                            }

                            val characterItem = result.selectedUserItemList.find { it.category == "character" }
                            characterItem?.let {
                                val backgroundMap = mapOf(
                                    "강아지" to R.drawable.ic_home_dog,
                                    "다람쥐" to R.drawable.ic_squirrel,
                                    "여우" to R.drawable.ic_fox,
                                    "펭귄" to R.drawable.ic_penguin
                                )

                                val characterRes = backgroundMap[it.itemName] ?: R.drawable.img_normal

                                Glide.with(requireContext())
                                    .load(characterRes)
                                    .centerCrop()
                                    .into(binding.icHomeDog)
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setupClickListeners() {
        binding.ivHomeAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddGoalActivity::class.java).apply {
                putExtra("accessToken", sharedViewModel.accessToken.value ?: "")
                putExtra("userId", sharedViewModel.userId.value ?: -1)
            }
            startActivityForResult(intent, ADD_GOAL_REQUEST_CODE)
        }

        binding.ivHomeCicle.setOnClickListener {
            val intent = Intent(requireContext(), ArchiveActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_GOAL_REQUEST_CODE && resultCode == RESULT_OK) {
            sharedViewModel.accessToken.value?.let { token ->
                sharedViewModel.userId.value?.let { userId ->
                    if (!token.isNullOrEmpty() && userId != -1) {
                        fetchMainInfo(token, userId.toLong())
                        fetchGoals("repeated", token, userId)
                    }
                }
            }
        }
    }

    private fun setupSegmentedControl() {
        binding.rgHome.setOnCheckedChangeListener { _, checkedId ->
            val category = when (checkedId) {
                R.id.rb_repeated -> "repeated"
                R.id.rb_short_term -> "short-term"
                R.id.rb_long_term -> "long-term"
                else -> "repeated"
            }

            sharedViewModel.accessToken.value?.let { token ->
                sharedViewModel.userId.value?.let { userId ->
                    if (!token.isNullOrEmpty() && userId != -1) {
                        fetchGoals(category, token, userId)
                    }
                }
            }
        }
    }

    fun fetchGoals(category: String, token: String, userId: Int) {
        lifecycleScope.launch {
            try {
                val response = HomeApi.getInstance(baseUrl, token).getGoals(userId, category, "Bearer $token")

                if (response.isSuccessful) {
                    response.body()?.let { apiResponse ->
                        if (apiResponse.status == 200) {
                            val goalList = apiResponse.result.goalInfoList
                            homeAdapter.updateItems(goalList)
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
