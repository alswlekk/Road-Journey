package com.roadjourney.Archive

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemArchiveFailBinding

class ArchiveAdapter(private val items: List<ArchiveItem>) :
    RecyclerView.Adapter<ArchiveAdapter.ArchiveViewHolder>() {

    inner class ArchiveViewHolder(private val binding: ItemArchiveFailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArchiveItem) {
            binding.tvArchiveGoal.text = item.goal
            binding.tvArchiveDetail.text = item.detail
            binding.tvArchiveStart.text = "등록일 : ${item.startDate}"
            binding.tvArchiveProgress.text = "진행도 : ${item.progress}%"
            binding.pbArchive.progress = item.progress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val binding =
            ItemArchiveFailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArchiveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
