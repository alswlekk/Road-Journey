package com.roadjourney.MyPage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.roadjourney.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        setupClickListeners()
        achievementClickListeners()
        accountClickListeners()
        return binding.root
    }

    private fun accountClickListeners() {
        binding.clMyPageAccount.setOnClickListener {
            val intent = Intent(requireContext(), AccountManagementActivity::class.java)
            startActivity(intent)
        }
    }

    private fun achievementClickListeners() {
        binding.clMyPageChallenge.setOnClickListener {
            val intent = Intent(requireContext(), AchievementActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupClickListeners() {
        binding.tvMyPageBtn.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
