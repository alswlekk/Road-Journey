package com.roadjourney.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemGoalDetailBinding

class GoalDetailAdapter(
    private val subGoals: List<SubGoal>,
    private val subGoalType: String
) : RecyclerView.Adapter<GoalDetailAdapter.GoalDetailViewHolder>() {

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
}
