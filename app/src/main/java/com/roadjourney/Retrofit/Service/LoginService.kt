package com.roadjourney.Retrofit.Service


import com.roadjourney.Login.Model.LoginData
import com.roadjourney.Login.Model.LoginResponseData
import com.roadjourney.Login.Model.SignupData
import com.roadjourney.Login.Model.SignupResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("auth/login")
    fun postLoginData(
        @Body loginData: LoginData
    ) : Call<LoginResponseData>

   @POST("auth/signup")
    fun postUser(
        @Body signupData: SignupData
    ) : Call<SignupResponseData>
}