package com.roadjourney.Friend

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.DialogFriendReceivedMessageBinding
import com.roadjourney.databinding.ItemMessageReceivedBinding

class FriendReceivedMessageAdapter(
    private val messages: ArrayList<FriendMessageData>
) : RecyclerView.Adapter<FriendReceivedMessageAdapter.FriendReceivedMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendReceivedMessageViewHolder {
        val binding = ItemMessageReceivedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )


        return FriendReceivedMessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FriendReceivedMessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size

    inner class FriendReceivedMessageViewHolder(
        private val binding: ItemMessageReceivedBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: FriendMessageData) {
            binding.tvRcMessage.text = message.message
            binding.tvRcTime.text = message.createdAt
            if(message.category == "SHARED_GOAL") {
                binding.tvRcDetail.visibility = ViewGroup.VISIBLE
                binding.tvRcVisit.visibility = ViewGroup.GONE
            } else {
                binding.tvRcDetail.visibility = ViewGroup.GONE
                binding.tvRcVisit.visibility = ViewGroup.VISIBLE
            }

            binding.tvRcDetail.setOnClickListener {
                val dialogBinding = DialogFriendReceivedMessageBinding.inflate(LayoutInflater.from(binding.root.context))
                val dialog = AlertDialog.Builder(binding.root.context)
                    .setView(dialogBinding.root)
                    .create()
                dialogBinding.ivFriendReceivedMessageClose.setOnClickListener {
                    dialog.dismiss()
                    messages.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
                dialogBinding.btnFriendReceivedMessageReject.setOnClickListener {
                    dialog.dismiss()
                    messages.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
                dialogBinding.btnFriendReceivedMessageAccept.setOnClickListener {
                    dialog.dismiss()
                    messages.removeAt(adapterPosition)
                    notifyDataSetChanged()
                }
                dialog.show()
            }

            binding.tvRcVisit.setOnClickListener {
                messages.removeAt(adapterPosition)
                notifyDataSetChanged()
                val intent = Intent(binding.root.context, FriendProfileActivity::class.java)
                val name = message.message.split("님이")[0]
                intent.putExtra("tvHomeName", name)
                binding.root.context.startActivity(intent)
            }
        }
    }
}