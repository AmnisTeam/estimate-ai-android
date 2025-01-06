package com.evg.test_essay.domain.mapper

import com.evg.api.type.CreateEssayTestDTO
import com.evg.test_essay.domain.model.CreateEssayTest

fun CreateEssayTest.toCreateEssayTestDTO(): CreateEssayTestDTO {
    return CreateEssayTestDTO(
        essay = this.essay,
    )
}