package com.neurosky.zonetrainer.ui.neuro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NeuroScreen(viewModel: NeuroViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    NeuroScreen(uiState = uiState)
}

@Composable
fun NeuroScreen(
    uiState: NeuroUiState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is NeuroUiState.Connected -> {
                if (uiState != NeuroUiState.Connected.INIT) {
                    NeuroContent(
                        attention = uiState.attention,
                        meditation = uiState.meditation
                    )
                } else {
                    Loading()
                }
            }
            NeuroUiState.Connecting -> {
                Connecting()
            }
            NeuroUiState.Unconnected -> {
                Unconnected()
            }
        }
    }
}

@Composable
fun Loading() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(24.dp))
        Text("MindWave에서 데이터를 불러오는 중입니다...")
    }

}

@Composable
fun Connecting() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(24.dp))
        Text("주변에 연결 가능한 MindWave를 검색 중입니다...")
    }
}

@Composable
fun Unconnected() {
    Text("MindWave에 연결할 수 없습니다\n블루투스 연결 상태를 확인해주세요")
}

