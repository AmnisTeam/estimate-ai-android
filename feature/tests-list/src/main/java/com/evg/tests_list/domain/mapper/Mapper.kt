package com.evg.tests_list.domain.mapper

import com.evg.api.domain.model.TestResponse
import com.evg.database.domain.model.ErrorTestTypeDBO
import com.evg.database.domain.model.LoadingTestTypeDBO
import com.evg.database.domain.model.ReadyTestTypeDBO
import com.evg.database.domain.model.TestTypeDBO
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

fun TestTypeDBO.toTestType(): TestType {
    return when {
        readyTestTypeDBO != null -> {
            readyTestTypeDBO?.let {
                TestType.OnReadyTestType(
                    id = this.id,
                    title = it.title,
                    type = it.type,
                    description = it.description,
                    level = it.level
                )
            } ?: throw IllegalArgumentException("ReadyTestTypeDBO is null")
        }
        loadingTestTypeDBO != null -> {
            loadingTestTypeDBO?.let {
                TestType.OnLoadingTestType(
                    id = this.id,
                    type = it.type,
                    queue = it.queue,
                    progress = it.progress
                )
            } ?: throw IllegalArgumentException("LoadingTestTypeDBO is null")
        }
        errorTestTypeDBO != null -> {
            errorTestTypeDBO?.let {
                TestType.OnErrorTestType(
                    id = this.id
                )
            } ?: throw IllegalArgumentException("ErrorTestTypeDBO is null")
        }
        else -> throw IllegalArgumentException("Unknown TestTypeDBO")
    }
}