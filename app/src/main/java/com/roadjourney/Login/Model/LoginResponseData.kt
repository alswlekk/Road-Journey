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
//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readInt()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(accessToken)
//        parcel.writeInt(userId)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<TokenData> {
//        override fun createFromParcel(parcel: Parcel): TokenData {
//            return TokenData(parcel)
//        }
//
//        override fun newArray(size: Int): Array<TokenData?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
