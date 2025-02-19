package com.roadjourney.Retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoginInterceptor(private val cookies: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        // 쿠키가 존재하는 경우에만 추가
        cookies?.takeIf { it.isNotEmpty() }?.let { cookie ->
            request.addHeader("Cookie", cookie)
        }

        Log.d("LoginInterceptor", "쿠키 추가됨: $cookies")

        return chain.proceed(request.build())
    }
}
