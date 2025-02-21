package com.roadjourney.Friend

data class SendRequestData(var friendUserId : Int)

data class SendRequestResponse(val status : String, val message : String)
