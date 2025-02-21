package com.roadjourney.Retrofit.Service

import com.roadjourney.Friend.EnableFriendMainData
import com.roadjourney.Friend.FriendData
import com.roadjourney.Friend.FriendRequestData
import com.roadjourney.Friend.FriendsData
import com.roadjourney.Friend.SendRequestData
import com.roadjourney.Friend.SendRequestResponse
import com.roadjourney.Login.Model.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @POST("friends/requests")
    fun postFriendRequest(
        @Header("Authorization") token : String,
        @Body sendRequestData : SendRequestData
    ) : Call<SendRequestResponse>

    @GET("friends/requests")
    fun getFriendRequestData(
        @Header("Authorization") token : String
    ) : Call<FriendRequestsData>

    data class FriendRequestsData(
        val requests : List<FriendRequestData>
    )

    @POST("friends/requests/{friendId}/action")
    fun postFriendRequestAction(
        @Header("Authorization") token : String,
        @Path("friendId") friendId : Int,
        @Body action : String // accept or reject
    ) : Call<FriendRequestActionResponse>

    data class FriendRequestActionResponse(
        val status : String,
        val message : String
    )
}