package com.evg.ui.extensions

import com.evg.model.TestLevelColors

fun Float.toTestLevel(): TestLevelColors {
    return when (this) {
        in 0f..16.67f -> TestLevelColors.A1
        in 16.67f..33.33f -> TestLevelColors.A2
        in 33.33f..50f -> TestLevelColors.B1
        in 50f..66.67f -> TestLevelColors.B2
        in 66.67f..83.33f -> TestLevelColors.C1
        in 83.33f..100f -> TestLevelColors.C2
        else -> TestLevelColors.UNKNOWN
    }
}