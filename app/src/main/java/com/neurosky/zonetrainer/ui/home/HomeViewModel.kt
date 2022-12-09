package com.neurosky.zonetrainer.ui.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.neurosky.zonetrainer.data.repository.NeuroRepository
import com.neurosky.zonetrainer.ui.base.BaseViewModel
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val repository: NeuroRepository
) : BaseViewModel() {

    val googleAccount = handle.get<GoogleAccount>(KEY_GOOGLE_ACCOUNT)!!

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            repository.getHomeData(googleAccount.id)
                .onSuccess { _uiState.value = HomeUiState.Success(it) }
                .onFailure {
                    Log.e("error", it.stackTraceToString())
                    _uiState.value = HomeUiState.Failure
                }
        }
    }

    companion object {
        const val KEY_GOOGLE_ACCOUNT = "KEY_GOOGLE_ACCOUNT"
    }
}
