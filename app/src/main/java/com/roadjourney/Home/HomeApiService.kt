package com.roadjourney.Home

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApiService {
    @GET("goals/list/{userId}")
    suspend fun getGoals(
        @Path("userId") userId: Int, 
        @Query("category") category: String,
        @Header("Authorization") authToken: String
    ): Response<GoalApiResponse>

    @GET("goals/{goalId}")
    suspend fun getGoalDetail(
        @Path("goalId") goalId: Int,
        @Header("Authorization") authToken: String
    ): Response<GoalDetailResponse>
}
