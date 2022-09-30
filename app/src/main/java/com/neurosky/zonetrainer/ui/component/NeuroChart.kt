package com.neurosky.zonetrainer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.neurosky.zonetrainer.ui.home.HomeData
import com.neurosky.zonetrainer.ui.model.ChartType
import com.neurosky.zonetrainer.util.marker
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.compose.style.ChartStyle
import com.patrykandpatryk.vico.compose.style.ProvideChartStyle
import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun NeuroChart(
    data: HomeData.ChartData,
    color: Color,
    modifier: Modifier = Modifier
) {
    val chartModelProducer by remember { mutableStateOf(ChartEntryModelProducer(data.avgEntryModel)) }

    val startAxis = startAxis(
        valueFormatter = { value, _ ->
            value.roundToInt().toString()
        },
    )
    val bottomAxis = bottomAxis(
        guideline = null,
        valueFormatter = { value, _ ->
            LocalDateTime.now().minusDays(6 - value.toLong()).format(
                DateTimeFormatter.ofPattern("d")
            )
        }
    )
    val chartStyle = ChartStyle.fromColors(
        axisGuidelineColor = Color.LightGray,
        axisLabelColor = Color.LightGray,
        axisLineColor = Color.LightGray,
        entityColors = listOf(color.copy(alpha = 0.5f)),
        elevationOverlayColor = color
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = data.type.stringRes),
                style = MaterialTheme.typography.titleLarge
            )
            NeuroDropdown(
                color = color,
                initialItem = ChartType.Avg.name,
                items = listOf(ChartType.Max.name, ChartType.Avg.name, ChartType.Min.name),
                onSelectItem = {
                    when (it) {
                        ChartType.Max.name -> {
                            chartModelProducer.setEntries(data.maxEntryModel)
                        }
                        ChartType.Avg.name -> {
                            chartModelProducer.setEntries(data.avgEntryModel)
                        }
                        ChartType.Min.name -> {
                            chartModelProducer.setEntries(data.minEntryModel)
                        }
                    }
                }
            )
        }
        ProvideChartStyle(chartStyle = chartStyle) {
            val lineChart =
                lineChart(minY = 0f, maxY = 100f, persistentMarkers = mapOf(6f to marker()))
            Chart(
                chart = lineChart,
                chartModelProducer = chartModelProducer,
                startAxis = startAxis,
                bottomAxis = bottomAxis,
                marker = marker()
            )
        }
    }
}
