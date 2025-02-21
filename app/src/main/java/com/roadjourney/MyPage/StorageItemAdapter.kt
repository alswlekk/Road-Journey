package com.roadjourney.MyPage

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.DialogItemStorageBinding
import com.roadjourney.databinding.ItemStorageBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StorageItemAdapter(
    private var items: List<StorageItem>,
    private val context: Context,
    private val token: String
) : RecyclerView.Adapter<StorageItemAdapter.StorageItemViewHolder>() {

    inner class StorageItemViewHolder(private val binding: ItemStorageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StorageItem) {
            binding.tvStorage.text = item.itemName
            binding.ivStorage.setImageResource(changeImageResource(item.itemName))

            val backgroundRes = if (item.selected) {
                R.drawable.item_shop_background_selected
            } else {
                R.drawable.item_shop_background
            }
            binding.root.setBackgroundResource(backgroundRes)

            binding.root.setOnClickListener {
                showItemDialog(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StorageItemViewHolder {
        val binding = ItemStorageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StorageItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StorageItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<StorageItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    private fun changeImageResource(itemName: String): Int {
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

    private fun showItemDialog(item: StorageItem) {
        val dialog = Dialog(context)
        val binding = DialogItemStorageBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)

        binding.tvItem.text = when (item.category) {
            "wallpaper" -> "배경"
            "ornament" -> "장식품"
            "character" -> "캐릭터"
            else -> "기타"
        }
        binding.tvItemName.text = item.itemName
        binding.tvItemDetail.text = item.description
        binding.ivShop.setImageResource(changeImageResource(item.itemName))

        binding.tvItemSaveBtn.text = if (item.selected) "장착 해제" else "장착하기"

        if (item.category == "wallpaper" || item.category == "character") {
            if(binding.tvItemSaveBtn.text == "장착 해제" ){
                binding.tvItemSaveBtn.isEnabled = false
                binding.tvItemSaveBtn.setBackgroundResource(R.drawable.shape_fill_gray3_25)
            }
        } else {
            binding.tvItemSaveBtn.isEnabled = true
            binding.tvItemSaveBtn.setBackgroundResource(R.drawable.shape_fill_blue_25)
            binding.tvItemSaveBtn.setOnClickListener {
                equipItem(item.userItemId, !item.selected, dialog)
                dialog.dismiss()
            }
        }

        binding.tvItemSaveBtn.setOnClickListener {
            equipItem(item.userItemId, !item.selected, dialog)
            dialog.dismiss()
        }

        binding.ivItemClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun equipItem(userItemId: Int, isEquipped: Boolean, dialog: Dialog) {
        val apiService = ItemApi.getInstance("http://52.78.84.107:8080", token)

        (context as? AppCompatActivity)?.lifecycleScope?.launch(Dispatchers.IO) {
            try {
                val response = apiService.equipItem(userItemId, "Bearer $token", EquipItemRequest(isEquipped))

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        dialog.dismiss()
                        (context as? ItemActivity)?.fetchItems("all", token)
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
