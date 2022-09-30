package com.neurosky.zonetrainer.ui.home

sealed interface HomeUiState {
    object Loading : HomeUiState
    object Failure : HomeUiState
    data class Success(val data: HomeData) : HomeUiState
}
