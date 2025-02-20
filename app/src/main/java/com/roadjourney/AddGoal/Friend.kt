package com.roadjourney.AddGoal

data class Friend(
    val userId: Int,
    val accountId: String,
    val nickname: String,
    val profileImage: String,
    val statusMessage: String?,
    val friendStatus: String
)
