package com.roadjourney.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.ItemGoalDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoalDetailAdapter(
    private var subGoals: List<SubGoal>,
    private val subGoalType: String,
    private val goalId: Int,
    private val token: String
) : RecyclerView.Adapter<GoalDetailAdapter.GoalDetailViewHolder>() {

    private val apiService = HomeApi.getInstance("http://52.78.84.107:8080", token)
    private var enabledPosition = 0

    inner class GoalDetailViewHolder(private val binding: ItemGoalDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subGoal: SubGoal, position: Int) {
            val goalText = if (subGoalType == "stepByStep") {
                "Step ${position + 1}"
            } else {
                "Sub Goal ${position + 1}"
            }

            binding.tvGoal.text = goalText
            binding.tvGoalDetail.text = subGoal.description

            binding.ivGoalStar1.visibility = if (subGoal.difficulty >= 1) View.VISIBLE else View.GONE
            binding.ivGoalStar2.visibility = if (subGoal.difficulty >= 2) View.VISIBLE else View.GONE
            binding.ivGoalStar3.visibility = if (subGoal.difficulty >= 3) View.VISIBLE else View.GONE
            binding.ivGoalStar4.visibility = if (subGoal.difficulty >= 4) View.VISIBLE else View.GONE
            binding.ivGoalStar5.visibility = if (subGoal.difficulty >= 5) View.VISIBLE else View.GONE

            binding.ivGoalDetailBtn.isEnabled = position == enabledPosition
            if (position < enabledPosition) {
                binding.ivGoalDetailBtn.setBackgroundResource(R.drawable.shape_fill_gray3_25)
            }

            binding.ivGoalDetailBtn.setOnClickListener {
                completeSubGoal(subGoal.subGoalId, position, binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalDetailViewHolder {
        val binding = ItemGoalDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalDetailViewHolder, position: Int) {
        holder.bind(subGoals[position], position)
    }

    override fun getItemCount(): Int = subGoals.size

    private fun completeSubGoal(subGoalId: Int, position: Int, binding: ItemGoalDetailBinding) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.completeSubGoal(goalId.toLong(), subGoalId.toLong(), "Bearer $token")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.status == 200) {
                            binding.ivGoalDetailBtn.setBackgroundResource(R.drawable.shape_fill_gray3_25)
                            binding.ivGoalDetailBtn.isEnabled = false

                            if (position + 1 < subGoals.size) {
                                enabledPosition = position + 1
                                notifyDataSetChanged()
                            } else {
                                binding.root.rootView.findViewById<View>(R.id.tv_goal_detail_btn_success)?.apply {
                                    isEnabled = true
                                    setBackgroundResource(R.drawable.shape_fill_activate_25)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
