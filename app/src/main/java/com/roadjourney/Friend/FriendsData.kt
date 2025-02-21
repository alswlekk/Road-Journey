package com.roadjourney.Friend

import java.time.LocalDateTime

data class FriendsData(
    var friends : List<FriendData>
)

data class FriendData(
    val userId : Int,
    var accountId : String,
    var nickname : String,
    var profileImage : String,
    var statusMessage : String,
    var lastLoginTime : String,
    var achievementCount : Int
)

data class EnableFriendMainData(
    var friendStatus : String,
    var activeStatus : String
)