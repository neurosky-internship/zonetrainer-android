package com.neurosky.zonetrainer.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.component.NeuroChart
import com.neurosky.zonetrainer.ui.component.RecentCard
import com.neurosky.zonetrainer.ui.theme.NeuroBlue
import com.neurosky.zonetrainer.ui.theme.NeuroPurple

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
    when (uiState) {
        HomeUiState.Loading -> {
            //TODO: Loading Component
        }
        HomeUiState.Failure -> {
            //TODO: Failure Component
        }
        is HomeUiState.Success -> {
            HomeContent(
                recentAttention = uiState.data.recentAttention,
                recentMeditation = uiState.data.recentMeditation,
                attentionChart = uiState.data.attentionChart,
                meditationChart = uiState.data.meditationChart
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    recentAttention: HomeData.RecentData,
    recentMeditation: HomeData.RecentData,
    attentionChart: HomeData.ChartData,
    meditationChart: HomeData.ChartData
) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.recently),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.padding(4.dp)
            ) {
                RecentCard(
                    data = recentAttention,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(18.dp))
                RecentCard(
                    data = recentMeditation,
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(48.dp))
            NeuroChart(data = attentionChart, color = NeuroPurple)
            Spacer(Modifier.height(48.dp))
            NeuroChart(data = meditationChart, color = NeuroBlue)
        }
    }
}
