package com.roadjourney.Login.Model

import android.os.Parcel
import android.os.Parcelable

data class LoginResponseData(
    val status : String,
    var data : TokenData
)
data class TokenData (
    val accessToken : String,
    var userId : Int
)
