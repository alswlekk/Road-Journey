package com.roadjourney.Home

data class GoalItem(
    val goalId: String,
    val goalName: String,
    val dDay: String,
    val progressText: String,
    val progressValue: Int,
    val priority: Int
)
