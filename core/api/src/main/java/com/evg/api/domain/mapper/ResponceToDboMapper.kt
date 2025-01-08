package com.evg.api.domain.mapper

import com.evg.api.domain.model.GetTestDataResponse
import com.evg.api.domain.model.TestResponse
import com.evg.database.domain.model.ErrorTestTypeDBO
import com.evg.database.domain.model.EssayTestDBO
import com.evg.database.domain.model.LoadingTestTypeDBO
import com.evg.database.domain.model.ReadyTestTypeDBO
import com.evg.database.domain.model.TestDataDBO
import com.evg.database.domain.model.TestTypeDBO

fun TestResponse.toTestTypeDBO(): TestTypeDBO {
    return TestTypeDBO().apply {
        when (this@toTestTypeDBO) {
            is TestResponse.OnReadyTestResponse -> {
                readyTestTypeDBO = ReadyTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                    title = this@toTestTypeDBO.title
                    type = this@toTestTypeDBO.type
                    description = this@toTestTypeDBO.description
                    level = this@toTestTypeDBO.level
                }
            }
            is TestResponse.OnLoadingTestResponse -> {
                loadingTestTypeDBO = LoadingTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                    type = this@toTestTypeDBO.type
                    queue = this@toTestTypeDBO.queue
                    progress = this@toTestTypeDBO.progress
                }
            }
            is TestResponse.OnErrorTestResponse -> {
                errorTestTypeDBO = ErrorTestTypeDBO().apply {
                    id = this@toTestTypeDBO.id
                }
            }
        }
    }
}

fun GetTestDataResponse.toTestDataDBO(): TestDataDBO {
    return when (this) {
        is GetTestDataResponse.EssayTest -> {
            TestDataDBO().apply {
                id = this@toTestDataDBO.id
                essayTestDBO = EssayTestDBO().apply {
                    essay = this@toTestDataDBO.essay
                }
            }
        }
    }
}
