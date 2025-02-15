package com.roadjourney.MyPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.databinding.ItemAlarmBinding

class AlarmAdapter(
    private val alarmList: MutableList<AlarmItem>,
    private val onRemoveItem: (Int) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(private val binding: ItemAlarmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlarmItem, position: Int) {
            binding.tvAlarmMessage.text = item.message
            binding.tvAlarmMessageDetail.text = item.detail
            binding.tvAlarmTime.text = item.time

            binding.tvAlarmBtn.setOnClickListener {
                onRemoveItem(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val binding = ItemAlarmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(alarmList[position], position)
    }

    override fun getItemCount(): Int = alarmList.size
}
