package com.neurosky.zonetrainer.ui.model

import androidx.annotation.StringRes
import com.neurosky.zonetrainer.R

enum class ConnectingState(
    @StringRes val displayStringRes: Int,
    @StringRes val actionStringRes: Int
) {
    Searching(
        displayStringRes = R.string.searching_msg,
        actionStringRes = R.string.cancel
    ),
    Optimizing(
        displayStringRes = R.string.optimizing_msg,
        actionStringRes = R.string.cancel
    ),
    Disconnected(
        displayStringRes = R.string.disconnected_msg,
        actionStringRes = R.string.back
    ),
    Disabled(
        displayStringRes = R.string.disabled_msg,
        actionStringRes = R.string.retry
    )
}
