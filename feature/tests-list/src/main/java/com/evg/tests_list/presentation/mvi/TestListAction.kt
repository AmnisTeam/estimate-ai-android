package com.evg.tests_list.presentation.mvi

sealed class TestListAction {
    data object GetAllTests: TestListAction()
}