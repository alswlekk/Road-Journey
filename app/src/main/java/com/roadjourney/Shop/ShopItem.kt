package com.roadjourney.Shop

import com.roadjourney.R

data class ShopApiResponse(
    val availableGold: Int,
    val items: List<ShopItem>
)

data class ShopEventApiResponse(
    val availableGold: Int,
    val specialItems: List<ShopItem>
)

data class ShopItem(
    val itemId: Int,
    val itemName: String,
    val category: String,
    val description: String,
    val gold: Int,
    val selected: Boolean,
    val owned: Boolean,
    val imageRes: Int = R.drawable.img_normal
)

data class OrderResponse(
    val purchasedItemId: Int,
    val availableGold: Int,
    val status: String,
    val message: String? = null
)

data class SpecialOrderResponse(
    val message: String,
    val status: String,
    val availableGold: Int,
    val itemName: String,
    val description: String
)
