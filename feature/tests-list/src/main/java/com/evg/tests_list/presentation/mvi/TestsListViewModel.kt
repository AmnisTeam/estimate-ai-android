package com.evg.tests_list.presentation.mvi

import androidx.lifecycle.ViewModel
import com.evg.tests_list.domain.usecase.TestsListUseCases
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestsListViewModel(
    private val testsListUseCases: TestsListUseCases,
): ContainerHost<TestsListState, TestsListSideEffect>, ViewModel() {
    override val container = container<TestsListState, TestsListSideEffect>(TestsListState())

    fun getAllTests() = intent {
        reduce { state.copy(isTestsLoading = true) }
        /*when (val response = testsListUseCases.registrationUseCase.invoke()) {
            is ServerResult.Success -> {
                postSideEffect(TestsListSideEffect.TestsListSuccess)
            }
            is ServerResult.Error -> {
                postSideEffect(TestsListSideEffect.TestsListFail(error = response.error))
            }
        }*/
        reduce { state.copy(isTestsLoading = false) }
    }
}