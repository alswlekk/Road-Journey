package com.roadjourney.Friend

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.DialogFriendBinding
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

            itemView.setOnClickListener {
                showDialog(item)
            }
        }

        private fun showDialog(item: FriendData) {
            val friendDialogBinding = DialogFriendBinding.inflate(LayoutInflater.from(itemView.context))

            friendDialogBinding.tvFriendPopupName.text = item.name
            friendDialogBinding.tvFriendPopupQuote.text = item.introduction
//            friendDialogBinding.tvFriendPopupId.text = item.id
            val friendDialog = AlertDialog.Builder(itemView.context)
                .setView(friendDialogBinding.root)
                .create()

            friendDialogBinding.ivFriendPopupClose.setOnClickListener {
                friendDialog.dismiss()
            }

            friendDialogBinding.btnVisit.setOnClickListener {
                val intent = Intent(itemView.context, FriendProfileActivity::class.java)
                itemView.context.startActivity(intent)
                friendDialog.dismiss()
            }

            friendDialog.show()
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