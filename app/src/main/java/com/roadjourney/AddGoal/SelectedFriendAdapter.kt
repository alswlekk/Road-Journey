package com.roadjourney.AddGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemAddFriendSuccessBinding

class SelectedFriendAdapter(private val selectedFriends: MutableList<Friend>) :
    RecyclerView.Adapter<SelectedFriendAdapter.SelectedFriendViewHolder>() {

    inner class SelectedFriendViewHolder(val binding: ItemAddFriendSuccessBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedFriendViewHolder {
        val binding = ItemAddFriendSuccessBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectedFriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectedFriendViewHolder, position: Int) {
        val friend = selectedFriends[position]
        with(holder.binding) {
            tvAddFriendSuccessName.text = friend.name
            tvAddFriendSuccessId.text = friend.id
        }
    }

    override fun getItemCount(): Int = selectedFriends.size

    fun updateList(newList: List<Friend>) {
        selectedFriends.clear()
        selectedFriends.addAll(newList)
        notifyDataSetChanged()
    }
}
