package com.roadjourney.Retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val BASE_URL = "http://52.78.84.107:8080/"

    var retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var cookies = ""

    fun setCookie(cookie: String) {
        if(cookies.isEmpty()) {
            cookies = cookie
            Log.e("쿠키", cookies)
        } else {
            Log.d("cookie", "none")
        }

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient(cookie))
            .build()
    }

    private fun createOkHttpClient(cookies: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoginInterceptor(cookies))
            .build()
    }
}