package com.roadjourney.AddGoal

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.ItemAddFriendBinding

class FriendAdapter(
    private var friendList: List<Friend>,
    private val onFriendToggled: (Friend, Boolean) -> Unit // 친구 추가/삭제 이벤트 콜백
) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    private val addedFriends = mutableSetOf<String>() // 선택된 친구 저장

    inner class FriendViewHolder(val binding: ItemAddFriendBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val binding = ItemAddFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]

        with(holder.binding) {
            tvAddFriendName.text = friend.name
            tvAddFriendId.text = friend.id

            val isAdded = addedFriends.contains(friend.id)
            updateButtonState(isAdded, tvAddFriendBtn)

            tvAddFriendBtn.setOnClickListener {
                if (isAdded) {
                    addedFriends.remove(friend.id)
                    updateButtonState(false, tvAddFriendBtn)
                    onFriendToggled(friend, false) // 삭제 콜백 호출
                } else {
                    addedFriends.add(friend.id)
                    updateButtonState(true, tvAddFriendBtn)
                    onFriendToggled(friend, true) // 추가 콜백 호출
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
