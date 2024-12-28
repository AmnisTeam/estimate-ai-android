package com.evg.tests_list.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.TestsListUseCases
import com.evg.tests_list.presentation.mapper.toTestState
import com.evg.tests_list.presentation.model.TestState
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestsListViewModel(
    private val testsListUseCases: TestsListUseCases,
): ContainerHost<TestsListState, TestsListSideEffect>, ViewModel() {
    override val container = container<TestsListState, TestsListSideEffect>(TestsListState())

    init {
        getAllTests()
        sendLoadingIDsToServer()
    }

    fun getAllTests() = intent {
        viewModelScope.launch {
            reduce { state.copy(isTestsLoading = true) }
            testsListUseCases.getAllTestsUseCaseUseCase.invoke()
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
    }

    private fun sendLoadingIDsToServer() = intent {
        viewModelScope.launch {
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
                   val qwewqe = 34 //TODO
               }
           }
        }
    }

    /*private fun sendLoadingIDsToServer(ids: List<Int>) = intent {
        viewModelScope.launch {
            testsListUseCases.sendLoadingIDsToServerUseCase.invoke()
                .cachedIn(viewModelScope)
                .collect { tests: ServerResult<List<TestType>, NetworkError> ->
                    when (tests) {
                        is ServerResult.Success -> {
                            val loadingTests = tests.data.filterIsInstance<TestType.OnReadyTestType>()
                            val readyTests = tests.data.filterIsInstance<TestType.OnReadyTestType>()

                            state.tests.value = state.tests.value.map { test ->
                                when (test) {
                                    is ServerResult.Success -> {
                                        when (test.data) {
                                            is TestType.OnLoadingTestType -> {
                                                val updatedTest = loadingTests.find { it.id == (test.data as TestType.OnLoadingTestType).id }
                                                ServerResult.Success(updatedTest ?: test.data)
                                            }
                                            is TestType.OnReadyTestType -> {
                                                val updatedTest = readyTests.find { it.id == (test.data as TestType.OnReadyTestType).id }
                                                ServerResult.Success(updatedTest ?: test.data)
                                            }
                                        }
                                    }
                                    is ServerResult.Error -> {
                                        ServerResult.Error(test.error)
                                    }
                                }
                            }
                        }
                        is ServerResult.Error -> {

                        }
                    }

                }
        }
    }*/
}