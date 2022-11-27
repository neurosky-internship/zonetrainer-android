package com.neurosky.zonetrainer.ui.util

import android.util.Log
import com.neurosky.connection.ConnectionStates
import com.neurosky.connection.DataType.MindDataType
import com.neurosky.connection.TgStreamHandler

class TgStreamHandlerImpl(
    private val onConnected: () -> Unit,
    private val onWorking: () -> Unit,
    private val onTimeout: () -> Unit,
    private val onAttentionReceived: (Int) -> Unit,
    private val onMeditationReceived: (Int) -> Unit
) : TgStreamHandler {

    override fun onStatesChanged(connectionStates: Int) {
        when (connectionStates) {
            ConnectionStates.STATE_CONNECTING -> {
                Log.d("TgStreamConnectionState", "State Connecting")
            }
            ConnectionStates.STATE_CONNECTED -> {
                Log.d("TgStreamConnectionState", "State Connected")
                onConnected()
            }
            ConnectionStates.STATE_WORKING -> {
                Log.d("TgStreamConnectionState", "State Working")
                onWorking()
            }
            ConnectionStates.STATE_GET_DATA_TIME_OUT -> {
                Log.d("TgStreamConnectionState", "State Get Data Timeout")
                onTimeout()
            }
            ConnectionStates.STATE_STOPPED -> {
                Log.d("TgStreamConnectionState", "State Stopped")
            }
            ConnectionStates.STATE_DISCONNECTED -> {
                Log.d("TgStreamConnectionState", "State Disconnected")
            }
            ConnectionStates.STATE_ERROR -> {
                Log.d("TgStreamConnectionState", "State Error")
            }
            ConnectionStates.STATE_FAILED -> {
                Log.d("TgStreamConnectionState", "State Failed")
            }
        }
    }

    override fun onDataReceived(dataType: Int, data: Int, obj: Any?) {
        when (dataType) {
            MindDataType.CODE_ATTENTION -> {
                Log.d("MindData", "Attention: $data")
                onAttentionReceived(data)
            }
            MindDataType.CODE_MEDITATION -> {
                Log.d("MindData", "Meditation: $data")
                onMeditationReceived(data)
            }
            else -> {}
        }
    }

    override fun onChecksumFail(payload: ByteArray?, length: Int, checksum: Int) {
        Log.e("Error", "Fail to checksum.")
    }

    override fun onRecordFail(flag: Int) {
        Log.e("Error", "Fail to record.")
    }
}
