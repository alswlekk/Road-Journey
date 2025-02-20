package com.roadjourney.AddGoal

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FriendService {
    @GET("/friends/search")
    fun searchFriends(
        @Header("Authorization") token: String,
        @Query("searchId") searchId: String
    ): Call<FriendResponse>
}

data class FriendResponse(
    val users: List<Friend>
)
