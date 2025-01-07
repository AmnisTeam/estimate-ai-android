package com.evg.tests_list.domain.usecase

import com.evg.tests_list.domain.model.TestSource
import com.evg.tests_list.domain.repository.TestsListRepository


class GetAllTestsUseCaseUseCase(
    private val testsListRepository: TestsListRepository,
) {
    suspend fun invoke(): TestSource {
        return if (testsListRepository.isInternetAvailable()) {
            TestSource.Network(testsListRepository.getAllTestsByPageFromServer())
        } else {
            TestSource.Local(testsListRepository.getAllTestsFromDatabase())
        }
    }
}