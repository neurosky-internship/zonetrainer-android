package com.neurosky.zonetrainer.ui.component

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ArcRotationAnimation(
    circleColor: Color,
    arcColor: Color
) {
    val infiniteTransition = rememberInfiniteTransition()

    val arcAngle1 by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 180F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val arcAngle2 by infiniteTransition.animateFloat(
        initialValue = 180F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val greenCircleAnimation by infiniteTransition.animateFloat(
        initialValue = 25f,
        targetValue = 40f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, delayMillis = 100, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Canvas(
        modifier = Modifier.size(50.dp)
    ) {
        drawArc(
            color = arcColor,
            startAngle = arcAngle1,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 5f, cap = StrokeCap.Round),
        )

        drawArc(
            color = arcColor,
            startAngle = arcAngle2,
            sweepAngle = 90f,
            useCenter = false,
            style = Stroke(width = 5f, cap = StrokeCap.Round),
        )

        drawCircle(
            color = arcColor,
            radius = 60f,
        )

        drawCircle(
            color = circleColor,
            radius = greenCircleAnimation,
        )
    }
}
