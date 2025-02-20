package com.roadjourney.Home

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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

    @POST("goals/{goalId}/sub-goals/{subGoalId}/complete")
    suspend fun completeSubGoal(
        @Path("goalId") goalId: Long,
        @Path("subGoalId") subGoalId: Long,
        @Header("Authorization") authToken: String
    ): Response<CompleteSubGoalResponse>

    @POST("goals/{goalId}/complete")
    suspend fun completeGoal(
        @Path("goalId") goalId: Long,
        @Header("Authorization") authToken: String
    ): Response<CompleteSubGoalResponse>

    @POST("goals/{goalId}/fail")
    suspend fun failGoal(
        @Path("goalId") goalId: Long,
        @Header("Authorization") authToken: String
    ): Response<CompleteSubGoalResponse>
}
