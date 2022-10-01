package com.neurosky.zonetrainer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BluetoothConnected
import androidx.compose.material.icons.rounded.BluetoothDisabled
import androidx.compose.material.icons.rounded.BluetoothSearching
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.model.ConnectingState
import com.neurosky.zonetrainer.ui.theme.NeuroGreen
import com.neurosky.zonetrainer.ui.theme.NeuroPurple
import com.neurosky.zonetrainer.ui.theme.NeuroRed

@Composable
fun ConnectingChip(
    state: ConnectingState,
    modifier: Modifier = Modifier
) {
    val color = when (state) {
        ConnectingState.Searching -> NeuroPurple
        ConnectingState.Optimizing -> NeuroGreen
        ConnectingState.Disconnected -> NeuroRed
        ConnectingState.Disabled -> NeuroRed
    }
    val iconVector = when (state) {
        ConnectingState.Searching -> Icons.Rounded.BluetoothSearching
        ConnectingState.Optimizing -> Icons.Rounded.BluetoothConnected
        ConnectingState.Disconnected -> Icons.Rounded.BluetoothDisabled
        ConnectingState.Disabled -> Icons.Rounded.BluetoothDisabled
    }
    val textRes = when (state) {
        ConnectingState.Searching -> R.string.connecting
        ConnectingState.Optimizing -> R.string.connected
        ConnectingState.Disconnected -> R.string.disconnected
        ConnectingState.Disabled -> R.string.disabled
    }

    Surface(
        shape = RoundedCornerShape(48.dp),
        color = color.copy(alpha = 0.1f),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = iconVector,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(id = textRes),
                color = color,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
