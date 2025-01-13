package com.roadjourney.AddGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.ItemGoalBinding

class GoalAdapter(
    private val goals: MutableList<String>
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    inner class GoalViewHolder(private val binding: ItemGoalBinding) : RecyclerView.ViewHolder(binding.root) {
        private val starStates = BooleanArray(5)

        fun bind(goal: String) {
            binding.tvGoal.text = goal

            val stars = listOf(
                binding.ivGoalStar1,
                binding.ivGoalStar2,
                binding.ivGoalStar3,
                binding.ivGoalStar4,
                binding.ivGoalStar5
            )

            stars.forEachIndexed { index, imageView ->
                imageView.setOnClickListener {
                    starStates[index] = !starStates[index]
                    imageView.setImageResource(
                        if (starStates[index]) R.drawable.ic_home_star else R.drawable.ic_add_goal_star
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val binding = ItemGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    override fun getItemCount(): Int = goals.size

    fun addGoal(goal: String) {
        goals.add(goal)
        notifyItemInserted(goals.size - 1)
    }

    fun removeGoal() {
        if (goals.isNotEmpty()) {
            val lastIndex = goals.size - 1
            goals.removeAt(lastIndex)
            notifyItemRemoved(lastIndex)
        }
    }
}
