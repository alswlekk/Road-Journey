package com.roadjourney.Home

data class GoalApiResponse(
    val code: Int,
    val status: Int,
    val message: String,
    val result: GoalResult
)

data class GoalResult(
    val goalInfoList: List<GoalItem>
)

data class GoalItem(
    val goalId: Int,
    val title: String,
    val difficulty: Double,
    val progressStatus: String,
    val progress: Int,
    val color: String,
    val expireAt: String
)

data class GoalDetailResponse(
    val code: Int,
    val status: Int,
    val message: String,
    val result: GoalDetail
)

data class GoalDetail(
    val goalId: Int,
    val title: String,
    val difficulty: Int,
    val category: String,
    val description: String,
    val progressStatus: String,
    val progress: Int,
    val subGoalType: String,
    val dateInfo: DateInfo,
    val subGoalList: List<SubGoal>
)

data class DateInfo(
    val startAt: String,
    val expireAt: String,
    val periodStartAt: String,
    val periodExpireAt: String,
    val repetitionPeriod: Int,
    val repetitionNumber: Int
)

data class SubGoal(
    val subGoalId: Int,
    val index: Int,
    val difficulty: Int,
    val description: String,
    val progressStatus: String
)

data class CompleteSubGoalResponse(
    val code: Int,
    val status: Int,
    val message: String,
    val result: RewardResult
)

data class RewardResult(
    val gold: Int,
    val growthPoint: Int,
    val reward: Boolean
)

data class MainApiResponse(
    val code: Int,
    val status: Int,
    val message: String,
    val result: MainResult
)

data class MainResult(
    val nickName: String,
    val selectedUserItemList: List<UserItem>
)

data class UserItem(
    val userItemId: Int,
    val itemId: Int,
    val itemName: String,
    val category: String,
    val growthPoint: Int?,
    val growthLevel: Int?,
    val selected: Boolean
)