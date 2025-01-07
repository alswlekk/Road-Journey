package com.roadjourney.MyPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.databinding.FragmentAchievementGoalBinding

class AchievementGoalFragment : Fragment() {
    lateinit var binding: FragmentAchievementGoalBinding
    private lateinit var achievementGoalAdapter: AchievementGoalAdapter
    private var goals = ArrayList<AchievementData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAchievementGoalBinding.inflate(inflater, container, false)

        initAdapter()
        initGoalData()


        return binding.root
    }

    private fun initAdapter() {
        achievementGoalAdapter = AchievementGoalAdapter(goals)
        binding.rvAchievementGoal.apply {
            adapter = achievementGoalAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun initGoalData() {
        goals.addAll(
            arrayListOf(
                AchievementData(
                    "천리 길도 한 걸음부터",
                    "목표를 1개 달성하세요.",
                    "성장 포인트 +5 골드 + 100"
                ),
                AchievementData(
                    "세상에 틀린 길은 없다",
                    "목표를 10개 달성하세요.",
                    "성장 포인트 + 10 골드 + 300"
                ),
                AchievementData(
                    "Road Journey!",
                    "목표를 100개 달성하세요.",
                    "성장 포인트 + 10 골드 + 500"
                ),
                AchievementData(
                    "No Pain, No Gain",
                    "난이도 5의 목표를 달성하세요.",
                    "성장 포인트 + 20 골드 + 1000"
                )
            )
        )
    }

}