package com.evg.test_essay.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.api.domain.utils.ServerResult
import com.evg.test_essay.domain.model.CreateEssayTest
import com.evg.test_essay.domain.usecase.TestEssayUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestEssayViewModel(
    private val testEssayUseCases: TestEssayUseCases,
): ContainerHost<TestEssayState, TestEssaySideEffect>, ViewModel() {
    override val container = container<TestEssayState, TestEssaySideEffect>(TestEssayState())

    fun sendTest(data: CreateEssayTest) = intent {
        reduce { state.copy(isTestSendingLoading = true) }
        when (val response = testEssayUseCases.sendTestToServerUseCase.invoke(data = data)) {
            is ServerResult.Success -> {
                postSideEffect(TestEssaySideEffect.TestEssaySuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(TestEssaySideEffect.TestEssayFail(error = response.error))
            }
        }
        reduce { state.copy(isTestSendingLoading = false) }
    }
}