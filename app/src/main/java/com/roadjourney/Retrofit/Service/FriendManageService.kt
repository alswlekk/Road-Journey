package com.roadjourney.Retrofit.Service

import com.roadjourney.Friend.EnableFriendMainData
import com.roadjourney.Friend.FriendData
import com.roadjourney.Friend.FriendsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendManageService {
    @GET("friends")
    fun getFriendData(@Header("Authorization") token : String,
                      @Query("sortBy") sortBy : String) : Call<FriendsData>

    @GET("friends/{friendUserId}/main")
    fun getEnableViewFriendMain(
        @Path("friendUserId") friendUserId : Int,
        @Query("notificationId") notificationId : Int?=null,
        @Header("Authorization") token : String
    ) : Call<EnableFriendMainData>

}