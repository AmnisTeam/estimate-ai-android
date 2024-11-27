package com.evg.tests_list.domain.usecase

import com.evg.tests_list.domain.repository.TestsListRepository


class GetAllTestsUseCase(
    private val testsListRepository: TestsListRepository,
) {
    suspend fun invoke(): Unit {
        //return testsListRepository
    }
}