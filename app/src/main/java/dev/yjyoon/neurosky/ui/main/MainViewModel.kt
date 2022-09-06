package dev.yjyoon.neurosky.ui.main

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.ViewModel
import com.neurosky.connection.TgStreamReader
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.neurosky.util.TgStreamHandlerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Connecting)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private lateinit var tgStreamReader: TgStreamReader

    fun onBluetoothEnabled(bluetoothAdapter: BluetoothAdapter) {
        tgStreamReader = TgStreamReader(
            bluetoothAdapter,
            TgStreamHandlerImpl(
                onConnected = {
                    _uiState.value = MainUiState.Connected.INIT
                    tgStreamReader.start()
                },
                onWorking = {
                    tgStreamReader.startRecordRawData()
                },
                onTimeout = {
                    tgStreamReader.stopRecordRawData()
                    _uiState.value = MainUiState.Unconnected
                },
                onAttentionReceived = { attention ->
                    _uiState.update { (it as MainUiState.Connected).copy(attention = attention) }
                },
                onMeditationReceived = { meditation ->
                    _uiState.update { (it as MainUiState.Connected).copy(meditation = meditation) }
                }
            )
        )

        tgStreamReader.setGetDataTimeOutTime(3)
        tgStreamReader.startLog()

        if (tgStreamReader.isBTConnected) {
            closeTgStreamReader()
        }

        tgStreamReader.connect()
    }

    fun onBluetoothDisabled() {
        _uiState.value = MainUiState.Unconnected
    }

    fun closeTgStreamReader() {
        tgStreamReader.stop()
        tgStreamReader.close()
    }
}
