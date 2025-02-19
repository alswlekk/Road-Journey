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
import com.roadjourney.SharedViewModel
import com.roadjourney.databinding.DialogBuyBinding
import com.roadjourney.databinding.DialogBuyFailBinding
import com.roadjourney.databinding.DialogItemInfoBinding
import com.roadjourney.databinding.ItemShopBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShopAdapter(
    private var items: List<ShopItem>,
    private val context: Context,
    private val sharedViewModel: SharedViewModel,
    private val updateShopItems: () -> Unit
) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {

    class ShopViewHolder(private val binding: ItemShopBinding, private val sharedViewModel: SharedViewModel, private val updateShopItems: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShopItem, context: Context) {
            binding.ivShop.setImageResource(item.imageRes)
            binding.tvShop.text = item.itemName
            binding.tvShopCoin.text = item.gold.toString()

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
            dialogBinding.tvItemName.text = item.itemName
            dialogBinding.tvItemDetail.text = item.description

            val coinDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.img_coin)
            coinDrawable?.setBounds(0, 0, coinDrawable.intrinsicWidth, coinDrawable.intrinsicHeight)

            val spannable = SpannableString("  ${item.gold}")
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
                purchaseItem(context, item)
            }

            dialog.show()
        }

        private fun purchaseItem(context: Context, item: ShopItem) {
            val baseUrl = "http://52.78.84.107:8080"

            sharedViewModel.accessToken.value?.let { token ->
                if (token.isNotEmpty()) {
                    val apiService = ShopApi.getInstance(baseUrl, token)

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val response = apiService.orderItem(item.itemId, "Bearer $token")
                            withContext(Dispatchers.Main) {
                                if (response.isSuccessful) {
                                    val orderResponse = response.body()

                                    orderResponse?.let {
                                        if (it.status == "success") {
                                            showBuyDialog(context, item, orderResponse.availableGold)
                                        } else {
                                            showBuyFailDialog(context, item, orderResponse.message ?: "구매 실패")
                                        }
                                        updateShopItems()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
            }
        }

        private fun showBuyFailDialog(context: Context, item: ShopItem, reason: String) {
            val buyFailDialog = Dialog(context)
            val buyFailBinding = DialogBuyFailBinding.inflate(LayoutInflater.from(context))
            buyFailDialog.setContentView(buyFailBinding.root)

            buyFailBinding.tvSuccess.text = reason

            buyFailBinding.tvSaveBtn.setOnClickListener {
                buyFailDialog.dismiss()
            }

            buyFailDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            buyFailDialog.show()
        }

        private fun showBuyDialog(context: Context, item: ShopItem, availableGold: Int) {
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
        return ShopViewHolder(binding, sharedViewModel, updateShopItems)
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
