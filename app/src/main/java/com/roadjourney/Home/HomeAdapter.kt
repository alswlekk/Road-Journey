package com.roadjourney.Home

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemHomeBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadjourney.databinding.DialogGoalDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeAdapter(private var items: List<GoalItem>, private val context: Context, private val token: String) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GoalItem) {
            binding.tvHomeGoal.text = "Goals " + item.goalId.toString()
            binding.tvHomeDetail.text = item.title
            binding.tvHomeProgress.text = "진행도 : ${item.progress}%"
            binding.tvHomeDday.text = calculateDDay(item.expireAt)
            binding.pbHome.progress = item.progress

            binding.ivHomeStar1.visibility = if (item.difficulty >= 1) View.VISIBLE else View.GONE
            binding.ivHomeStar2.visibility = if (item.difficulty >= 2) View.VISIBLE else View.GONE
            binding.ivHomeStar3.visibility = if (item.difficulty >= 3) View.VISIBLE else View.GONE
            binding.ivHomeStar4.visibility = if (item.difficulty >= 4) View.VISIBLE else View.GONE
            binding.ivHomeStar5.visibility = if (item.difficulty >= 5) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                showGoalDetailDialog(context, item.goalId, token)
            }
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

    private fun showGoalDetailDialog(context: Context, goalId: Int, token: String) {
        val dialog = Dialog(context)
        val dialogBinding = DialogGoalDetailBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        dialogBinding.ivGoalDetailClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

        val apiService = HomeApi.getInstance("http://52.78.84.107:8080", token)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getGoalDetail(goalId, "Bearer $token")

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { goalDetailResponse ->
                            if (goalDetailResponse.status == 200) {
                                val goal = goalDetailResponse.result
                                val dateInfo = goal.dateInfo
                                val subGoals = goal.subGoalList

                                dialogBinding.tvGoalDetailId.text = "Goal ${goal.goalId}"
                                dialogBinding.tvGoalDetailTitle.text = goal.title
                                dialogBinding.tvGoalDetailContent.text = goal.description
                                dialogBinding.tvGoalDetailPeriod.text = "${dateInfo.startAt} ~ ${dateInfo.expireAt}"
                                dialogBinding.tvGoalDetailEnddate.text = calculateDDay(dateInfo.expireAt)
                                dialogBinding.pbGoalDetail.progress = goal.progress

                                setStarsVisibility(dialogBinding, goal.difficulty)

                                dialogBinding.rvGoalDetailGoal.layoutManager = LinearLayoutManager(context)
                                val subGoalAdapter = GoalDetailAdapter(subGoals, goal.subGoalType)
                                dialogBinding.rvGoalDetailGoal.adapter = subGoalAdapter
                            }
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun setStarsVisibility(binding: DialogGoalDetailBinding, difficulty: Int) {
        binding.ivGoalDetailStar1.visibility = if (difficulty >= 1) View.VISIBLE else View.GONE
        binding.ivGoalDetailStar2.visibility = if (difficulty >= 2) View.VISIBLE else View.GONE
        binding.ivGoalDetailStar3.visibility = if (difficulty >= 3) View.VISIBLE else View.GONE
        binding.ivGoalDetailStar4.visibility = if (difficulty >= 4) View.VISIBLE else View.GONE
        binding.ivGoalDetailStar5.visibility = if (difficulty >= 5) View.VISIBLE else View.GONE
    }

    private fun calculateDDay(expireDate: String): String {
        return try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val today = LocalDate.now()
            val goalDate = LocalDate.parse(expireDate, formatter)

            val daysBetween = ChronoUnit.DAYS.between(today, goalDate)

            when {
                daysBetween > 0 -> "D-${daysBetween}"
                daysBetween == 0L -> "D-Day"
                else -> "D+${-daysBetween}"
            }
        } catch (e: Exception) {
            "날짜 오류"
        }
    }
}



