package com.roadjourney.MyPage.Model

data class MyPageUserData(
    var status : String,
    var data : UserData
)

data class UserData(
    var accountId : String,
    var email : String,
    var nickname : String,
    var profileImage : String,
    var statusMessage : String? = null
)
