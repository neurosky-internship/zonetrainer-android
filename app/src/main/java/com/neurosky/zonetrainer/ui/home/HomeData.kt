package com.neurosky.zonetrainer.ui.home

import com.neurosky.zonetrainer.ui.model.NeuroType
import java.time.LocalDateTime

data class HomeData(
    val recentAttention: RecentData,
    val recentMeditation: RecentData,
) {
    data class RecentData(
        val type: NeuroType,
        val value: Int,
        val datetime: LocalDateTime
    )
}
