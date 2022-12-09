package com.neurosky.zonetrainer.ui.home

import com.neurosky.zonetrainer.ui.model.NeuroType
import com.patrykandpatryk.vico.core.entry.ChartEntry

data class HomeData(
    val recentAttention: RecentData,
    val recentMeditation: RecentData,
    val attentionChart: ChartData,
    val meditationChart: ChartData
) {

    data class RecentData(
        val type: NeuroType,
        val value: Int,
        val datetime: String
    ) {

        val status: Status = when (value) {
            in 0 until boundaryValue -> Status.Bad
            in boundaryValue..100 -> Status.Good
            else -> Status.None
        }

        companion object {
            const val boundaryValue: Int = 50

            enum class Status(
                val string: String
            ) {
                Good("GOOD"),
                Bad("BAD"),
                None("NONE")
            }
        }
    }

    data class ChartData(
        val type: NeuroType,
        val maxEntryModel: List<ChartEntry>,
        val avgEntryModel: List<ChartEntry>,
        val minEntryModel: List<ChartEntry>
    )
}
