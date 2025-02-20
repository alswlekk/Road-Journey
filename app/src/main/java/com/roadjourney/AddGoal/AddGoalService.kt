package com.roadjourney.AddGoal

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AddGoalService {
    @POST("/goals")
    fun createGoal(
        @Header("Authorization") token: String,
        @Body goalRequest: GoalRequest
    ): Call<GoalResponse>
}
