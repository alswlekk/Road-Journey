package com.roadjourney.Login.Model

data class SignupData(
    var accountId : String,
    var accountPw : String,
    var email : String,
    var nickname : String,
    var profileImage : String,
    var statusMessage : String? = null
)
