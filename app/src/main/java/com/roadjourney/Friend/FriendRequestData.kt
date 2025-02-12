package com.roadjourney.Friend

data class FriendRequestData(
    val friendId : Int,
    val userId : Int,
    val accountId: String,
    val nickname : String,
    val profileImage : String,
    val statusMessage : String? = null,
    val friendStatus : String,
    val createdAt : String,
)
