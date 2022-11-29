package com.neurosky.zonetrainer.data.remote.model

data class RecentDataResponse(
    val attention: Int,
    val meditation: Int,
    val date: String?,
    val updateAt: String?
)
