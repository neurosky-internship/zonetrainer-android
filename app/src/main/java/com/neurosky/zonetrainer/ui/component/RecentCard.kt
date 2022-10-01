package com.neurosky.zonetrainer.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.R
import com.neurosky.zonetrainer.ui.home.HomeData
import com.neurosky.zonetrainer.ui.theme.Grey
import com.neurosky.zonetrainer.ui.theme.NeuroGreen
import com.neurosky.zonetrainer.ui.theme.NeuroRed
import com.neurosky.zonetrainer.ui.theme.White
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun RecentCard(
    data: HomeData.RecentData,
    boundaryValue: Int = 50,
    modifier: Modifier = Modifier
) {
    val isGood = data.value >= boundaryValue
    val color = if (isGood) NeuroGreen else NeuroRed

    Surface(
        modifier = modifier.then(Modifier.aspectRatio(1 / 1.15f)),
        shape = RoundedCornerShape(24.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = data.type.stringRes),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${stringResource(id = R.string.today_at)} ${
                    data.datetime.format(
                        DateTimeFormatter.ofPattern(
                            stringResource(id = R.string.recent_time_fmt),
                            Locale.ENGLISH
                        )
                    )
                }",
                color = Grey,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = White,
                    shape = RoundedCornerShape(48.dp),
                    modifier = Modifier
                        .width(72.dp)
                        .height(32.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = if (isGood) R.string.good else R.string.bad),
                            modifier = Modifier.align(Alignment.Center),
                            color = color,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
