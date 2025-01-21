package com.evg.utils.model

import com.evg.utils.extensions.toTestLevel


data class TestScore(
    val score: Int
) {
    init {
        require(score in 0..100) { "Score must be between 0 and 100" }
    }

    val level: TestLevelColors = score.toTestLevel()
}