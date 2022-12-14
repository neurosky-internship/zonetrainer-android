package com.neurosky.zonetrainer.ui.neuro

sealed interface NeuroUiState {
    object Disabled : NeuroUiState
    object Disconnected : NeuroUiState
    object Connecting : NeuroUiState
    data class Connected(
        val attention: Int,
        val meditation: Int
    ) : NeuroUiState {

        companion object {
            val INIT = Connected(attention = 0, meditation = 0)
        }
    }
}
