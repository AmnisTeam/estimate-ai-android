package com.evg.test_essay.domain.mapper

import com.evg.api.domain.model.GetTestDataResponse
import com.evg.api.type.CreateEssayTestDTO
import com.evg.database.domain.model.EssayTestDBO
import com.evg.test_essay.domain.model.EssayTestData

fun EssayTestData.toCreateEssayTestDTO(): CreateEssayTestDTO {
    return CreateEssayTestDTO(
        essay = this.essay,
    )
}

fun EssayTestDBO?.toEssayTestData(): EssayTestData? {
    return this?.let {
        EssayTestData(
            essay = it.essay
        )
    }
}


fun GetTestDataResponse.EssayTest.toEssayTestData(): EssayTestData {
    /*return this?.let {
        EssayTestData(
            essay = it.essay
        )
    }*/
    return EssayTestData(
        essay = this.essay
    )
}
