package com.neurosky.zonetrainer.ui.model

import androidx.annotation.StringRes
import com.neurosky.zonetrainer.R

enum class ChartType(
    @StringRes stringRes: Int
) {
    Avg(R.string.avg),
    Max(R.string.max),
    Min(R.string.min)
}
