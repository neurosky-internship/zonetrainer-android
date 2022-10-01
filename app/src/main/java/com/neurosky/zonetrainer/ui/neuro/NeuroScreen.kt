package com.neurosky.zonetrainer.ui.neuro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BluetoothDisabled
import androidx.compose.material.icons.rounded.HeadsetOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.component.ArcRotationAnimation
import com.neurosky.zonetrainer.ui.component.ConnectingChip
import com.neurosky.zonetrainer.ui.component.ThreeBounceAnimation
import com.neurosky.zonetrainer.ui.model.ConnectingState
import com.neurosky.zonetrainer.ui.theme.Black
import com.neurosky.zonetrainer.ui.theme.White

@Composable
fun NeuroScreen(
    viewModel: NeuroViewModel,
    closeActivity: () -> Unit,
    onRetry: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    NeuroScreen(
        uiState = uiState,
        closeActivity = closeActivity,
        onRetry = onRetry
    )
}

@Composable
fun NeuroScreen(
    uiState: NeuroUiState,
    closeActivity: () -> Unit,
    onRetry: () -> Unit
) {
    when (uiState) {
        is NeuroUiState.Connected -> {
            if (uiState != NeuroUiState.Connected.INIT) {
                NeuroContent(
                    attention = uiState.attention,
                    meditation = uiState.meditation
                )
            } else {
                ConnectingScreen(
                    state = ConnectingState.Optimizing,
                    onClickAction = closeActivity
                )
            }
        }
        NeuroUiState.Connecting -> {
            ConnectingScreen(
                state = ConnectingState.Searching,
                onClickAction = closeActivity
            )
        }
        NeuroUiState.Disconnected -> {
            ConnectingScreen(
                state = ConnectingState.Disconnected,
                onClickAction = closeActivity
            )
        }
        NeuroUiState.Disabled -> {
            ConnectingScreen(
                state = ConnectingState.Disabled,
                onClickAction = onRetry
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectingScreen(
    state: ConnectingState,
    onClickAction: () -> Unit,
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.analysis),
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                actions = {
                    ConnectingChip(
                        state = state,
                        modifier = Modifier.padding(top = 92.dp, end = 12.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(vertical = 12.dp, horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (state) {
                            ConnectingState.Searching -> {
                                ThreeBounceAnimation(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            ConnectingState.Optimizing -> {
                                ArcRotationAnimation(
                                    circleColor = White,
                                    arcColor = MaterialTheme.colorScheme.primary
                                )
                            }
                            ConnectingState.Disconnected -> {
                                Icon(
                                    imageVector = Icons.Rounded.HeadsetOff,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            ConnectingState.Disabled -> {
                                Icon(
                                    imageVector = Icons.Rounded.BluetoothDisabled,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Text(
                            text = stringResource(id = state.displayStringRes),
                            color = Black,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.weight(1.1f))
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = onClickAction,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = White,
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(text = stringResource(id = state.actionStringRes))
                            }
                        }
                    }
                }
            }
        }
    }
}
