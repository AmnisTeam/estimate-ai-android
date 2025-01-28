package com.evg.estimateai

import com.evg.estimateai.graphs.Route


sealed class TopBarTitle(
    val route: Route,
    val title: String,
) {
    companion object {
        val allTitles = listOf(TestSelect, TestsList)
    }

    data object TestSelect : TopBarTitle(
        route = Route.TestSelect,
        title = "Select the test type",
    )
    data object TestsList : TopBarTitle(
        route = Route.TestEssay(id = null),
        title = "The essay test",
    )
}
