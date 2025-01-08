package com.evg.test_essay.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.EssayTestData
import com.evg.test_essay.domain.usecase.TestEssayUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestEssayViewModel(
    private val testEssayUseCases: TestEssayUseCases,
    testId: Int?,
): ContainerHost<TestEssayState, TestEssaySideEffect>, ViewModel() {
    override val container = container<TestEssayState, TestEssaySideEffect>(TestEssayState())
    val isEditable = testId == null

    init {
        testId?.let { getEssayTestData(it) }
    }

    fun sendTest(data: EssayTestData) = intent {
        reduce { state.copy(isTestSending = true) }
        when (val response = testEssayUseCases.sendTestToServerUseCase.invoke(data = data)) {
            is ServerResult.Success -> {
                postSideEffect(TestEssaySideEffect.TestEssaySuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(TestEssaySideEffect.TestEssayFail(error = response.error))
            }
        }
        reduce { state.copy(isTestSending = false) }
    }

    private fun getEssayTestData(id: Int) = intent {
        reduce { state.copy(isTestDataLoading = true) }
        when (val response = testEssayUseCases.getEssayTestData.invoke(id = id)) {
            is ServerResult.Success -> {
                state.testData.value = response.data
            }
            is ServerResult.Error -> {
                postSideEffect(TestEssaySideEffect.TestDataFail(error = response.error))
            }
        }
        reduce { state.copy(isTestDataLoading = false) }
    }
}