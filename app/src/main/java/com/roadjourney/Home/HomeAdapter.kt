package com.roadjourney.Home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemHomeBinding

class HomeAdapter(private var items: List<GoalItem>) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GoalItem) {
            binding.tvHomeGoal.text = item.goalId
            binding.tvHomeDetail.text = item.goalName
            binding.tvHomeDday.text = item.dDay
            binding.tvHomeProgress.text = item.progressText
            binding.pbHome.progress = item.progressValue

            binding.ivHomeStar1.visibility = if (item.priority >= 1) View.VISIBLE else View.GONE
            binding.ivHomeStar2.visibility = if (item.priority >= 2) View.VISIBLE else View.GONE
            binding.ivHomeStar3.visibility = if (item.priority >= 3) View.VISIBLE else View.GONE
            binding.ivHomeStar4.visibility = if (item.priority >= 4) View.VISIBLE else View.GONE
            binding.ivHomeStar5.visibility = if (item.priority >= 5) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<GoalItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}

