package com.neurosky.zonetrainer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = NeuroBlue,
    onPrimary = White,
    primaryContainer = NeuroBlue.copy(alpha = 0.33f),
    onPrimaryContainer = NeuroBlue,
    secondary = NeuroPurple,
    onSecondary = White,
    secondaryContainer = NeuroPurple.copy(alpha = 0.33f),
    onSecondaryContainer = NeuroPurple,
    tertiary = NeuroRed,
    onTertiary = White,
    tertiaryContainer = NeuroRed.copy(alpha = 0.33f),
    onTertiaryContainer = NeuroRed,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

@Composable
fun NeuroskyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
