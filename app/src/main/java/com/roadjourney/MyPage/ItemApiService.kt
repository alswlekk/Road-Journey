package com.roadjourney.MyPage

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemApiService {
    @GET("items/storage")
    suspend fun getStorageItems(
        @Query("category") category: String,
        @Header("Authorization") authToken: String
    ): StorageApiResponse

    @PATCH("items/storage/{userItemId}/equip")
    suspend fun equipItem(
        @Path("userItemId") userItemId: Int,
        @Header("Authorization") authToken: String,
        @Body request: EquipItemRequest
    ): Response<EquipItemResponse>
}
