package com.neurosky.zonetrainer.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorScheme = lightColorScheme(
    primary = NeuroBlue,
    onPrimary = White,
    primaryContainer = NeuroBlue.copy(alpha = 0.12f),
    onPrimaryContainer = NeuroBlue,
    secondary = NeuroPurple,
    onSecondary = White,
    secondaryContainer = NeuroPurple.copy(alpha = 0.12f),
    onSecondaryContainer = NeuroPurple,
    tertiary = NeuroRed,
    onTertiary = White,
    tertiaryContainer = NeuroRed.copy(alpha = 0.12f),
    onTertiaryContainer = NeuroRed,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

@Composable
fun NeuroTheme(
    systemBarsColor: Color = White,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = systemBarsColor,
            darkIcons = true,
            isNavigationBarContrastEnforced = false,
        )
    }
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
