package com.roadjourney.AddGoal

data class GoalRequest(
    val title: String,
    val difficulty: Float,
    val category: String,
    val description: String,
    val sharedGoal: Boolean,
    val sharedGoalType: String?,
    val publicGoal: Boolean,
    val subGoalType: String,
    val dateInfo: DateInfo,
    val friendList: List<FriendRequest>,
    val subGoalList: List<SubGoal>,
    val repeatedGoal: Boolean
)

data class DateInfo(
    val startAt: String,
    val expireAt: String,
    val repetitionPeriod: Int,
    val repetitionNumber: Int
)

data class FriendRequest(
    val userId: Int
)

data class SubGoal(
    val index: Int,
    val difficulty: Float,
    val description: String
)

data class GoalResponse(
    val code: Int,
    val status: Int,
    val message: String
)
