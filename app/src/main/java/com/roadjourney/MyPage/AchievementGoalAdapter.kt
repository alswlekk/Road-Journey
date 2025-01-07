package com.roadjourney.MyPage

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.DialogSuccessAchievementGoalBinding
import com.roadjourney.databinding.ItemAchievementBinding

class AchievementGoalAdapter(
    private val achievements: List<AchievementData>
) : RecyclerView.Adapter<AchievementGoalAdapter.AchievementViewHolder>() {
    inner class AchievementViewHolder(private val binding: ItemAchievementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AchievementData) {
            binding.tvAchievementMission.text = item.achievementMission
            binding.tvAchievementReward.text = item.achievementReward
            binding.tvAchievementTitle.text = item.achievementTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding =
            ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        binding.ivAchievement.setOnClickListener() {
            if (binding.ivAchievement.drawable.equals(R.drawable.img_acheivement_success)) {
                val successDialogBinding =
                    DialogSuccessAchievementGoalBinding.inflate(LayoutInflater.from(parent.context))
                val successDialog = AlertDialog.Builder(parent.context)
                    .setView(successDialogBinding.root)
                    .create()

                successDialogBinding.btnFinish.setOnClickListener {
                    successDialog.dismiss()
                    binding.ivAchievement.setImageResource(R.drawable.img_achievement_complete)
                    binding.ivAchievement.isClickable = false
                }
                successDialog.show()
            }
        }

        return AchievementViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        holder.bind(achievements[position])
    }
}