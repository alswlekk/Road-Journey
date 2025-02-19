package com.roadjourney.Home

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HomeApi {
    fun getInstance(baseUrl: String, token: String): HomeApiService {
        val client = okhttp3.OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(HomeApiService::class.java)
    }
}
