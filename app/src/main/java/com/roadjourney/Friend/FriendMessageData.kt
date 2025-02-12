package com.roadjourney.Friend

import com.roadjourney.R

data class FriendMessageData(
    val message: String,
    val createdAt: String,
    val category: String,
    val relatedId: Int, // //(친구의) userId or (공유받은) goalId
    val profilePicUrl: String? = R.drawable.ic_alarm.toString(),
)
