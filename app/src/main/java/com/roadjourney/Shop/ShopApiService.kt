package com.roadjourney.Shop

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ShopApiService {
    @GET("items/shop")
    suspend fun getShopItems(
        @Query("category") category: String,
        @Header("Authorization") authToken: String
    ): ShopApiResponse

    @POST("items/shop/order")
    suspend fun orderItem(
        @Query("itemId") itemId: Int,
        @Header("Authorization") authToken: String
    ): Response<OrderResponse>

    @GET("items/special")
    suspend fun getSpecialShopItems(
        @Header("Authorization") authToken: String
    ): ShopEventApiResponse

    @POST("items/special/order")
    suspend fun orderSpecialItem(
        @Header("Authorization") authToken: String
    ): Response<SpecialOrderResponse>
}