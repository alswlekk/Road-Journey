package com.roadjourney.Shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.ItemShopEventBinding

class ShopEventAdapter : RecyclerView.Adapter<ShopEventAdapter.ShopEventViewHolder>() {

    private var items: List<ShopItem> = listOf()

    inner class ShopEventViewHolder(private val binding: ItemShopEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ShopItem) {
            binding.tvShopEventName.text = item.itemName
            binding.ivShopEvent.setImageResource(changeImageResource(item.itemName))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopEventViewHolder {
        val binding = ItemShopEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopEventViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ShopItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun changeImageResource(itemName: String): Int {
        return when (itemName) {
            "밤하늘" -> R.drawable.img_sky
            "기본 방" -> R.drawable.img_normal
            "바다" -> R.drawable.img_ocean
            "에펠탑" -> R.drawable.img_tower
            "숲" -> R.drawable.img_forest
            "벚꽃길" -> R.drawable.img_flower
            "설원" -> R.drawable.img_snow
            "음료수" -> R.drawable.img_juice
            "화분" -> R.drawable.img_pot
            "게임기" -> R.drawable.img_game
            "스마트폰" -> R.drawable.img_phone
            "산타 양말" -> R.drawable.img_socks
            "책" -> R.drawable.img_book
            "베개" -> R.drawable.img_pillow
            "강아지" -> R.drawable.img_dog
            "다람쥐" -> R.drawable.img_squirrel
            "여우" -> R.drawable.img_fox
            "펭귄" -> R.drawable.img_penguin
            else -> R.drawable.img_normal
        }
    }
}
