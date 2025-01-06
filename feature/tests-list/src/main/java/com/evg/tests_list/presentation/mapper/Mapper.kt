package com.evg.tests_list.presentation.mapper

import com.evg.model.TestIcons
import com.evg.model.TestLevelColors
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.model.TestState

fun TestType.toTestState(): TestState {
    return when (this) {
        is TestType.OnReadyTestType -> TestState.FinishedTest(
            id = this.id,
            icon = this.type.toTestIcons(),
            title = this.title,
            description = this.description,
            levelColor = this.level.toTestLevelColors(),
        )
        is TestType.OnLoadingTestType -> TestState.LoadingTest(
            id = this.id,
            icon = this.type.toTestIcons(),
            queue = this.queue,
            progress = this.progress,
        )
        is TestType.OnErrorTestType -> TestState.ErrorTest(
            id = this.id,
        )
    }
}

fun String.toTestIcons(): TestIcons {
    return when (this) {
        "essay" -> TestIcons.ESSAY
        else -> TestIcons.UNKNOWN
    }
}

fun String.toTestLevelColors(): TestLevelColors {
    return when (this) {
        "A1" -> TestLevelColors.A1
        "A2" -> TestLevelColors.A2
        "B1" -> TestLevelColors.B1
        "B2" -> TestLevelColors.B2
        "C1" -> TestLevelColors.C1
        "C2" -> TestLevelColors.C2
        else -> TestLevelColors.UNKNOWN
    }
}