package com.evg.estimateai

import com.evg.estimateai.graphs.Route


sealed class TopBarTitle(
    val route: Route,
    val title: String,
) {
    companion object {
        val allTitles = listOf(TestSelect, TestsList(1))
        val allTitlesRoutes = listOf(TestSelect.route, TestsList(1).route)
    }

    data object TestSelect : TopBarTitle(
        route = Route.TestSelect,
        title = "Select the test type",
    )
    data class TestsList(val id: Int?) : TopBarTitle(
        route = Route.TestEssay(id = id),
        title = "The essay test",
    )
}
