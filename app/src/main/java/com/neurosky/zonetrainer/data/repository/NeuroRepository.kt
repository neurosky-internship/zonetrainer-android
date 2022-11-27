package com.neurosky.zonetrainer.data.repository

import com.neurosky.zonetrainer.data.remote.api.NeuroService
import com.neurosky.zonetrainer.data.remote.model.NeuroRequest
import com.neurosky.zonetrainer.ui.home.HomeData
import com.neurosky.zonetrainer.ui.model.NeuroType
import com.patrykandpatryk.vico.core.entry.FloatEntry
import javax.inject.Inject

class NeuroRepository @Inject constructor(
    private val service: NeuroService
) {

    suspend fun getHomeData(userId: String): Result<HomeData> = runCatching {
        val recentDataResponse = service.getRecentData(userId)
        val graphDataResponse = service.getGraphData(userId)

        HomeData(
            recentAttention = HomeData.RecentData(
                type = NeuroType.Attention,
                value = recentDataResponse.attention,
                datetime = recentDataResponse.date ?: ""
            ),
            recentMeditation = HomeData.RecentData(
                type = NeuroType.Meditation,
                value = recentDataResponse.meditation,
                datetime = recentDataResponse.date ?: ""
            ),
            attentionChart = HomeData.ChartData(
                type = NeuroType.Attention,
                maxEntryModel = List(7) { graphDataResponse.attentionData[it].maxData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                },
                avgEntryModel = List(7) { graphDataResponse.attentionData[it].avgData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                },
                minEntryModel = List(7) { graphDataResponse.attentionData[it].minData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                }
            ),
            meditationChart = HomeData.ChartData(
                type = NeuroType.Meditation,
                maxEntryModel = List(7) { graphDataResponse.meditationData[it].maxData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                },
                avgEntryModel = List(7) { graphDataResponse.meditationData[it].avgData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                },
                minEntryModel = List(7) { graphDataResponse.meditationData[it].minData }.mapIndexed { x, y ->
                    FloatEntry(
                        x = x.toFloat(),
                        y = y
                    )
                }
            )
        )
    }

    suspend fun postNeuroData(
        userId: String,
        attentionData: List<NeuroRequest.NeuroData>,
        meditationData: List<NeuroRequest.NeuroData>
    ): Result<Unit> =
        runCatching {
            service.postNeuroData(
                NeuroRequest(
                    userId = userId,
                    attentionData = attentionData,
                    meditationData = meditationData
                )
            )
        }
}
