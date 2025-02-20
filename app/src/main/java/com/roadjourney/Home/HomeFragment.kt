package com.roadjourney.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeAdapter = HomeAdapter(emptyList(), requireContext(), "")
        binding.rvHome.adapter = homeAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())

        binding.rgHome.check(R.id.rb_repeated)

        sharedViewModel.accessToken.observe(viewLifecycleOwner) { token ->
            sharedViewModel.userId.observe(viewLifecycleOwner) { userId ->
                if (!token.isNullOrEmpty() && userId != -1) {
                    fetchGoals("repeated", token, userId)
                }
            }
        }

        setupSegmentedControl()
        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.ivHomeAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddGoalActivity::class.java).apply {
                putExtra("accessToken", sharedViewModel.accessToken.value ?: "")
                putExtra("userId", sharedViewModel.userId.value ?: -1)
            }
            startActivity(intent)
        }

        binding.ivHomeCicle.setOnClickListener {
            val intent = Intent(requireContext(), ArchiveActivity::class.java)
            startActivity(intent)
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

    private fun fetchGoals(category: String, token: String, userId: Int) {
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
