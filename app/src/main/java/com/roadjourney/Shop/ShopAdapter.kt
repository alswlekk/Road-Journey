package com.roadjourney.Shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.roadjourney.R


class ShopAdapter(private val items: List<ShopItem>) : RecyclerView.Adapter<ShopAdapter.ShopViewHolder>() {
    class ShopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivShop: ImageView = view.findViewById(R.id.iv_shop)
        private val tvShop: TextView = view.findViewById(R.id.tv_shop)
        private val ivShopCoin: ImageView = view.findViewById(R.id.iv_shop_coin)
        private val tvShopCoin: TextView = view.findViewById(R.id.tv_shop_coin)

        fun bind(item: ShopItem) {
            ivShop.setImageResource(item.imageRes)
            tvShop.text = item.name
            tvShopCoin.text = item.price
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false)
        return ShopViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}