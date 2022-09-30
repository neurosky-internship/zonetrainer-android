package com.neurosky.zonetrainer.ui.model

import androidx.annotation.StringRes
import com.neurosky.zonetrainer.R

enum class NeuroType(
    @StringRes val stringRes: Int
) {
    Attention(R.string.attention),
    Meditation(R.string.meditation)
}
