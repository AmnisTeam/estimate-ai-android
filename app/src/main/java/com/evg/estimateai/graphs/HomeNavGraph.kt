package com.evg.estimateai.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.evg.estimateai.bottomNavPadding
import com.evg.estimateai.scaffold.EstimateAiScaffold
import com.evg.statistics.presentation.StatisticsRoot
import com.evg.tests_list.presentation.TestsListRoot


fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation<Route.Home>(
        startDestination = Route.TestsList,
    ) {
        composable<Route.TestsList> {
            EstimateAiScaffold(
                modifier = Modifier.padding(bottom = bottomNavPadding)
            ) { paddingValues ->
                TestsListRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    onTestSelectScreen = { navController.navigate(route = Route.TestCreation) },
                    onTestEssayScreen = { id -> navController.navigate(route = Route.TestEssay(id = id)) },
                )
            }
        }
        composable<Route.Statistics> {
            EstimateAiScaffold(
                modifier = Modifier.padding(bottom = bottomNavPadding),
            ) { paddingValues ->
                StatisticsRoot(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                )
            }
        }
        composable<Route.Account> { }

        createTestNavGraph(navController = navController)
    }
}
