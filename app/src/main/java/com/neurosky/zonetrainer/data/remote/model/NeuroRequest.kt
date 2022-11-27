package com.neurosky.zonetrainer.data.remote.model

data class NeuroRequest(
    val userId: String,
    val attentionData: List<NeuroData>,
    val meditationData: List<NeuroData>
) {

    data class NeuroData(
        val timestamp: String,
        val value: Int
    )
}
