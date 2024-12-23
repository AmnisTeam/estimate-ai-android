package com.evg.tests_list.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.flatMap
import androidx.paging.map
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.ServerResult
import com.evg.tests_list.domain.model.TestType
import com.evg.tests_list.domain.usecase.TestsListUseCases
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class TestsListViewModel(
    private val testsListUseCases: TestsListUseCases,
): ContainerHost<TestsListState, TestsListSideEffect>, ViewModel() {
    override val container = container<TestsListState, TestsListSideEffect>(TestsListState())

    init {
        updateTests()
        sendLoadingIDsToServer()
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
                .collect { tests: PagingData<ServerResult<TestType, NetworkError>> ->
                    state.tests.value = tests
                }
        }
    }

    private fun sendLoadingIDsToServer() = intent {
        viewModelScope.launch {
           val result = testsListUseCases.connectTestProgressUseCase.invoke()
           when (result) {
               is ServerResult.Success -> {
                   result.data.collect { tests ->
                       val testsById = tests.associateBy {
                           when (it) {
                               is TestType.OnReadyTestType -> it.id
                               is TestType.OnLoadingTestType -> it.id
                               is TestType.OnErrorTestType -> it.id
                           }
                       }

                       state.tests.value = state.tests.value.map { test ->
                           when (test) {
                               is ServerResult.Success -> {
                                   val currentTestId = when (val data = test.data) {
                                       is TestType.OnReadyTestType -> data.id
                                       is TestType.OnLoadingTestType -> data.id
                                       is TestType.OnErrorTestType -> data.id
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