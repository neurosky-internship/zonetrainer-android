package com.neurosky.zonetrainer.util

import com.patrykandpatryk.vico.core.entry.ChartEntryModelProducer

fun ChartEntryModelProducer.getMaxX(): Float =
    this.getModel().entries[0].indices.maxBy { this.getModel().entries[0][it].y }.toFloat()