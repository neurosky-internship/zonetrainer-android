package com.neurosky.zonetrainer.ui.home

import com.neurosky.zonetrainer.ui.model.NeuroType
import com.patrykandpatryk.vico.core.entry.ChartEntry
import java.time.LocalDateTime

data class HomeData(
    val recentAttention: RecentData,
    val recentMeditation: RecentData,
    val attentionChart: ChartData,
    val meditationChart: ChartData
) {
    data class RecentData(
        val type: NeuroType,
        val value: Int,
        val datetime: LocalDateTime
    )

    data class ChartData(
        val type: NeuroType,
        val maxEntryModel: List<ChartEntry>,
        val avgEntryModel: List<ChartEntry>,
        val minEntryModel: List<ChartEntry>
    )
}
