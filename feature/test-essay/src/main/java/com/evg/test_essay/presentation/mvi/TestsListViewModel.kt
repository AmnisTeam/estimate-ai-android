package com.evg.test_essay.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.test_essay.domain.usecase.TestEssayUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestEssayViewModel(
    private val testEssayUseCases: TestEssayUseCases,
): ContainerHost<TestEssayState, TestEssaySideEffect>, ViewModel() {
    override val container = container<TestEssayState, TestEssaySideEffect>(TestEssayState())

    fun sendTest() = intent {
        reduce { state.copy(isTestSendingLoading = true) }
        /*when (val response = testsListUseCases.registrationUseCase.invoke()) {
            is ServerResult.Success -> {
                postSideEffect(TestsListSideEffect.TestsListSuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(TestsListSideEffect.TestsListFail(error = response.error))
            }
        }*/
        reduce { state.copy(isTestSendingLoading = false) }
    }
}