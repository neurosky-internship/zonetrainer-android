package com.neurosky.zonetrainer.data.remote.model

data class GraphDataResponse(
    val attentionData: List<GraphData>,
    val meditationData: List<GraphData>
) {
    data class GraphData(
        val avgData: Float,
        val minData: Float,
        val maxData: Float,
        val date: String
    )
}
