package com.neurosky.zonetrainer.ui.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.futured.donut.compose.DonutProgress
import app.futured.donut.compose.data.DonutModel
import app.futured.donut.compose.data.DonutSection

@Composable
fun NeuroDonut(
    title: String,
    value: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    var hasOpacity by remember { mutableStateOf(true) }

    val surfaceAlpha: Float by animateFloatAsState(
        targetValue = if (hasOpacity) 1f else 0.1f
    )

    Surface(
        modifier = modifier
            .then(Modifier.aspectRatio(3 / 4f))
            .clip(shape = RoundedCornerShape(24.dp))
            .clickable { hasOpacity = hasOpacity.not() },
        color = MaterialTheme.colorScheme.background.copy(alpha = surfaceAlpha)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 32.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
            Spacer(Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center
            ) {
                DonutProgress(
                    modifier = Modifier.size(92.dp),
                    model = DonutModel(
                        cap = 100f,
                        masterProgress = 1f,
                        gapWidthDegrees = 45f,
                        gapAngleDegrees = 90f,
                        strokeWidth = 40f,
                        backgroundLineColor = Color.LightGray,
                        sections = listOf(
                            DonutSection(amount = value, color = color)
                        )
                    )
                )
                Text(
                    text = "${value.toInt()}",
                    style = MaterialTheme.typography.titleMedium,
                    color = color
                )
            }
        }
    }
}
