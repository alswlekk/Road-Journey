package com.roadjourney.AddGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.roadjourney.R
import com.roadjourney.databinding.ItemAddFriendBinding

class FriendAdapter(
    private var friendList: List<Friend>,
    private val onFriendToggled: (Friend, Boolean) -> Unit
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    private val addedFriends = mutableSetOf<String>()

    inner class FriendViewHolder(val binding: ItemAddFriendBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemAddFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]

        with(holder.binding) {
            tvAddFriendName.text = friend.nickname
            tvAddFriendId.text = friend.accountId

            Glide.with(ivAddFriendProfile.context)
                .load(friend.profileImage)
                .placeholder(R.drawable.img_friend_profile1)
                .error(R.drawable.img_friend_profile1)
                .circleCrop()
                .into(ivAddFriendProfile)

            val isAdded = addedFriends.contains(friend.accountId)
            updateButtonState(isAdded, tvAddFriendBtn)

            tvAddFriendBtn.setOnClickListener {
                if (isAdded) {
                    addedFriends.remove(friend.accountId)
                    updateButtonState(false, tvAddFriendBtn)
                    onFriendToggled(friend, false)
                } else {
                    addedFriends.add(friend.accountId)
                    updateButtonState(true, tvAddFriendBtn)
                    onFriendToggled(friend, true)
                }
            }
        }
    }

    override fun getItemCount(): Int = friendList.size

    fun updateList(newList: List<Friend>) {
        friendList = newList
        notifyDataSetChanged()
    }

    private fun updateButtonState(isAdded: Boolean, button: TextView) {
        if (isAdded) {
            button.text = "추가 완료"
            button.setBackgroundResource(R.drawable.shape_fill_gray3_15)
        } else {
            button.text = "추가하기"
            button.setBackgroundResource(R.drawable.shape_fill_blue1_15)
        }
    }
}
