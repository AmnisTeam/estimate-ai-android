package com.evg.utils.extensions

import com.evg.utils.model.TestLevelColors

fun Number.toTestLevel(): TestLevelColors {
    val value = this.toFloat()
    return when (value) {
        in 0f..30f -> TestLevelColors.A1
        in 30f..50f -> TestLevelColors.A2
        in 50f..66f -> TestLevelColors.B1
        in 67f..74f -> TestLevelColors.B2
        in 75f..90f -> TestLevelColors.C1
        in 91f..100f -> TestLevelColors.C2
        else -> TestLevelColors.UNKNOWN
    }
}