package com.evg.tests_list.presentation.mapper

import com.evg.utils.model.TestIcons
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.presentation.model.TestState
import com.evg.utils.extensions.toTestLevel

fun TestType.toTestState(): TestState {
    return when (this) {
        is TestType.OnReadyTestType -> TestState.FinishedTest(
            id = this.id,
            icon = this.type.toTestIcons(),
            title = this.title,
            description = this.description,
            levelColor = this.score.toTestLevel(),
            createdAt = this.createdAt,
        )
        is TestType.OnLoadingTestType -> TestState.LoadingTest(
            id = this.id,
            icon = this.type.toTestIcons(),
            queue = this.queue,
            progress = this.progress,
            createdAt = this.createdAt,
        )
        is TestType.OnErrorTestType -> TestState.ErrorTest(
            id = this.id,
            createdAt = this.createdAt,
        )
    }
}

fun String.toTestIcons(): TestIcons {
    return when (this) {
        "essay" -> TestIcons.ESSAY
        else -> TestIcons.UNKNOWN
    }
}