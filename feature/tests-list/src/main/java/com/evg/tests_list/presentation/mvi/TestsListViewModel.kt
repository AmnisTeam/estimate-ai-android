package com.evg.tests_list.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestSource
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.TestsListUseCases
import com.evg.tests_list.presentation.mapper.toTestState
import com.evg.tests_list.presentation.model.TestState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestsListViewModel(
    private val testsListUseCases: TestsListUseCases,
): ContainerHost<TestsListState, TestsListSideEffect>, ViewModel() {
    override val container = container<TestsListState, TestsListSideEffect>(TestsListState())

    private var connectTestProgressJob: Job? = null

    init {
        getAllTests()
        connectTestProgress()
    }

    fun getAllTests() = intent {
        reduce { state.copy(isTestsLoading = true) }
        viewModelScope.launch {
            when (val testSource = testsListUseCases.getAllTestsUseCaseUseCase.invoke()) {
                is TestSource.Network -> {
                    testSource
                        .data
                        .cachedIn(viewModelScope)
                        .collect { tests: PagingData<ServerResult<TestType, NetworkError>> ->
                            val mappedTests: PagingData<ServerResult<TestState, NetworkError>> = tests.map { result ->
                                when (result) {
                                    is ServerResult.Success -> ServerResult.Success(result.data.toTestState())
                                    is ServerResult.Error -> ServerResult.Error(result.error)
                                }
                            }

                            state.tests.value = mappedTests
                            reduce { state.copy(isTestsLoading = false) }
                        }
                }
                is TestSource.Local -> {
                    testSource.data
                        .collect { tests: PagingData<TestType> ->
                            val mappedTests: PagingData<ServerResult<TestState, NetworkError>> = tests.map { result ->
                                ServerResult.Success(result.toTestState())
                            }

                            state.tests.value = mappedTests
                            reduce { state.copy(isTestsLoading = false) }
                        }
                }
            }
        }
    }

    private fun connectTestProgress() = intent {
        if (connectTestProgressJob != null) return@intent

        connectTestProgressJob = viewModelScope.launch {
           when (val result = testsListUseCases.connectTestProgressUseCase.invoke()) {
               is ServerResult.Success -> {
                   result.data.collect { tests ->
                       val testsById = tests.map { it.toTestState() } .associateBy {
                           when (it) {
                               is TestState.ErrorTest -> it.id
                               is TestState.FinishedTest -> it.id
                               is TestState.LoadingTest -> it.id
                           }
                       }

                       state.tests.value = state.tests.value.map { test ->
                           when (test) {
                               is ServerResult.Success -> {
                                   val currentTestId = when (val data = test.data) {
                                       is TestState.ErrorTest -> data.id
                                       is TestState.FinishedTest -> data.id
                                       is TestState.LoadingTest -> data.id
                                   }

                                   val updatedTest = testsById[currentTestId]

                                   if (updatedTest != null) {
                                       ServerResult.Success(updatedTest)
                                   } else {
                                       test
                                   }
                               }
                               is ServerResult.Error -> {
                                   ServerResult.Error(test.error)
                               }
                           }
                       }
                   }
               }
               is ServerResult.Error -> {
                   // postSideEffect(TestsListSideEffect.ConnectTestProgressFail(error = result.error)) //TODO
               }
           }
        }
    }
}