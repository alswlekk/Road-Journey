package com.roadjourney.MyPage

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ItemApiService {
    @GET("items/storage")
    suspend fun getStorageItems(
        @Query("category") category: String,
        @Header("Authorization") authToken: String
    ): StorageApiResponse
}