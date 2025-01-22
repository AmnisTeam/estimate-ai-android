package com.evg.utils.model

import com.evg.utils.extensions.toTestLevel


data class TestScore(
    private val scoreInit: Int
) {
    val score: Int = scoreInit.coerceIn(0, 100)
    val level: TestLevelColors = score.toTestLevel()
}