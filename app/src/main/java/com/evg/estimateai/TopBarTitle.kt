package com.evg.estimateai

import androidx.annotation.StringRes
import com.evg.estimateai.graphs.Route
import com.evg.resource.R


sealed class TopBarTitle(
    val route: Route,
    @StringRes val title: Int,
) {
    companion object {
        val allTitles = listOf(TestSelect, TestsList)
    }

    data object TestSelect : TopBarTitle(
        route = Route.TestSelect,
        title = R.string.select_test_type,
    )
    data object TestsList : TopBarTitle(
        route = Route.TestEssay(id = null),
        title = R.string.essay_test,
    )
}
