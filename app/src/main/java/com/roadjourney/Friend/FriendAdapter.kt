package com.roadjourney.Friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemFriendBinding

class FriendAdapter(private var items: List<FriendData>) :
    RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    inner class FriendViewHolder(val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FriendData) {
            binding.tvFriendRcTime.text = "최근 접속 : ${item.recentLogin}"
            binding.tvFriendRcName.text = item.name
            binding.ivFriendRcProfile.setImageResource(item.profileImage)
            binding.tvFriendRcQuote.text = item.introduction
            binding.tvFriendRcGoal.text = "달성 목표: ${item.goalsAchieved}개"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(data: List<FriendData>) {
        items = data
        notifyDataSetChanged()
    }

}