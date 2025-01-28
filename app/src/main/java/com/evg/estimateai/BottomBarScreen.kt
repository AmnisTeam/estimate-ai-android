package com.evg.estimateai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.evg.estimateai.graphs.Route


sealed class BottomBarScreen(
    val route: Route,
    val title: String,
    val icon: ImageVector,
) {
    companion object {
        val allScreens = listOf(Statistics, TestsList, Account)
    }

    data object Statistics : BottomBarScreen(
        route = Route.Statistics,
        title = "Statistics",
        icon = Icons.Default.DateRange
    )
    data object TestsList : BottomBarScreen(
        route = Route.TestsList,
        title = "Tests",
        icon = Icons.Default.Face
    )
    data object Account : BottomBarScreen(
        route = Route.Account,
        title = "Account",
        icon = Icons.Default.Home
    )
}
