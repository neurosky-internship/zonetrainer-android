package com.neurosky.zonetrainer.data.remote.model

data class NeuroRequest(
    val userId: String,
    val attentionData: List<AttentionData>,
    val meditationData: List<MeditationData>
) {

    data class AttentionData(
        val timestamp: String,
        val attention: Int
    )

    data class MeditationData(
        val timestamp: String,
        val meditation: Int
    )
}
