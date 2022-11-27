package com.neurosky.zonetrainer.ui.neuro

import android.bluetooth.BluetoothAdapter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.neurosky.connection.TgStreamReader
import com.neurosky.zonetrainer.ui.util.TgStreamHandlerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NeuroViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<NeuroUiState>(NeuroUiState.Connecting)
    val uiState: StateFlow<NeuroUiState> = _uiState.asStateFlow()

    private var tgStreamReader: TgStreamReader? = null

    var isRecording by mutableStateOf(false)

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
}
