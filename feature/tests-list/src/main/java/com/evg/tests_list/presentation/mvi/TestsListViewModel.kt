package com.evg.tests_list.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.map
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.TestsListUseCases
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestsListViewModel(
    private val testsListUseCases: TestsListUseCases,
): ContainerHost<TestsListState, TestsListSideEffect>, ViewModel() {
    override val container = container<TestsListState, TestsListSideEffect>(TestsListState())

    init {
        updateTests()
    }

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

    fun updateTests() = intent {
        viewModelScope.launch {
            testsListUseCases.getAllTestsUseCaseUseCase.invoke()
                .cachedIn(viewModelScope)
                .collect { tests ->
                    /*val successTests: PagingData<TestType> = tests.map { result ->
                        when (result) {
                            is ServerResult.Success -> result.data
                            is ServerResult.Error -> {
                                result.data
                            }
                        }
                    }*/

                    state.tests.value = tests
                }
        }
    }
}