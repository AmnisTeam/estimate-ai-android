package com.evg.tests_list.domain.mapper

import com.evg.api.domain.model.TestResponse
import com.evg.tests_list.domain.model.TestType

fun TestResponse.toTestType(): TestType {
    return when (this) {
        is TestResponse.OnReadyTestResponse -> {
            TestType.OnReadyTestType(
                id = this.id,
                title = this.title,
                type = this.type,
                description = this.description,
                level = this.level,
            )
        }
        is TestResponse.OnLoadingTestResponse -> {
            TestType.OnLoadingTestType(
                id = this.id,
                type = this.type,
                queue = this.queue,
                progress = this.progress,
            )
        }
        is TestResponse.OnErrorTestResponse -> {
            TestType.OnErrorTestType(
                id = this.id,
            )
        }
    }
}
