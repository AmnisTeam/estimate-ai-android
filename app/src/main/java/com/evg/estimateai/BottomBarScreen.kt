package com.evg.estimateai

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.evg.estimateai.graphs.Route
import com.evg.resource.R


sealed class BottomBarScreen(
    val route: Route,
    @StringRes val title: Int,
    val icon: ImageVector,
) {
    companion object {
        val allScreens = listOf(Statistics, TestsList, Account)
    }

    data object Statistics : BottomBarScreen(
        route = Route.Statistics,
        title = R.string.tests,
        icon = Icons.Default.DateRange
    )
    data object TestsList : BottomBarScreen(
        route = Route.TestsList,
        title = R.string.statistics,
        icon = Icons.Default.Face
    )
    data object Account : BottomBarScreen(
        route = Route.Account,
        title = R.string.account,
        icon = Icons.Default.Home
    )
}
