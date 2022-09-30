package com.neurosky.zonetrainer.ui.home

import com.neurosky.zonetrainer.ui.base.BaseViewModel
import com.neurosky.zonetrainer.ui.model.NeuroType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = HomeUiState.Success(
            data = HomeData(
                recentAttention = HomeData.RecentData(
                    type = NeuroType.Attention,
                    value = (0..100).random(),
                    datetime = LocalDateTime.now()
                ),
                recentMeditation = HomeData.RecentData(
                    type = NeuroType.Meditation,
                    value = (0..100).random(),
                    datetime = LocalDateTime.now()
                )
            )
        )
    }
}
