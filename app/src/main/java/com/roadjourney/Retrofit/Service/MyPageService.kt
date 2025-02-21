package com.roadjourney.Retrofit.Service

import com.roadjourney.MyPage.Model.MyPageUserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MyPageService {
    @GET("my")
    fun getMyInformation(@Header("Authorization") authorization: String): Call<MyPageUserData>
}
