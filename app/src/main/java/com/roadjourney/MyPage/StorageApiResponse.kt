package com.roadjourney.MyPage

data class StorageApiResponse(
    val items: List<StorageItem>
)

data class StorageItem(
    val userItemId: Int,
    val itemName: String,
    val category: String,
    val selected: Boolean,
    val growthPoint: Int,
    val growthLevel: Int,
    val description: String
)

data class EquipItemRequest(
    val isEquipped: Boolean
)

data class EquipItemResponse(
    val status: String,
    val message: String
)