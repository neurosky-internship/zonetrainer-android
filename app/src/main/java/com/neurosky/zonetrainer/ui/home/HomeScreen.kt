package com.neurosky.zonetrainer.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Headset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.component.ContentCard
import com.neurosky.zonetrainer.ui.component.NeuroChart
import com.neurosky.zonetrainer.ui.component.RecentCard
import com.neurosky.zonetrainer.ui.model.GoogleAccount
import com.neurosky.zonetrainer.ui.theme.NeuroBlue
import com.neurosky.zonetrainer.ui.theme.NeuroBlueGrey
import com.neurosky.zonetrainer.ui.theme.NeuroPurple
import com.neurosky.zonetrainer.ui.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToNeuro: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    HomeScreen(
        account = viewModel.googleAccount,
        uiState = uiState,
        navigateToNeuro = navigateToNeuro
    )
}

@Composable
fun HomeScreen(
    account: GoogleAccount,
    uiState: HomeUiState,
    navigateToNeuro: () -> Unit
) {
    when (uiState) {
        HomeUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        HomeUiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(id = R.string.fail_to_load))
            }
        }

        is HomeUiState.Success -> {
            HomeContent(
                account = account,
                recentAttention = uiState.data.recentAttention,
                recentMeditation = uiState.data.recentMeditation,
                attentionChart = uiState.data.attentionChart,
                meditationChart = uiState.data.meditationChart,
                navigateToNeuro = navigateToNeuro
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    account: GoogleAccount,
    recentAttention: HomeData.RecentData,
    recentMeditation: HomeData.RecentData,
    attentionChart: HomeData.ChartData,
    meditationChart: HomeData.ChartData,
    navigateToNeuro: () -> Unit
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
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = NeuroBlueGrey),
                actions = {
                    AsyncImage(
                        model = account.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(top = 92.dp, end = 12.dp)
                            .clip(shape = CircleShape)
                            .size(36.dp)
                            .clickable { }
                    )
                }
            )
        },
        containerColor = NeuroBlueGrey,
        floatingActionButton = {
            Button(
                onClick = navigateToNeuro,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = NeuroBlue,
                    contentColor = White
                ),
                contentPadding = PaddingValues(vertical = 12.dp),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .shadow(
                        ambientColor = NeuroBlue,
                        spotColor = NeuroBlue,
                        elevation = 8.dp,
                        shape = RoundedCornerShape(CornerSize(48.dp)),
                        clip = true
                    )
            ) {
                Row {
                    Icon(imageVector = Icons.Rounded.Headset, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.start_training),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 0.dp, vertical = 24.dp)
        ) {
            ContentCard {
                Text(
                    text = stringResource(id = R.string.recently),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp, bottom = 18.dp)
                )
                Spacer(Modifier.height(12.dp))
                Row {
                    RecentCard(
                        data = recentAttention,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.width(12.dp))
                    RecentCard(
                        data = recentMeditation,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(Modifier.height(28.dp))
            ContentCard {
                NeuroChart(data = attentionChart, color = NeuroPurple)
            }
            Spacer(Modifier.height(28.dp))
            ContentCard {
                NeuroChart(data = meditationChart, color = NeuroBlue)
            }
            Spacer(Modifier.height(28.dp))
        }
    }
}
