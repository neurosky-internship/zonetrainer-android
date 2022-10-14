package com.neurosky.zonetrainer.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.ui.theme.NeuroBlueLight
import com.neurosky.zonetrainer.ui.theme.White

@Composable
fun ContentCard(
    contentVerticalPadding: Dp = 24.dp,
    contentHorizontalPadding: Dp = 24.dp,
    modifier: Modifier = Modifier,
    content: @Composable (ColumnScope.() -> Unit)
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = White),
        modifier = modifier.shadow(
            ambientColor = NeuroBlueLight,
            spotColor = NeuroBlueLight,
            elevation = 12.dp,
            shape = RoundedCornerShape(CornerSize(24.dp)),
            clip = true
        )
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = contentHorizontalPadding,
                    vertical = contentVerticalPadding
                ),
            content = content
        )
    }
}
