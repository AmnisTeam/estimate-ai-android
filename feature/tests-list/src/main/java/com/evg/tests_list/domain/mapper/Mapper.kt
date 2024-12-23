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
                status = this.status,
                level = this.level,
                createdAt = this.createdAt
            )
        }
        is TestResponse.OnLoadingTestResponse -> {
            TestType.OnLoadingTestType(
                id = this.id,
                progress = this.progress,
                queue = this.queue,
                createdAt = this.createdAt
            )
        }
        is TestResponse.OnErrorTestResponse -> {
            TestType.OnErrorTestType(
                id = this.id,
            )
        }
    }
}
