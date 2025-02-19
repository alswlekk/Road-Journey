package com.roadjourney.Friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.DialogFriendRequestBinding
import com.roadjourney.databinding.ItemFriendRequestBinding

class FriendRequestAdapter(
    private val requests: ArrayList<FriendRequestData>
) : RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val binding = ItemFriendRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FriendRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount(): Int = requests.size

    inner class FriendRequestViewHolder(
        private val binding: ItemFriendRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(request: FriendRequestData) {
            binding.apply {
                ivFriendRcProfile.setImageResource(request.profileImage.toInt())
                val time = request.createdAt
                tvRcMessage.text = "${request.nickname}님이 친구 요청을 보냈어요!"
                if (time.contains("T")) {
                    tvRcTime.text = time.split("T")[0] + " " + time.split("T")[1].substring(0, 5)
                } else {
                    tvRcTime.text = time
                }
            }

            binding.tvRcDetail.setOnClickListener {
                val dialogBinding = DialogFriendRequestBinding.inflate(LayoutInflater.from(binding.root.context))

                dialogBinding.tvFriendPopupName.text = request.nickname
                dialogBinding.tvFriendPopupQuote.text = request.statusMessage
                dialogBinding.tvFriendPopupId.text = "아이디 : ${request.accountId}"
//                dialogBinding.ivFriendPopupProfile.setImageResource(request.profileImage.toInt())

                val dialog = androidx.appcompat.app.AlertDialog.Builder(binding.root.context)
                    .setView(dialogBinding.root)
                    .create()

                dialogBinding.ivFriendPopupClose.setOnClickListener {
                    dialog.dismiss()
                    requests.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }

                dialogBinding.btnFriendPopupReject.setOnClickListener {
                    dialog.dismiss()
                    requests.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }

                dialogBinding.btnFriendPopupAccept.setOnClickListener {
                    dialog.dismiss()
                    requests.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }

                dialog.show()
            }
        }
    }
}