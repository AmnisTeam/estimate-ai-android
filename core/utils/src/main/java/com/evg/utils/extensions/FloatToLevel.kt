package com.evg.utils.extensions

import com.evg.utils.model.TestLevelColors

fun Number.toTestLevel(): TestLevelColors {
    val value = this.toFloat()
    return when (value) {
        in 0f..16.67f -> TestLevelColors.A1
        in 16.67f..33.33f -> TestLevelColors.A2
        in 33.33f..50f -> TestLevelColors.B1
        in 50f..66.67f -> TestLevelColors.B2
        in 66.67f..83.33f -> TestLevelColors.C1
        in 83.33f..100f -> TestLevelColors.C2
        else -> TestLevelColors.UNKNOWN
    }
    /*return when (value) {
        in 0..20 -> TestLevelColors.A1
        in 20..36 -> TestLevelColors.A2
        in 36..52 -> TestLevelColors.B1
        in 52..68 -> TestLevelColors.B2
        in 68..84 -> TestLevelColors.C1
        in 84..100 -> TestLevelColors.C2
        else -> TestLevelColors.UNKNOWN
    }*/
}