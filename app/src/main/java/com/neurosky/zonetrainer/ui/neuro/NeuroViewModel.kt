package com.neurosky.zonetrainer.ui.neuro

import android.bluetooth.BluetoothAdapter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neurosky.connection.TgStreamReader
import com.neurosky.zonetrainer.data.remote.model.NeuroRequest
import com.neurosky.zonetrainer.data.repository.NeuroRepository
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.util.TgStreamHandlerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NeuroViewModel @Inject constructor(
    handle: SavedStateHandle,
    private val repository: NeuroRepository
) : ViewModel() {

    val googleAccount = handle.get<GoogleAccount>(KEY_GOOGLE_ACCOUNT)!!

    private val _uiState = MutableStateFlow<NeuroUiState>(NeuroUiState.Connecting)
    val uiState: StateFlow<NeuroUiState> = _uiState.asStateFlow()

    private var tgStreamReader: TgStreamReader? = null

    var isRecording by mutableStateOf(false)

    val attentionLogData: List<NeuroRequest.NeuroData> = emptyList()
    val meditationLogData: List<NeuroRequest.NeuroData> = emptyList()

    fun onBluetoothEnabled(bluetoothAdapter: BluetoothAdapter) {
        _uiState.value = NeuroUiState.Connecting
        tgStreamReader = TgStreamReader(
            bluetoothAdapter,
            TgStreamHandlerImpl(
                onConnected = {
                    _uiState.value = NeuroUiState.Connected.INIT
                    tgStreamReader!!.start()
                },
                onWorking = {
                    tgStreamReader!!.startRecordRawData()
                },
                onTimeout = {
                    tgStreamReader!!.stopRecordRawData()
                    _uiState.value = NeuroUiState.Disconnected
                },
                onAttentionReceived = { attention ->
                    _uiState.update { (it as NeuroUiState.Connected).copy(attention = attention) }
                },
                onMeditationReceived = { meditation ->
                    _uiState.update { (it as NeuroUiState.Connected).copy(meditation = meditation) }
                }
            )
        )

        tgStreamReader!!.setGetDataTimeOutTime(3)
        tgStreamReader!!.startLog()

        if (tgStreamReader!!.isBTConnected) {
            closeTgStreamReader()
        }

        tgStreamReader!!.connect()
    }

    fun onBluetoothDisabled() {
        _uiState.value = NeuroUiState.Disabled
    }

    fun closeTgStreamReader() {
        if (tgStreamReader != null) {
            tgStreamReader!!.stop()
            tgStreamReader!!.close()
        }
    }

    fun postLogData() {
        viewModelScope.launch {
            repository.postNeuroData(
                userId = googleAccount.id,
                attentionData = attentionLogData,
                meditationData = meditationLogData
            )
        }
    }

    companion object {
        private const val KEY_GOOGLE_ACCOUNT = "KEY_GOOGLE_ACCOUNT"
    }
}
