package com.roadjourney.Home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.AddGoalActivity
import com.roadjourney.R
import com.roadjourney.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeAdapter = HomeAdapter(emptyList())
        binding.rvHome.adapter = homeAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())

        binding.rgHome.check(R.id.rb_repeat)
        updateRecyclerViewData(getRepeatData())

        setupSegmentedControl()
        setupClickListeners()
        return binding.root
    }

    private fun setupClickListeners() {
        binding.ivHomeAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddGoalActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupSegmentedControl() {
        binding.rgHome.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_repeat -> {
                    updateRecyclerViewData(getRepeatData())
                }
                R.id.rb_short -> {
                    updateRecyclerViewData(getShortTermData())
                }
                R.id.rb_long -> {
                    updateRecyclerViewData(getLongTermData())
                }
            }
        }
    }

    private fun updateRecyclerViewData(data: List<GoalItem>) {
        homeAdapter.updateItems(data)
    }

    private fun getRepeatData(): List<GoalItem> {
        return listOf(
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
            GoalItem("Goal 1", "반복 목표 1", "D-3", "진행도: 70%", 70, 3),
            GoalItem("Goal 2", "반복 목표 2", "D-5", "진행도: 40%", 40, 1),
        )
    }

    private fun getShortTermData(): List<GoalItem> {
        return listOf(
            GoalItem("Goal 1", "단기 목표 1", "D-1", "진행도: 90%", 90, 5),
            GoalItem("Goal 2", "단기 목표 2", "D-2", "진행도: 50%", 50, 2),
        )
    }

    private fun getLongTermData(): List<GoalItem> {
        return listOf(
            GoalItem("Goal 1", "장기 목표 1", "D-30", "진행도: 20%", 20, 4),
            GoalItem("Goal 2", "장기 목표 2", "D-50", "진행도: 10%", 10, 1),
        )
    }
}
