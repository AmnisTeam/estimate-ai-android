package com.evg.estimateai

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    companion object {
        val allRoutes = listOf(Statistics.route, Tests.route, Account.route)
        val allScreens = listOf(Statistics, Tests, Account)
    }

    data object Statistics : BottomBarScreen(
        route = "statistics",
        title = "Statistics",
        icon = Icons.Default.DateRange
    )
    data object Tests : BottomBarScreen(
        route = "tests",
        title = "Tests",
        icon = Icons.Default.Face
    )
    data object Account : BottomBarScreen(
        route = "account",
        title = "Account",
        icon = Icons.Default.Home
    )
}
