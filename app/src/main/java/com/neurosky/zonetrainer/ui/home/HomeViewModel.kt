package com.neurosky.zonetrainer.ui.home

import androidx.lifecycle.SavedStateHandle
import com.neurosky.zonetrainer.ui.base.BaseViewModel
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.model.NeuroType
import com.patrykandpatryk.vico.core.entry.FloatEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    handle: SavedStateHandle
) : BaseViewModel() {

    val googleAccount = handle.get<GoogleAccount>(KEY_GOOGLE_ACCOUNT)!!

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = HomeUiState.Success(
            data = HomeData(
                recentAttention = HomeData.RecentData(
                    type = NeuroType.Attention,
                    value = 51,
                    datetime = LocalDateTime.now()
                ),
                recentMeditation = HomeData.RecentData(
                    type = NeuroType.Meditation,
                    value = 49,
                    datetime = LocalDateTime.now()
                ),
                attentionChart = HomeData.ChartData(
                    type = NeuroType.Attention,
                    maxEntryModel = getRandomEntries(50, 100),
                    avgEntryModel = getRandomEntries(25, 75),
                    minEntryModel = getRandomEntries(0, 50)
                ),
                meditationChart = HomeData.ChartData(
                    type = NeuroType.Meditation,
                    maxEntryModel = getRandomEntries(50, 100),
                    avgEntryModel = getRandomEntries(25, 75),
                    minEntryModel = getRandomEntries(0, 50)
                )
            )
        )
    }

    private fun getRandomEntries(from: Int, until: Int) = List(size = 7) {
        Random.nextInt(from, until).toFloat()
    }.mapIndexed { x, y ->
        FloatEntry(
            x = x.toFloat(),
            y = y,
        )
    }

    companion object {
        const val KEY_GOOGLE_ACCOUNT = "KEY_GOOGLE_ACCOUNT"
    }
}
