package com.roadjourney.Shop

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R
import com.roadjourney.databinding.DialogBuyBinding
import com.roadjourney.databinding.DialogItemInfoBinding
import com.roadjourney.databinding.ItemShopBinding

class ShopAdapter(private var items: List<ShopItem>, private val context: Context) :
    RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(private val binding: ItemShopBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShopItem, context: Context) {
            binding.ivShop.setImageResource(item.imageRes)
            binding.tvShop.text = item.name
            binding.tvShopCoin.text = item.price

            binding.root.setOnClickListener {
                showItemDialog(item, context)
            }
        }

        private fun showItemDialog(item: ShopItem, context: Context) {
            val dialog = Dialog(context)
            val dialogBinding = DialogItemInfoBinding.inflate(LayoutInflater.from(context))
            dialog.setContentView(dialogBinding.root)
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialogBinding.tvItem.text = item.category
            dialogBinding.ivShop.setImageResource(item.imageRes)
            dialogBinding.tvItemName.text = item.name
            val coinDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.img_coin)
            coinDrawable?.setBounds(0, 0, coinDrawable.intrinsicWidth, coinDrawable.intrinsicHeight)

            val spannable = SpannableString("  ${item.price}")
            coinDrawable?.let {
                val imageSpan = ImageSpan(it, ImageSpan.ALIGN_BOTTOM)
                spannable.setSpan(imageSpan, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }

            dialogBinding.tvItemSaveBtn.text = spannable

            dialogBinding.ivItemClose.setOnClickListener {
                dialog.dismiss()
            }

            dialogBinding.tvItemSaveBtn.setOnClickListener {
                dialog.dismiss()
                showBuyDialog(context, item)
            }

            dialog.show()
        }

        private fun showBuyDialog(context: Context, item: ShopItem) {
            val buyDialog = Dialog(context)
            val buyBinding = DialogBuyBinding.inflate(LayoutInflater.from(context))
            buyDialog.setContentView(buyBinding.root)

            buyBinding.tvSaveBtn.setOnClickListener {
                buyDialog.dismiss()
            }

            buyDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            buyDialog.show()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding = ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<ShopItem>) {
        items = newItems
        notifyDataSetChanged()
    }
}
