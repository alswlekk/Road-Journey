package com.roadjourney.Retrofit

import android.content.Context
import com.roadjourney.Login.Model.TokenData

object SharedPreferencesHelper {
    private const val PREF_NAME = "AppPreferences"
    private const val ACCESS_TOKEN_KEY = "access_token"
    private const val USER_ID_KEY = "user_id"
    private const val COOKIE_KEY = "Cookie"

    private fun getPreferences(context: Context) =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // 쿠키 저장
    fun saveCookie(context: Context, cookie: String) {
        getPreferences(context).edit().putString(COOKIE_KEY, cookie).apply()
    }

    // 쿠키 불러오기
    fun loadCookie(context: Context): String? {
        return getPreferences(context).getString(COOKIE_KEY, null)
    }

    // 쿠키 삭제
    fun clearCookie(context: Context) {
        getPreferences(context).edit().remove(COOKIE_KEY).apply()
    }

    // TokenData 저장
    fun saveTokenData(context: Context, tokenData: TokenData) {
        getPreferences(context).edit()
            .putString(ACCESS_TOKEN_KEY, tokenData.accessToken)
            .putInt(USER_ID_KEY, tokenData.userId)
            .apply()
    }

    // TokenData 불러오기
    fun loadTokenData(context: Context): TokenData? {
        val sharedPref = getPreferences(context)
        val accessToken = sharedPref.getString(ACCESS_TOKEN_KEY, null)
        val userId = sharedPref.getInt(USER_ID_KEY, -1)

        return if (accessToken != null && userId != -1) {
            TokenData(accessToken, userId)
        } else {
            null
        }
    }

    // TokenData 삭제 (로그아웃 시 사용)
    fun clearTokenData(context: Context) {
        getPreferences(context).edit()
            .remove(ACCESS_TOKEN_KEY)
            .remove(USER_ID_KEY)
            .apply()
    }
}
