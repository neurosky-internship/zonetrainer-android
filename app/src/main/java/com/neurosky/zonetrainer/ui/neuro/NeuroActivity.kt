package com.neurosky.zonetrainer.ui.neuro

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.neurosky.zonetrainer.ui.theme.NeuroTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NeuroActivity : ComponentActivity() {

    private val viewModel: NeuroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBluetoothAdapter()

        setContent {
            NeuroTheme {
                NeuroScreen(
                    viewModel = viewModel,
                    closeActivity = ::finish
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeTgStreamReader()
    }

    private fun getBluetoothAdapter() {
        val bluetoothManager =
            this@NeuroActivity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled) {
            viewModel.onBluetoothEnabled(bluetoothAdapter)
        } else {
            viewModel.onBluetoothDisabled()
        }
    }

    companion object {
        fun startActivity(context: Context) {
            val intent = Intent(context, NeuroActivity::class.java)
            context.startActivity(intent)
        }
    }
}
