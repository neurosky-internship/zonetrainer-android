package com.neurosky.zonetrainer.ui.main

sealed interface MainUiState {
    object Unconnected : MainUiState
    object Connecting : MainUiState
    data class Connected(
        val attention: Int,
        val meditation: Int
    ) : MainUiState {

        companion object {
            val INIT = Connected(attention = 0, meditation = 0)
        }
    }
}
